package com.ruoyi.business.aidetection.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.aidetection.domain
 * @Project：ruoyi-vue-service
 * @name：WSMessage
 * @Date：2024/4/23 10:23
 * @Filename：WSMessage
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WSMessage {
    private String Action;
    private String SocketType;
    private String ChartName;
    private String Data;

}
