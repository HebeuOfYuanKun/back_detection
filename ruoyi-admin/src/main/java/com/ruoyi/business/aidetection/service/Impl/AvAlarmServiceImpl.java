package com.ruoyi.business.aidetection.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.business.aidetection.config.ReadConfig;
import com.ruoyi.business.aidetection.domain.AvAlarm;
import com.ruoyi.business.aidetection.domain.vo.AvAlarmVo;
import com.ruoyi.business.aidetection.mapper.AvAlarmMapper;
import com.ruoyi.business.aidetection.service.AvAlarmService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * alarmService业务层处理
 *
 * @author yuankun
 * @date 2024-04-06
 */
@Service
public class AvAlarmServiceImpl extends ServiceImpl<AvAlarmMapper, AvAlarm> implements AvAlarmService {
    @Autowired
    ReadConfig readConfig;
    @Override
    public List<AvAlarm> queryList(AvAlarmVo entity) {

            QueryWrapper<AvAlarm> queryWrapper=new QueryWrapper<>();
            if(entity.getState()!=null&&!"".equals(entity.getState())){
                queryWrapper.eq("state",entity.getState());
            }
            if(entity.getParams().get("beginTime")!=null&&!"".equals(entity.getParams().get("beginTime").toString())){
                queryWrapper.ge("create_time",entity.getParams().get("beginTime").toString());
            }
            if(entity.getParams().get("endTime")!=null&&!"".equals(entity.getParams().get("endTime").toString())){
                queryWrapper.le("create_time",entity.getParams().get("endTime").toString()+" 24");
            }
            if(entity.getCategory()!=null&&!"".equals(entity.getCategory())){
                String[] items = entity.getCategory().split(",");

                for (String item : items) {
                    queryWrapper.or().like("category",item);
                }
            }
            queryWrapper.orderByDesc("create_time");
            List<AvAlarm> avAlarmList = baseMapper.selectList(queryWrapper);
            //拼接视频和封面地址
            for (AvAlarm avAlarm : avAlarmList) {
                avAlarm.setImagePath(readConfig.getMediaHttpHost()+"/"+avAlarm.getImagePath());
                avAlarm.setVideoPath(readConfig.getMediaHttpHost()+"/"+avAlarm.getVideoPath());
            }
            return avAlarmList;


    }

    @Override
    public List<AvAlarm> queryAll(AvAlarmVo entity) {
        return baseMapper.selectList(null);
    }

    @Override
    public AvAlarmVo queryById(Long id) {
        return this.baseMapper.queryById(id);
    }
}