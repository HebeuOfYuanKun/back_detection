package com.ruoyi.business.aidetection.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.business.aidetection.domain.AvAlarm;
import com.ruoyi.business.aidetection.domain.vo.AvAlarmVo;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * alarmService接口
 *
 * @author yuankun
 * @date 2024-04-06
 */
@Service
public interface AvAlarmService extends IService<AvAlarm> {

    /**
     * 分页查询
     *
     * @param entity
     * @return
     */
    public List<AvAlarm> queryList(AvAlarmVo entity);

    /**
     * 查询全部
     *
     * @param entity
     * @return
     */
    List<AvAlarm> queryAll(AvAlarmVo entity);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    AvAlarmVo queryById(Long id);
}