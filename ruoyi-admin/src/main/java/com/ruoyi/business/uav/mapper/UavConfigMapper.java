package com.ruoyi.business.uav.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.business.uav.domain.UavConfig;
import com.ruoyi.business.uav.domain.vo.UavConfigVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav.mapper
 * @Project：ruoyi-vue-service
 * @name：aa
 * @Date：2024/5/12 15:23
 * @Filename：aa
 */
public interface UavConfigMapper extends BaseMapper<UavConfig> {

    Page<UavConfigVo> queryList(Page<?> page, @Param("entity") UavConfigVo entity);

    List<UavConfigVo> queryList(@Param("entity") UavConfigVo entity);

    UavConfigVo queryById(@Param("id") Long id);

}

