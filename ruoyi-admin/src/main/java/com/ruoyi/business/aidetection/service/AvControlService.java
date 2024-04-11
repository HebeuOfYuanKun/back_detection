package com.ruoyi.business.aidetection.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.business.aidetection.domain.AvControl;
import com.ruoyi.business.aidetection.domain.vo.AvControlVo;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * controlService接口
 *
 * @author yuankun
 * @date 2024-04-09
 */
@Service
public interface AvControlService extends IService<AvControl> {

    /**
     * 分页查询
     *
     * @param entity
     * @return
     */
    Map<String,Object> queryList(AvControlVo entity);

    /**
     * 查询全部
     *
     * @param entity
     * @return
     */
    List<AvControlVo> queryAll(AvControlVo entity);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    AvControlVo queryById(Long id);
}