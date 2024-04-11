package com.ruoyi.business.appuser.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.business.appuser.service.WeiXinMpService;
import com.ruoyi.business.properties.BusinessProperties;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Tellsea
 * @date 2022/9/7
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WeiXinMpServiceImpl implements WeiXinMpService {

    private final WxMpService wxMpService;
    private final BusinessProperties businessProperties;

    private final ISysUserService sysUserService;
    private final SysLoginService loginService;

    @Override
    public String getLoginUrl() {
        return wxMpService.getOAuth2Service().buildAuthorizationUrl(businessProperties.getWeiXinMp().getCallbackUrl(), WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
    }

    @Override
    public void callback() {
        HttpServletRequest request = ServletUtils.getRequest();
        HttpServletResponse response = ServletUtils.getResponse();
        String code = request.getParameter("code");
        try {
            WxOAuth2AccessToken accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
            // 这里可以拿到：头像、昵称、openId、性别
            WxOAuth2UserInfo userInfo = wxMpService.getOAuth2Service().getUserInfo(accessToken, null);
            List<SysUser> list = sysUserService.list(new LambdaQueryWrapper<SysUser>()
                    .eq(SysUser::getUserName, userInfo.getOpenid()));
            if (list.size() > 1) {
                log.error("账号异常，存在多个OpenId：{}", userInfo.getOpenid());
                return;
            }
            String token = loginService.getToken(list, userInfo.getOpenid(), userInfo.getNickname(), userInfo.getHeadImgUrl(), userInfo.getSex());
            response.sendRedirect(businessProperties.getWeiXinMp().getLoginUrl() + "?token=" + token);
        } catch (WxErrorException e) {
            log.error("根据code获取AccessToken异常");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
