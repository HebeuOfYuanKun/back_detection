package com.ruoyi.business.aidetection.controller;

import com.ruoyi.common.annotation.Anonymous;
import com.ruoyi.framework.websocket.WebSocketUsers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.aidetection.controller
 * @Project：ruoyi-vue-service
 * @name：TestController
 * @Date：2024/4/23 11:24
 * @Filename：TestController
 */
@RestController
public class TestController {
    @Anonymous
    @GetMapping("/test")
    public String test(){
        WebSocketUsers.sendMessageToUsersByText(String.valueOf(999999999));
        return "test";
    }
}
