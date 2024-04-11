package com.ruoyi.business.appuser.service;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginBody;

/**
 * 默认登录
 *
 * @author Tellsea
 * @date 2022/9/2
 */
public interface DefaultLoginService {

    /**
     * 账号密码登录
     *
     * @param entity
     * @return
     */
    AjaxResult login(LoginBody entity);

    /**
     * 获取用户信息
     *
     * @return
     */
    AjaxResult getInfo();

    /**
     * 获取验证码
     *
     * @param username
     * @return
     */
    AjaxResult getCode(String username);

    /**
     * 账号验证码登录
     *
     * @param entity
     * @return
     */
    AjaxResult loginByCode(LoginBody entity);

    /**
     * 注册验证码登录
     *
     * @param entity
     * @return
     */
    AjaxResult register(LoginBody entity);
}
