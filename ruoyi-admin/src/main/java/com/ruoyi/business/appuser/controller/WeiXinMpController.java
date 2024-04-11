package com.ruoyi.business.appuser.controller;

import com.ruoyi.business.appuser.service.WeiXinMpService;
import com.ruoyi.common.core.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信公众号控制器
 *
 * @author Tellsea
 * @date 2022/9/6
 */
@Api("微信公众号控制器")
@RestController
@RequestMapping("/au/weiXinMp")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class WeiXinMpController {

    private final WeiXinMpService weiXinMpService;

    @ApiOperation("获取授权登录Url")
    @GetMapping("getLoginUrl")
    public AjaxResult getLoginUrl() {
        return AjaxResult.success("操作成功", weiXinMpService.getLoginUrl());
    }

    @ApiOperation("登录回调")
    @RequestMapping("callback")
    public void callback() {
        weiXinMpService.callback();
    }
}
