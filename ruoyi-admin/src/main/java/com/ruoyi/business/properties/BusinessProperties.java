package com.ruoyi.business.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 业务配置
 *
 * @author Tellsea
 * @date 2022/9/7
 */
@Data
@Component
@ConfigurationProperties(prefix = "business")
public class BusinessProperties {

    /**
     * 服务端根路径
     */
    private String serviceUrl;
    /**
     * 微信公众号配置
     */
    private WeiXinMp weiXinMp;

    @Data
    public static class WeiXinMp {

        /**
         * 公众号授权登录回调地址
         */
        private String callbackUrl;
        /**
         * 回调完成重定向前端地址
         */
        private String loginUrl;
    }
}
