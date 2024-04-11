package com.ruoyi.business.aidetection.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.business.aidetection.domain.AvObject;
import com.ruoyi.business.aidetection.domain.vo.AvObjectVo;
import com.ruoyi.business.aidetection.mapper.AvObjectMapper;
import com.ruoyi.business.aidetection.service.AvObjectService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 识别物体Service业务层处理
 *
 * @author yuankun
 * @date 2024-04-06
 */
@Service
public class AvObjectServiceImpl extends ServiceImpl<AvObjectMapper, AvObject> implements AvObjectService {

    @Override
    public TableDataInfo<AvObjectVo> queryList(AvObjectVo entity) {

        return PageUtils.buildDataInfo(baseMapper.queryList(PageUtils.buildPage(), entity));
    }

    @Override
    public List<AvObject> queryAll(AvObjectVo entity) {
        return baseMapper.selectList(null);
    }

    @Override
    public AvObjectVo queryById(Long id) {
        return baseMapper.queryById(id);
    }
}