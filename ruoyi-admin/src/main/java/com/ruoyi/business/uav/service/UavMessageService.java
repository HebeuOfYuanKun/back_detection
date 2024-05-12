package com.ruoyi.business.uav.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.business.uav.domain.vo.UavMessageVo;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.business.uav.domain.UavMessage;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav.service
 * @Project：ruoyi-vue-service
 * @name：ddd
 * @Date：2024/5/12 16:19
 * @Filename：ddd
 */
@Service
public interface UavMessageService extends IService<UavMessage> {

    /**
     * 分页查询
     *
     * @param entity
     * @return
     */
    TableDataInfo<UavMessageVo> queryList(UavMessageVo entity);

    /**
     * 查询全部
     *
     * @param entity
     * @return
     */
    List<UavMessageVo> queryAll(UavMessageVo entity);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    UavMessage queryById(Long id);

    /**
     * 根据topic查询
     *
     * @param topic
     * @return
     */
    UavMessage queryByTopic(String topic);
}

