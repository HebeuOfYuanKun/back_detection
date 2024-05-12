package com.ruoyi.business.uav.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ruoyi.business.uav.domain.UavMessage;
import com.ruoyi.business.uav.domain.vo.UavMessageVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav.mapper
 * @Project：ruoyi-vue-service
 * @name：aaa
 * @Date：2024/5/12 16:18
 * @Filename：aaa
 */
public interface UavMessageMapper extends BaseMapper<UavMessage> {

    Page<UavMessageVo> queryList(Page<?> page, @Param("entity") UavMessageVo entity);

    List<UavMessageVo> queryList(@Param("entity") UavMessageVo entity);

    UavMessageVo queryById(@Param("id") Long id);

}
