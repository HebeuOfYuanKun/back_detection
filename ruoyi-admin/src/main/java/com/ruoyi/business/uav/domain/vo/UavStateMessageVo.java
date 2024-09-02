package com.ruoyi.business.uav.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav.domain.vo
 * @Project：ruoyi-vue-service
 * @name：UavStateMessageVo
 * @Date：2024/5/25 20:04
 * @Filename：UavStateMessageVo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UavStateMessageVo {

    Double lng;
    Double  lat;
    float altitude;
    float ultrasonic;
    float airspeed;
    String batteryPower;

}
