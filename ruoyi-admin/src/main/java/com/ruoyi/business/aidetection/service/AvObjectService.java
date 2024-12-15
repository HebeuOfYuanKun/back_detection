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
    TableDataInfo<AvObject> queryList(AvObjectVo entity);

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


    /**
     * 根据对象编码查询对象信息。
     *
     * @param objectCode 对象编码
     * @return 返回包含对象信息的 AvObjectVo 对象
     */
    AvObject queryByObjectCode(String objectCode);
    /**
     * 根据对象编码查询对象信息。
     *
     * @param objectCode 对象编码
     * @return 返回包含对象信息的 AvObjectVo 对象
     */
    List<AvObject> queryByObjectCodes(List<String> objectCode);
}
