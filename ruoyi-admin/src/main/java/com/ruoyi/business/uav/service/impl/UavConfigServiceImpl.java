package com.ruoyi.business.uav.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.business.aidetection.config.Analyzer;
import com.ruoyi.business.aidetection.config.ZLMediaKit;
import com.ruoyi.business.aidetection.domain.AvControl;
import com.ruoyi.business.aidetection.service.AvControlService;
import com.ruoyi.business.uav.config.MqttConsumerConfig;
import com.ruoyi.business.uav.domain.UavConfig;
import com.ruoyi.business.uav.domain.UavConfigMessage;
import com.ruoyi.business.uav.domain.UavMessage;
import com.ruoyi.business.uav.domain.vo.UavConfigVo;
import com.ruoyi.business.uav.mapper.UavConfigMapper;
import com.ruoyi.business.uav.service.UavConfigMessageService;
import com.ruoyi.business.uav.service.UavConfigService;
import com.ruoyi.business.uav.service.UavMessageService;
import com.ruoyi.common.utils.StringUtils;
import org.hamcrest.text.IsEmptyString;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav.service.impl
 * @Project：ruoyi-vue-service
 * @name：cc
 * @Date：2024/5/12 15:27
 * @Filename：cc
 */
@Service
public class UavConfigServiceImpl extends ServiceImpl<UavConfigMapper, UavConfig> implements UavConfigService {

    @Autowired
    private UavConfigMessageService uavConfigMessageService;
    @Autowired
    private UavMessageService uavMessageService;
    @Autowired
    private MqttConsumerConfig mqttConsumerConfig;

    @Autowired
    private AvControlService avControlService;
    @Autowired
    private Analyzer analyzer;
    @Autowired
    private ZLMediaKit zlMediaKit;


    @Override
    public List<UavConfigVo> queryList(UavConfigVo entity) {
        //查询符合条件的无人机配置信息
        QueryWrapper<UavConfig> queryWrapper = new QueryWrapper<>();
        if(entity.getUavBrand() != null||"".equals(entity.getUavBrand()))
            queryWrapper.like("uav_brand", entity.getUavBrand());
        if(entity.getUavModel() != null||"".equals(entity.getUavBrand()))
            queryWrapper.like("uav_model", entity.getUavModel());
        if(entity.getState() != null)
            queryWrapper.eq("state", entity.getState());

        List<UavConfig> uavConfigList = baseMapper.selectList(queryWrapper);
        List<UavConfigVo> uavConfigVoList = new ArrayList<>();
        //查找无人机的订阅信息
        for (UavConfig uavConfig : uavConfigList) {
            UavConfigVo uavConfigVo = new UavConfigVo();
            BeanUtils.copyProperties(uavConfig, uavConfigVo);
            //先查询关联表中无人机配置的所有订阅信息的id
            List<UavConfigMessage> uavConfigMessageList = uavConfigMessageService.queryUavConfigMessageByConfigId(uavConfigVo.getId());
            List<String> messageTopic = new ArrayList<>();
            for (UavConfigMessage uavConfigMessage:uavConfigMessageList) {
                //根据订阅信息id查询订阅topic
                messageTopic.add(uavMessageService.queryById(uavConfigMessage.getMessageId()).getMessageTopic());
            }
            //如果有订阅信息就拼接成字符串
            if(messageTopic.size() > 0)
                uavConfigVo.setMessageTopic(String.join(",", messageTopic));
            uavConfigVoList.add(uavConfigVo);
            //查询无人机的布控信息
            String[] split = uavConfigVo.getUavControlId().split(",");
            List<String> ControlName = new ArrayList<>();
            for (String ControlId:split) {
                AvControl avControl = avControlService.getById(ControlId);
                if(avControl!=null){
                    ControlName.add(avControl.getName());
                }
            }
            if(ControlName.size() > 0){
                uavConfigVo.setUavControlName(String.join(",", ControlName));
            }

        }
        return uavConfigVoList;
    }

    @Override
    public List<UavConfigVo> queryAll(UavConfigVo entity) {
        return this.baseMapper.queryList(entity);
    }

    @Override
    public UavConfigVo queryById(Long id) {
        //查询符合条件的无人机配置信息
        UavConfig uavConfig = baseMapper.selectById(id);
        //查找无人机的订阅信息
        UavConfigVo uavConfigVo = new UavConfigVo();
        BeanUtils.copyProperties(uavConfig, uavConfigVo);
        //先查询关联表中无人机配置的所有订阅信息的id
        List<UavConfigMessage> uavConfigMessageList = uavConfigMessageService.queryUavConfigMessageByConfigId(uavConfigVo.getId());
        List<String> messageTopic = new ArrayList<>();
        for (UavConfigMessage uavConfigMessage:uavConfigMessageList) {
            //根据订阅信息id查询订阅topic
            messageTopic.add(uavMessageService.queryById(uavConfigMessage.getMessageId()).getMessageTopic());
        }
        //如果有订阅信息就拼接成字符串
        if(messageTopic.size() > 0)
            uavConfigVo.setMessageTopic(String.join(",", messageTopic));



        return uavConfigVo;
    }

