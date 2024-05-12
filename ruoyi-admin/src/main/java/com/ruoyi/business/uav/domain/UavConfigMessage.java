package com.ruoyi.business.uav.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav.domain
 * @Project：ruoyi-vue-service
 * @name：akk
 * @Date：2024/5/12 15:57
 * @Filename：akk
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class UavConfigMessage extends BaseEntity {

    /**
     * 订阅信息id
     */
    private Long messageId;

    /**
     * 配置id
     */
    private Long configId;

}

