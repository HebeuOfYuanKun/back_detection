package com.ruoyi.business.uav.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.business.uav.domain.UavConfig;
import com.ruoyi.business.uav.domain.vo.UavConfigVo;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;


import java.util.List;
/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav.service
 * @Project：ruoyi-vue-service
 * @name：aaa
 * @Date：2024/5/12 15:25
 * @Filename：aaa
 */
@Service
public interface UavConfigService extends IService<UavConfig> {

    /**
     * 分页查询
     *
     * @param entity
     * @return
     */
    List<UavConfigVo> queryList(UavConfigVo entity);

    /**
     * 查询全部
     *
     * @param entity
     * @return
     */
    List<UavConfigVo> queryAll(UavConfigVo entity);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    UavConfigVo queryById(Long id);

    /**
     * 修改无人机配置信息
     *
     * @param uavConfigVo
     * @return
     */
    boolean updateConfigById(UavConfigVo uavConfigVo) throws Exception;


    /**
     * 新增无人机配置信息
     *
     * @param uavConfigVo
     * @return
     */
    boolean saveUavConfigVo(UavConfigVo uavConfigVo) throws Exception;

    /**
     * 连接无人机
     *
     * @param id
     * @return
     */
    boolean connectUav(Long id) throws Exception;
    /**
     * 取消连接
     *
     * @param id
     * @return
     */
    boolean disconnectUav(Long id) throws Exception;
}
