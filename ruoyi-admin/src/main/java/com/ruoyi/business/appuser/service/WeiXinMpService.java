package com.ruoyi.business.appuser.service;

/**
 * 微信公众号接口
 *
 * @author Tellsea
 * @date 2022/9/7
 */
public interface WeiXinMpService {

    /**
     * 获取授权登录Url
     *
     * @return
     */
    String getLoginUrl();

    /**
     * 登录回调
     */
    void callback();
}
