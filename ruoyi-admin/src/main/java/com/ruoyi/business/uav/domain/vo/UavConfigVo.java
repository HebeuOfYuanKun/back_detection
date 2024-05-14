package com.ruoyi.business.uav.domain.vo;

import com.ruoyi.business.uav.domain.UavConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav.domain.vo
 * @Project：ruoyi-vue-service
 * @name：uua
 * @Date：2024/5/12 15:24
 * @Filename：uua
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UavConfigVo extends UavConfig {
    private String messageTopic;
    private String uavControlName;
}
