package com.ruoyi.business.aidetection.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.business.aidetection.domain.AvObject;
import com.ruoyi.business.aidetection.domain.vo.AvObjectVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 识别物体Mapper接口
 *
 * @author yuankun
 * @date 2024-04-06
 */
public interface AvObjectMapper extends BaseMapper<AvObject> {

    Page<AvObjectVo> queryList(Page<?> page, @Param("entity") AvObjectVo entity);

    List<AvObjectVo> queryList(@Param("entity") AvObjectVo entity);

    AvObjectVo queryById(@Param("id") Long id);

}