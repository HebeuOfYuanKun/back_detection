package com.ruoyi.business.aidetection.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.business.aidetection.domain.AvAlgorithm;
import com.ruoyi.business.aidetection.domain.vo.AvAlgorithmVo;
import com.ruoyi.business.aidetection.mapper.AvAlgorithmMapper;
import com.ruoyi.business.aidetection.service.AvAlgorithmService;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.PageUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.aidetection.service.Impl
 * @Project：ruoyi-vue-service
 * @name：aaa
 * @Date：2024/4/19 15:18
 * @Filename：aaa
 */
@Service
public class AvAlgorithmServiceImpl extends ServiceImpl<AvAlgorithmMapper, AvAlgorithm> implements AvAlgorithmService {

    @Override
    public List<AvAlgorithmVo> queryList(AvAlgorithmVo entity) {

        QueryWrapper queryWrapper = new QueryWrapper<>();
        if(entity.getName()!=null&&!"".equals(entity.getName())){
            queryWrapper.like("name",entity.getName());
        }
        if(entity.getModelName()!=null&&!"".equals(entity.getModelName())){
            queryWrapper.like("model_name",entity.getModelName());
        }
        List<AvAlgorithmVo> list = baseMapper.selectList(queryWrapper);

        return list;
    }

    @Override
    public List<AvAlgorithmVo> queryAll(AvAlgorithmVo entity) {//查询所有算法
        List<AvAlgorithm> avAlgorithms = baseMapper.selectList(null);
        List<AvAlgorithmVo> avAlgorithmVos = new ArrayList<>();
        for (AvAlgorithm avAlgorithm:avAlgorithms) {
            AvAlgorithmVo avAlgorithmVo = new AvAlgorithmVo();
            BeanUtils.copyProperties(avAlgorithm, avAlgorithmVo);
            avAlgorithmVos.add(avAlgorithmVo);
        }
        return avAlgorithmVos;
    }
    /**
     * 根据算法代码查询算法列表
     *
     * @param AlgorithmCode 算法代码
     * @return 算法，若未找到匹配的算法，则返回空
     */
    @Override
    public AvAlgorithmVo queryByAlgorithmCode(String AlgorithmCode) throws RuntimeException{
        QueryWrapper<AvAlgorithm> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("algorithm_code",AlgorithmCode);
        AvAlgorithm avAlgorithm = baseMapper.selectOne(queryWrapper);
        AvAlgorithmVo avAlgorithmVo = new AvAlgorithmVo();
        BeanUtils.copyProperties(avAlgorithm, avAlgorithmVo);
        return avAlgorithmVo;
    }

    @Override
    public AvAlgorithmVo queryById(Long id) {
        AvAlgorithm avAlgorithm = baseMapper.selectById(id);
        AvAlgorithmVo avAlgorithmVo =new AvAlgorithmVo();
        BeanUtils.copyProperties(avAlgorithm, avAlgorithmVo);
        return avAlgorithmVo;
    }

    @Override
    public boolean save(AvAlgorithm entity) {
        return super.save(entity);
    }
}
