package com.ruoyi.business.aidetection.controller;

import com.ruoyi.business.aidetection.config.ZLMediaKit;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.aidetection.controller
 * @Project：ruoyi-vue-service
 * @name：AvOnlineStream
 * @Date：2024/4/8 16:25
 * @Filename：AvOnlineStream
 */
@RestController
@RequestMapping("/business/stream")
public class AvStreamController {
    @Autowired
    private ZLMediaKit zlMediaKit;
    @GetMapping("/list")
    @PreAuthorize("@ss.hasPermi('business:stream:list')")
    public AjaxResult getAvOnlineStream() {
        Map<String,Object> objectMap=new HashMap<>();
        objectMap.put("mediaList",zlMediaKit.getMediaList());
        objectMap.put("mediaServerState",zlMediaKit.isMediaServerState());
        return AjaxResult.success(objectMap);
    }
    @GetMapping("/flvurl")
    @PreAuthorize("@ss.hasPermi('business:stream:flvurl')")
    public AjaxResult getAvFlvUrl(String app,String name) {
        Map<String,Object> objectMap=new HashMap<>();
        if(app==null||name==null||"".equals(app)||"".equals(name)){
            objectMap.put("hasAdress",false);
        }else{
            objectMap.put("hasAdress",true);
            objectMap.put("flvUrl",zlMediaKit.getFlvUrl(app,name));
        }

        return AjaxResult.success(objectMap);
    }
}
