package com.ruoyi.business.aidetection.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * alarm对象 av_alarm
 *
 * @author yuankun
 * @date 2024-04-06
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AvAlarm extends BaseEntity {

    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * $column.columnComment
     */
    @Excel(name = "排序", type = Excel.Type.EXPORT)
    private Long sort;

    /**
     * $column.columnComment
     */

    private String controlCode;

    /**
     * $column.columnComment
     */
    @Excel(name = "描述",  type = Excel.Type.EXPORT)
    @TableField("`desc`")
    private String desc;

    /**
     * $column.columnComment
     */
    @Excel(name = "视频路径",  type = Excel.Type.EXPORT)
    private String videoPath;

    /**
     * $column.columnComment
     */
    @Excel(name = "图片路径",  type = Excel.Type.EXPORT)
    private String imagePath;

    /**
     * $column.columnComment
     */
    @Excel(name = "状态", readConverterExp = "1=未处理,2=已处理,3=已忽略", type = Excel.Type.EXPORT)
    private Long state;

    /**
     * $column.columnComment
     */
    @Excel(name = "预警等级", readConverterExp = "1=一级预警,2=二级预警,3=三级预警")
    private Long grade;

    /**
     * $column.columnComment
     */
    @Excel(name = "备注", type = Excel.Type.EXPORT)
    private String remark;

    /**
     * $column.columnComment
     */
    private Date operTime;

    /**
     * $column.columnComment
     */
    @Excel(name = "预警类别")
    private String category;

}