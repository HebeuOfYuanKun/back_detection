package com.ruoyi.business.aidetection.domain.vo;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.aidetection.domain.vo
 * @Project：ruoyi-vue-service
 * @name：AvControlAddVo
 * @Date：2024/12/14 16:04
 * @Filename：AvControlAddVo
 */


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvControlAddVo {
    private String code;
    private String algorithmCode;
    private String objects;
    private String objectCode;
    private String modelCode;
    private Long minInterval;
    private double classThresh;
    private double overlapThresh;
    private String streamUrl;
    private Long pushStream;
    private String pushStreamUrl;
    private String recognitionRegion;
}
