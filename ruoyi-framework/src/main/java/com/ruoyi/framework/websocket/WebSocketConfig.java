package com.ruoyi.framework.websocket;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.framework.websocket
 * @Project：ruoyi-vue-service
 * @name：aa
 * @Date：2024/4/22 20:24
 * @Filename：aa
 */


@Configuration
public class WebSocketConfig
{
    @Bean
    public ServerEndpointExporter serverEndpointExporter()
    {
        return new ServerEndpointExporter();
    }
}
