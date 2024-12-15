package com.ruoyi.business.aidetection.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.business.aidetection.domain.AvObject;
import com.ruoyi.business.aidetection.domain.vo.AvObjectVo;
import com.ruoyi.business.aidetection.mapper.AvObjectMapper;
import com.ruoyi.business.aidetection.service.AvObjectService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.stereotype.Service;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
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
    public TableDataInfo<AvObject> queryList(AvObjectVo entity) {
        QueryWrapper<AvObject> queryWrapper=new QueryWrapper<>();
        if(!StringUtils.isBlank(entity.getName()))
            queryWrapper.eq("name",entity.getName());
        if(entity.getGrade()!=null)
            queryWrapper.eq("grade",entity.getGrade());
        return PageUtils.buildDataInfo(baseMapper.selectPage(PageUtils.buildPage(), queryWrapper));
    }

    @Override
    public List<AvObject> queryAll(AvObjectVo entity) {
        return baseMapper.selectList(null);
    }

    @Override
    public AvObjectVo queryById(Long id) {
        return baseMapper.queryById(id);
    }


    @Override
    public AvObject queryByObjectCode(String objectCode) {
        QueryWrapper<AvObject> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("code",objectCode);
        AvObject avObject=getOne(queryWrapper,false);
        return avObject;
    }

    @Override
    public List<AvObject> queryByObjectCodes(List<String> objectCodes) {
        List<AvObject> avObjects=new ArrayList<>();
        for(String objectCode:objectCodes){
            QueryWrapper<AvObject> queryWrapper=new QueryWrapper<>();
            queryWrapper.eq("code",objectCode);
            AvObject avObject=getOne(queryWrapper,false);
            if(avObject!=null)
                avObjects.add(avObject);
        }
        return avObjects;
    }
}