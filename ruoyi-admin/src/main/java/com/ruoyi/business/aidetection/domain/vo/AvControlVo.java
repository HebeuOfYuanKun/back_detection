package com.ruoyi.business.aidetection.domain.vo;

import com.ruoyi.business.aidetection.domain.AvControl;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * controlVo对象 av_control
 *
 * @author yuankun
 * @date 2024-04-09
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AvControlVo extends AvControl {
    private Boolean isActivated;
    private Double checkFps;

}