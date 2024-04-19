package com.ruoyi.business.aidetection.service.Impl;

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
    public TableDataInfo<AvAlgorithmVo> queryList(AvAlgorithmVo entity) {
        return PageUtils.buildDataInfo(this.baseMapper.queryList(PageUtils.buildPage(), entity));
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

    @Override
    public AvAlgorithmVo queryById(Long id) {
        return this.baseMapper.queryById(id);
    }
}
