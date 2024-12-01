package com.ruoyi.business.aidetection.domain;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.aidetection.domain
 * @Project：ruoyi-vue-service
 * @name：AvControl
 * @Date：2024/4/9 20:28
 * @Filename：AvControl
 */
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * control对象 av_control
 *
 * @author yuankun
 * @date 2024-04-09
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AvControl extends BaseEntity {

    /**
     *
     */
    private Long id;
    /**
     * 排序
     */
    @Excel(name = "识别名称")
    private String name;
    /**
     * 排序
     */
    @Excel(name = "排序")
    private Long sort;

    /**
     * 编码
     */
    @Excel(name = "编码")
    private String code;

    /**
     * 流app
     */
    @Excel(name = "流app")
    private String streamApp;

    /**
     * 流name
     */
    @Excel(name = "流name")
    private String streamName;

    /**
     * 视频信息
     */
    @Excel(name = "视频信息")
    private String streamVideo;

    /**
     * 音频信息
     */
    @Excel(name = "音频信息")
    private String streamAudio;

    /**
     * 识别物体码
     */
    @Excel(name = "识别物体码")
    private String objectCode;

    /**
     * 算法码
     */
    @Excel(name = "算法码")
    private String algorithmCode;

    /**
     * 最小报警时间间隔
     */
    @Excel(name = "最小报警时间间隔")
    private Long minInterval;

    /**
     * 最小报警时间间隔
     */
    @Excel(name = "识别区域")
    private String recognitionRegion;
    /**
     * 置信度
     */
    @Excel(name = "置信度")
    private Double classThresh;

    /**
     * 阈值
     */
    @Excel(name = "阈值")
    private Double overlapThresh;

    /**
     * 拉流地址
     */
    @Excel(name = "推流地址")
    private Long pushStream;
    /**
     * 拉流app
     */
    @Excel(name = "推流app")
    private String pushStreamApp;

    /**
     * 拉流name
     */
    @Excel(name = "推流name")
    private String pushStreamName;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 状态
     */
    @Excel(name = "状态")
    private Long state;

}