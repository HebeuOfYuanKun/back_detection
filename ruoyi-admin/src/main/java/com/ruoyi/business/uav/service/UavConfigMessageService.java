package com.ruoyi.business.uav.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.business.uav.domain.UavConfigMessage;
import com.ruoyi.business.uav.domain.UavMessage;
import com.ruoyi.business.uav.domain.vo.UavConfigMessageVo;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav.service.impl
 * @Project：ruoyi-vue-service
 * @name：ConfigMessageService
 * @Date：2024/5/12 15:54
 * @Filename：ConfigMessageService
 */
@Service
public interface UavConfigMessageService extends IService<UavConfigMessage> {

    /**
     * 分页查询
     *
     * @param entity
     * @return
     */

    TableDataInfo<UavConfigMessageVo> queryList(UavConfigMessageVo entity);

    /**
     * 查询全部
     *
     * @param entity
     * @return
     */
    List<UavConfigMessageVo> queryAll(UavConfigMessageVo entity);

    /**
     * 根据ID查询
     *
     * @param messageId
     * @return
     */
    UavConfigMessageVo queryById(Long messageId);

    /**
     * 根据配置id查询UavConfigMessage
     *
     * @param configId
     * @return
     */
    List<UavConfigMessage> queryUavConfigMessageByConfigId(Long configId);

    /**
     * 根据配置id查询UavMessage
     *
     * @param configId
     * @return
     */
    List<UavMessage> queryUavMessageByConfigId(Long configId);


    /**
     * 根据ConfigId进行删除
     *
     * @param configId
     * @return
     */
    int deleteByConfigId(Long configId);


}