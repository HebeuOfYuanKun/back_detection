package com.ruoyi.business.uav.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.business.uav.domain.UavConfigMessage;
import com.ruoyi.business.uav.domain.vo.UavConfigMessageVo;
import com.ruoyi.business.uav.mapper.UavConfigMessageMapper;
import com.ruoyi.business.uav.service.UavConfigMessageService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav.service.impl
 * @Project：ruoyi-vue-service
 * @name：aaa
 * @Date：2024/5/12 15:58
 * @Filename：aaa
 */
@Service
public class UavConfigMessageServiceImpl extends ServiceImpl<UavConfigMessageMapper, UavConfigMessage> implements UavConfigMessageService {

    @Override
    public TableDataInfo<UavConfigMessageVo> queryList(UavConfigMessageVo entity) {
        return PageUtils.buildDataInfo(this.baseMapper.queryList(PageUtils.buildPage(), entity));
    }

    @Override
    public List<UavConfigMessageVo> queryAll(UavConfigMessageVo entity) {
        return this.baseMapper.queryList(entity);
    }

    @Override
    public UavConfigMessageVo queryById(Long messageId) {
        return this.baseMapper.queryById(messageId);
    }

    @Override
    public List<UavConfigMessage> queryByConfigId(Long configId) {
        QueryWrapper<UavConfigMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("config_id", configId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public int deleteByConfigId(Long configId) {
        QueryWrapper<UavConfigMessage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("config_id", configId);

        return baseMapper.delete(queryWrapper);
    }
}

