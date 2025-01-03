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
 * @name：AvObject
 * @Date：2024/4/6 19:34
 * @Filename：AvObject
 */

@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AvObject extends BaseEntity {

    /**
     * id
     */
    private Long id;

    /**
     * 识别物体编码
     */
    @Excel(name = "识别物体编码")
    private String code;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 英文名称
     */
    @Excel(name = "英文名称")
    private String nameEn;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Long state;

    /**
     * 预警等级
     */
    @Excel(name = "预警等级")
    private Long grade;

    /**
     * 提示信息
     */
    @Excel(name = "提示信息")
    private String tipMessage;

}
