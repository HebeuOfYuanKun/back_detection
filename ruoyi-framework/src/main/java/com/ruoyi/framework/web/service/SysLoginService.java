package com.ruoyi.framework.web.service;

import cn.hutool.core.comparator.CompareUtil;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.RoleConstants;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.security.context.AuthenticationContextHolder;
import com.ruoyi.system.domain.SysUserRole;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService {
    @Autowired
    private TokenService tokenService;
    @Resource
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private ISysConfigService configService;
    @Autowired
    private SysPermissionService permissionService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        boolean captchaEnabled = configService.selectCaptchaEnabled();
        // 验证码开关
        if (captchaEnabled) {
            validateCaptcha(username, code, uuid);
        }
        // 用户验证
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        } finally {
            AuthenticationContextHolder.clearContext();
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 登录：账号 + 密码
     *
     * @param username 用户名
     * @param password 密码
     * @return 结果
     */
    public String login(String username, String password) {
        // 用户验证
        Authentication authentication = null;
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            AuthenticationContextHolder.setContext(authenticationToken);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        } finally {
            AuthenticationContextHolder.clearContext();
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }

    public String getToken(List<SysUser> list, String userName, String nickName) {
        return getToken(list, userName, nickName, null, null);
    }

    /**
     * 用户不存在，创建用户，存在则更新用户
     *
     * @param list
     * @param userName 账号
     * @param nickName 昵称
     * @param avatar   头像
     * @param sex      1-男  2-女
     * @return
     */
    public String getToken(List<SysUser> list, String userName, String nickName, String avatar, Integer sex) {
        String token;
        if (CollectionUtils.isEmpty(list)) {
            SysUser sysUser = new SysUser();
            sysUser.setUserName(userName);
            sysUser.setNickName(nickName);
            sysUser.setAvatar(avatar);
            handleSex(sysUser, sex);
            sysUser.setDeptId(0L);
            sysUser.setPassword(SecurityUtils.encryptPassword("123456"));
            sysUser.setRoleIds(new Long[]{RoleConstants.USER.getRoleId()});
            sysUserService.insertUser(sysUser);
            // 生成token
            sysUser = sysUserService.selectUserByUserName(sysUser.getUserName());
            LoginUser loginUser = new LoginUser(sysUser, permissionService.getMenuPermission(sysUser));
            token = tokenService.createToken(loginUser);
        } else {
            SysUser user = list.get(0);
            user.setNickName(nickName);
            user.setAvatar(avatar);
            handleSex(user, sex);
            sysUserService.updateById(user);
            user.setAvatar(StringUtils.isNotEmpty(avatar) ? avatar : user.getAvatar());
            SysUser sysUser = sysUserService.selectUserByUserName(user.getUserName());
            List<String> roleKeys = sysUser.getRoles().stream().map(SysRole::getRoleKey).collect(Collectors.toList());
            if (!roleKeys.contains(RoleConstants.USER.getRoleKey())) {
                // 如果没有用户角色，给角色用户
                sysUserRoleMapper.insert(new SysUserRole().setUserId(user.getUserId()).setRoleId(RoleConstants.USER.getRoleId()));
                sysUser = sysUserService.selectUserByUserName(user.getUserName());
            }
            LoginUser loginUser = new LoginUser(sysUser, permissionService.getMenuPermission(sysUser));
            token = tokenService.createToken(loginUser);
        }
        return token;
    }

    /**
     * 处理性别
     *
     * @param sysUser
     * @param sex
     */
    public void handleSex(SysUser sysUser, Integer sex) {
        if (sex == null) {
            return;
        }
        String sexValue = "2";
        if (CompareUtil.compare(sex, 1) == 0) {
            sexValue = "0";
        } else if (CompareUtil.compare(sex, 2) == 0) {
            sexValue = "1";
        }
        sysUser.setSex(sexValue);
    }
}
