package com.ruoyi.business.aidetection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.business.aidetection.domain.AvAlarm;
import com.ruoyi.business.aidetection.domain.vo.AvAlarmVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * alarmMapper接口
 *
 * @author yuankun
 * @date 2024-04-06
 */
public interface AvAlarmMapper extends BaseMapper<AvAlarm> {

    Page<AvAlarmVo> queryList(Page<?> page, @Param("entity") AvAlarmVo entity);

    List<AvAlarmVo> queryList(@Param("entity") AvAlarmVo entity);

    AvAlarmVo queryById(@Param("id") Long id);

}