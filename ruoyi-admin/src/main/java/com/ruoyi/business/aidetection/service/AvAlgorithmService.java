package com.ruoyi.business.aidetection.service;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.aidetection.service
 * @Project：ruoyi-vue-service
 * @name：AvAlgorithmService
 * @Date：2024/4/19 15:07
 * @Filename：AvAlgorithmService
 */

import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.business.aidetection.domain.AvAlgorithm;
import com.ruoyi.business.aidetection.domain.vo.AvAlgorithmVo;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * 算法Service接口
 *
 * @author yuankun
 * @date 2024-04-19
 */
@Service
public interface AvAlgorithmService extends IService<AvAlgorithm> {

    /**
     * 分页查询
     *
     * @param entity
     * @return
     */
    List<AvAlgorithmVo> queryList(AvAlgorithmVo entity);

    /**
     * 查询全部
     *
     * @param entity
     * @return
     */
    List<AvAlgorithmVo> queryAll(AvAlgorithmVo entity);

    /**
     * 根据ID查询
     *
     * @param id
     * @return
     */
    AvAlgorithmVo queryById(Long id);

}

