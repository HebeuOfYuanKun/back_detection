package com.ruoyi.business.uav.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.business.uav.domain.UavConfigMessage;
import com.ruoyi.business.uav.domain.vo.UavConfigMessageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav.mapper
 * @Project：ruoyi-vue-service
 * @name：aaa
 * @Date：2024/5/12 16:00
 * @Filename：aaa
 */
public interface UavConfigMessageMapper extends BaseMapper<UavConfigMessage> {

    Page<UavConfigMessageVo> queryList(Page<?> page, @Param("entity") UavConfigMessageVo entity);

    List<UavConfigMessageVo> queryList(@Param("entity") UavConfigMessageVo entity);

    UavConfigMessageVo queryById(@Param("id") Long messageId);

}