    @Override
    @Transactional//防止出现脏数据
    public boolean updateConfigById(UavConfigVo uavConfigVo) throws Exception {

        UavConfig uavConfig = baseMapper.selectById(uavConfigVo.getId());
        BeanUtils.copyProperties(uavConfigVo, uavConfig);
        baseMapper.updateById(uavConfig);
        //删除所有的关联表信息
        uavConfigMessageService.deleteByConfigId(uavConfigVo.getId());
        List<String> split = new ArrayList<>();
        if(uavConfigVo.getMessageTopic()!=null){
            split= Arrays.asList(uavConfigVo.getMessageTopic().split(","));
        }else{
            throw new Exception("请添加无人机订阅的主题！");
        }



        for (String messageTopic:split) {
           if(uavMessageService.queryByTopic(messageTopic)==null){
               throw new Exception("您所添加的订阅主题不存在！请先添加订阅主题！");
           }
           uavConfigMessageService.save(new UavConfigMessage(uavMessageService.queryByTopic(messageTopic).getId(),uavConfigVo.getId()));



        }
        return true;
    }

    @Override
    @Transactional//防止出现脏数据
    public boolean saveUavConfigVo(UavConfigVo uavConfigVo) throws Exception {
        QueryWrapper<UavConfig> queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(uavConfigVo.getUavId())&&!StringUtils.isEmpty(uavConfigVo.getAddress())&&!StringUtils.isEmpty(uavConfigVo.getUavClient())&&
                !StringUtils.isEmpty(uavConfigVo.getUsername())&&!StringUtils.isEmpty(uavConfigVo.getPassword())&&
                !StringUtils.isEmpty(uavConfigVo.getPort().toString())){
            queryWrapper.eq("uav_id", uavConfigVo.getUavId());
            queryWrapper.eq("address", uavConfigVo.getAddress());
            queryWrapper.eq("uav_client", uavConfigVo.getUavClient());
            queryWrapper.eq("username", uavConfigVo.getUsername());
            queryWrapper.eq("port", uavConfigVo.getPort());
            if(baseMapper.selectList(queryWrapper).size() > 0){
                throw new Exception("该无人机配置信息已存在！");
            }else{
                if(!StringUtils.isEmpty(uavConfigVo.getMessageTopic())){//检测是否已经存在订阅消息主题
                    if(uavMessageService.queryByTopic(uavConfigVo.getMessageTopic())==null){
                        throw new Exception("您无人机所添加的订阅主题不存在！请先去消息管理添加订阅主题！");
                    }
                    uavConfigMessageService.save(new UavConfigMessage(uavMessageService.queryByTopic(uavConfigVo.getMessageTopic()).getId(),uavConfigVo.getId()));
                }

                baseMapper.insert(uavConfigVo);
        }
        }else{
            throw new Exception("请填写完整无人机配置信息！");
        }
        return true;
    }

    @Override
    @Transactional
    public boolean connectUav(Long id) throws Exception {
        UavConfigVo uavConfigVo = queryById(id);
        if(StringUtils.isEmpty(uavConfigVo.getMessageTopic())){
            throw new Exception("无订阅消息，先添加订阅再连接！");
        }

        //连接Mqtt网关
        mqttConsumerConfig.connect(uavConfigVo);
        //订阅主题
        String[] split = uavConfigVo.getMessageTopic().split(",");
        int[] qos=new int[split.length];
        int i=0;
        for (String messageTopic:split) {
            UavMessage uavMessage = uavMessageService.queryByTopic(messageTopic);
            qos[i]=uavMessage.getMessageQos().intValue();;
            i++;
        }

        mqttConsumerConfig.subscribe(split,qos);
        mqttConsumerConfig.setIsConnected(true);
        uavConfigVo.setState(1L);
        baseMapper.updateById(uavConfigVo);
        return true;
    }

    @Override
    @Transactional
    public boolean disconnectUav(Long id) throws Exception {
        UavConfigVo uavConfigVo = queryById(id);
        if(uavConfigVo.getState()!=1){
            throw new Exception("当前不是处于连接状态！");
        }
        if(StringUtils.isEmpty(uavConfigVo.getMessageTopic())){
            throw new Exception("无订阅消息，先添加订阅再取消连接！");
        }

        //设定无人机离线
        uavConfigVo.setUavState(0);
        //设定无人机识别服务状态
        uavConfigVo.setUavAiState(0);
        //取消识别服务
        if(uavConfigVo.getUavId()!=null){
            QueryWrapper<AvControl> avControlQueryWrapper=new QueryWrapper<>();
            avControlQueryWrapper.eq("uav_sn",uavConfigVo.getUavId());
            avControlService.list(avControlQueryWrapper).forEach(avControl->{
                Map<String, Object> map = analyzer.controlCancel(avControl.getCode());
                if("200".equals(map.get("code").toString()))
                    avControl.setState(0L);
                avControlService.updateById(avControl);
            });
        }
        //取消连接Mqtt网关
        System.out.println(mqttConsumerConfig.getIsConnected());
        if(mqttConsumerConfig.getIsConnected()){

            mqttConsumerConfig.disConnect();
        }
        //设定离线状态
        uavConfigVo.setState(0L);
        mqttConsumerConfig.setIsConnected(false);
        baseMapper.updateById(uavConfigVo);
        return true;
    }
}

