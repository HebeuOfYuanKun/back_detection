package com.ruoyi.business.uav.domain;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav.domain.vo
 * @Project：ruoyi-vue-service
 * @name：aa
 * @Date：2024/5/12 16:16
 * @Filename：aa
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class UavMessage extends BaseEntity {

    /**
     * id
     */
    private Long id;

    /**
     * 订阅信息主题
     */
    @Excel(name = "订阅信息主题")
    private String messageTopic;

    /**
     * 订阅Qos
     */
    @Excel(name = "订阅Qos")
    private Long messageQos;

}

