package com.ruoyi.business.aidetection.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.business.aidetection.domain.AvControl;
import com.ruoyi.business.aidetection.domain.vo.AvControlVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * controlMapper接口
 *
 * @author yuankun
 * @date 2024-04-09
 */
public interface AvControlMapper extends BaseMapper<AvControl> {

    Page<AvControlVo> queryList(Page<?> page, @Param("entity") AvControlVo entity);

    List<AvControlVo> queryList(@Param("entity") AvControlVo entity);

    AvControlVo queryById(@Param("id") Long id);

}