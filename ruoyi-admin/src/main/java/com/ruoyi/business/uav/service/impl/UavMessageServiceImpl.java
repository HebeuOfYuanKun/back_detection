package com.ruoyi.business.uav.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.business.uav.domain.vo.UavMessageVo;
import com.ruoyi.business.uav.service.UavMessageService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.business.uav.domain.UavMessage;
import com.ruoyi.business.uav.mapper.UavMessageMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav.service.impl
 * @Project：ruoyi-vue-service
 * @name：qqq
 * @Date：2024/5/12 16:20
 * @Filename：UavMessageServiceImpl
 */
@Service
public class UavMessageServiceImpl extends ServiceImpl<UavMessageMapper, UavMessage> implements UavMessageService {

    @Override
    public TableDataInfo<UavMessageVo> queryList(UavMessageVo entity) {
        return PageUtils.buildDataInfo(this.baseMapper.queryList(PageUtils.buildPage(), entity));
    }

    @Override
    public List<UavMessageVo> queryAll(UavMessageVo entity) {
        return this.baseMapper.queryList(entity);
    }

    @Override
    public UavMessage queryById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public UavMessage queryByTopic(String topic) {
        QueryWrapper<UavMessage>  queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("message_topic", topic);
        return baseMapper.selectOne(queryWrapper);

    }
}
