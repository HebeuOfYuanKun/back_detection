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
 * @name：Algorithm
 * @Date：2024/11/16 14:43
 * @Filename：Algorithm
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AvAlgorithm extends BaseEntity {

    /**
     * id
     */
    private Long id;

    /**
     * 排序
     */
    @Excel(name = "排序")
    private Integer sort;

    /**
     * 名字
     */
    @Excel(name = "名字")
    private String name;

    /**
     * 算法编码
     */
    @Excel(name = "算法编码")
    private String algorithmCode;

    /**
     * 识别物体
     */
    @Excel(name = "识别物体")
    private String objects;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String description;

    /**
     * 模型编码
     */
    @Excel(name = "模型编码")
    private String modelCode;

    /**
     * 模型名字
     */
    @Excel(name = "模型名字")
    private String modelName;

    /**
     * 模型地址
     */
    @Excel(name = "模型地址")
    private String modelPath;

    /**
     * 运行设备
     */
    @Excel(name = "运行设备")
    private String device;

}