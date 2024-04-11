package com.ruoyi.business.aidetection.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.business.aidetection.domain.AvObject;
import com.ruoyi.business.aidetection.domain.vo.AvObjectVo;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 识别物体Service接口
 *
 * @author yuankun
 * @date 2024-04-06
 */
@Service
public interface AvObjectService extends IService<AvObject> {

    /**
     * 分页查询
     *
     * @param entity
     * @return
     */
    TableDataInfo<AvObjectVo> queryList(AvObjectVo entity);

    /**
     * 查询全部
     *
     * @param entity
     * @return
     */
    List<AvObject> queryAll(AvObjectVo entity);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    AvObjectVo queryById(Long id);
}
