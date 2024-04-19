package com.ruoyi.business.aidetection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.ruoyi.business.aidetection.domain.AvAlgorithm;
import com.ruoyi.business.aidetection.domain.vo.AvAlgorithmVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.aidetection.mapper
 * @Project：ruoyi-vue-service
 * @name：AvAlgorithmMapper
 * @Date：2024/4/19 15:23
 * @Filename：AvAlgorithmMapper
 */
public interface AvAlgorithmMapper extends BaseMapper<AvAlgorithm> {

    Page<AvAlgorithmVo> queryList(Page<?> page, @Param("entity") AvAlgorithmVo entity);

    List<AvAlgorithmVo> queryList(@Param("entity") AvAlgorithmVo entity);

    AvAlgorithmVo queryById(@Param("id") Long id);

}
