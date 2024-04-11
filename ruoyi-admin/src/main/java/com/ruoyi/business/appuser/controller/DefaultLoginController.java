package com.ruoyi.business.appuser.controller;

import com.ruoyi.business.appuser.service.DefaultLoginService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 默认登录
 *
 * @author Tellsea
 * @date 2022/9/2
 */
@Api("默认登录")
@RestController
@RequestMapping("/au/defaultLogin")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class DefaultLoginController {

    private final DefaultLoginService defaultLoginService;

    @ApiOperation("登录检查")
    @GetMapping("beforeLogin")
    public AjaxResult beforeLogin() {
        return AjaxResult.success("检查成功");
    }

    @ApiOperation("账号密码登录")
    @PostMapping("login")
    public AjaxResult login(@RequestBody LoginBody entity) {
        return defaultLoginService.login(entity);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        return defaultLoginService.getInfo();
    }

    @ApiOperation("获取验证码")
    @GetMapping("getCode/{username}")
    public AjaxResult getCode(@PathVariable String username) {
        return defaultLoginService.getCode(username);
    }

    @ApiOperation("账号验证码登录")
    @PostMapping("loginByCode")
    public AjaxResult loginByCode(@RequestBody LoginBody entity) {
        return defaultLoginService.loginByCode(entity);
    }

    @ApiOperation("注册验证码登录")
    @PostMapping("register")
    public AjaxResult register(@RequestBody LoginBody entity) {
        return defaultLoginService.register(entity);
    }
}
