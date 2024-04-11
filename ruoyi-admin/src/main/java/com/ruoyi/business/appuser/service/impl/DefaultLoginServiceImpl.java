package com.ruoyi.business.appuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.business.appuser.service.DefaultLoginService;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.mapper.SysUserRoleMapper;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 默认登录
 *
 * @author Tellsea
 * @date 2022/9/2
 */
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DefaultLoginServiceImpl implements DefaultLoginService {

    private final SysLoginService loginService;
    private final SysPermissionService permissionService;
    private final RedisCache redisCache;
    private final ISysUserService sysUserService;

    @Override
    public AjaxResult login(LoginBody entity) {
        return AjaxResult.success("登录成功", loginService.login(entity.getUsername(), entity.getPassword()));
    }

    @Override
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    @Override
    public AjaxResult getCode(String username) {
        String key = CacheConstants.PHONE_CODE + username;
        String code = RandomStringUtils.randomNumeric(4);
        if (redisCache.hasKey(key)) {
            return AjaxResult.success("无需重复发送验证码");
        }
        redisCache.setCacheObject(key, code, 5, TimeUnit.SECONDS);
        // 调用短信平台发送验证码
        return AjaxResult.success("验证码发送成功");
    }

    @Override
    public AjaxResult loginByCode(LoginBody entity) {
        String key = CacheConstants.PHONE_CODE + entity.getUsername();
        if (!redisCache.hasKey(key)) {
            return AjaxResult.error("请先获取短信验证码");
        }
        String cacheCode = redisCache.getCacheObject(key);
        if (StringUtils.isEmpty(cacheCode)) {
            return AjaxResult.error("验证码已失效，请重新获取");
        }
        if (!StringUtils.equals(cacheCode, entity.getCode())) {
            return AjaxResult.error("短信验证码错误");
        }
        entity.setPassword("123456");
        return AjaxResult.success("登录成功", loginService.login(entity.getUsername(), entity.getPassword()));
    }

    @Override
    public AjaxResult register(LoginBody entity) {
        String key = CacheConstants.PHONE_CODE + entity.getUsername();
        if (!redisCache.hasKey(key)) {
            return AjaxResult.error("请先获取短信验证码");
        }
        String cacheCode = redisCache.getCacheObject(key);
        if (StringUtils.isEmpty(cacheCode)) {
            return AjaxResult.error("验证码已失效，请重新获取");
        }
        if (!StringUtils.equals(cacheCode, entity.getCode())) {
            return AjaxResult.error("短信验证码错误");
        }
        List<SysUser> list = sysUserService.list(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, entity.getUsername()));
        if (list.size() > 1) {
            return AjaxResult.error("账号异常，存在多个");
        }
        String token = loginService.getToken(list, entity.getUsername(), entity.getUsername());
        return AjaxResult.success("登录成功", token);
    }
}
