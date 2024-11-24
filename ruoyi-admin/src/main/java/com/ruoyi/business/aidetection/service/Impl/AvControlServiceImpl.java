package com.ruoyi.business.aidetection.service.Impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.business.aidetection.config.Analyzer;
import com.ruoyi.business.aidetection.config.ZLMediaKit;
import com.ruoyi.business.aidetection.domain.AvControl;
import com.ruoyi.business.aidetection.domain.vo.AvControlVo;
import com.ruoyi.business.aidetection.mapper.AvControlMapper;
import com.ruoyi.business.aidetection.service.AvControlService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * controlService业务层处理
 *
 * @author yuankun
 * @date 2024-04-09
 */
@Service
public class AvControlServiceImpl extends ServiceImpl<AvControlMapper, AvControl> implements AvControlService {
    @Autowired
    private ZLMediaKit zlMediaKit;
    @Autowired
    private Analyzer analyzer;
    @Override
    public Map<String, Object> queryList(AvControlVo entity) {
        //设定媒体服务器和分析服务器状态为false,初始化媒体服务器和分析服务器在线视频流
        Boolean mediaServerState = false;
        Boolean analyzerServerState = false;
        Map<String,Map<String, Object>> mediaOnlineServerList =new HashMap<>();
        Map<String,Object> analyzerOnlineServerList = new HashMap<>();
        List<AvControlVo> avControlVoList =new ArrayList<>();
        try{
            List<Map<String, Object>> mediaServerList = zlMediaKit.getMediaList();//获取流媒体服务列表，即在线视频流

            mediaServerState = zlMediaKit.isMediaServerState();//获取流媒体服务器状态
            for (Map<String,Object> mediaServer:mediaServerList) {
                if((Boolean) mediaServer.get("active")){

                    mediaOnlineServerList.put(mediaServer.get("code").toString(),mediaServer);

                }
            }
        }catch (Exception e){
            log.error("Error while querying media servers", e);  // 使用日志记录异常

        }
        try{
            Map<String,Object> mapControls=analyzer.controls();

            analyzerServerState = analyzer.isAnalyzerServerState();
            //System.out.println(mapControls);
            if (mapControls != null && !mapControls.isEmpty()) {
                JSONArray dataArray = (JSONArray) mapControls.get("data");
                if (dataArray != null && dataArray.length() > 0) {
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject j = dataArray.optJSONObject(i);
                        if (j != null && j.has("code")) {
                            analyzerOnlineServerList.put(j.getString("code"), j);
                        }
                    }
                }
            }
            /*for (int i = 0; i < ((JSONArray)mapControls.get("data")).length(); i++) {
                JSONObject j = (JSONObject) ((JSONArray)mapControls.get("data")).get(i);

                //System.out.println("==========="+(j.getString("code")));
                analyzerOnlineServerList.put(j.getString("code"),j);
            }*/


            QueryWrapper<AvControl> queryWrapper = new QueryWrapper();
            queryWrapper.orderByAsc("sort");
            List<AvControl> avControlList = baseMapper.selectList(queryWrapper);

            List<String> codeList = new ArrayList<>();
            for (AvControl avControl:avControlList) {
                JSONObject onlineControl=null;
                codeList.add(avControl.getCode());
                String streamCode=String.format("%s_%s",avControl.getStreamApp(),avControl.getStreamName());

                AvControlVo avControlVo = new AvControlVo();
                BeanUtils.copyProperties(avControl,avControlVo);
                avControlVo.setCheckFps(0.0);
                Long currentState=0L;
                if(mediaOnlineServerList.containsKey(streamCode)){
                    avControlVo.setIsActivated(true);
                }else {
                    avControlVo.setIsActivated(false);
                }
                if(analyzerOnlineServerList.size()!=0){
                    if(analyzerOnlineServerList.containsKey(avControl.getCode())){
                         onlineControl = (JSONObject)analyzerOnlineServerList.get(avControl.getCode());
                    }else{
                        analyzer.controlCancel(avControl.getCode());
                    }
                }
                if(onlineControl!=null){
                    currentState=1L;
                    avControl.setState(1L);
                    avControlVo.setCheckFps(Double.valueOf(String.format("%.2f",onlineControl.getDouble("checkFps"))));
                }else{
                    if(avControl.getState().longValue()!=0L){
                        avControl.setState(5L);
                    }
                }
                if(currentState!=avControl.getState()){
                    //avControl.setState(currentState);
                    baseMapper.updateById(avControl);
                }
                avControlVoList.add(avControlVo);
            }


        }catch (Exception e){
            log.error("Error while querying analyzer servers", e);  // 使用日志记录异常

        }

        Map<String,Object> mapData= new HashMap<>();
        mapData.put("mediaServerState",mediaServerState);
        mapData.put("analyzerServerState",analyzerServerState);
        mapData.put("data",avControlVoList);
        return mapData;
    }

    @Override
    public List<AvControlVo> queryAll(AvControlVo entity) {
        return this.baseMapper.queryList(entity);
    }

    @Override
    public AvControlVo queryById(Long id) {
        return this.baseMapper.queryById(id);
    }
}