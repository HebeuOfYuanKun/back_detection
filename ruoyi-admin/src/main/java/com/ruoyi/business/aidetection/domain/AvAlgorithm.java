package com.ruoyi.business.aidetection.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.aidetection.domain
 * @Project：ruoyi-vue-service
 * @name：AvAlgorithm
 * @Date：2024/4/19 11:53
 * @Filename：AvAlgorithm
 */

@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AvAlgorithm extends BaseEntity {

    /**
     * ID
     */
    private Long id;
    /**
     * 排序
     */
    private Long sort;
    /**
     * 编码
     */

    private String code;
    /**
     * 名称
     */

    private String name;
    /**
     * 英文名称
     */
    private String nameEn;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态
     */
    private Long state;

}
