package com.ruoyi.business.uav.config;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.business.aidetection.config.Analyzer;
import com.ruoyi.business.aidetection.config.ZLMediaKit;
import com.ruoyi.business.aidetection.domain.AvControl;
import com.ruoyi.business.aidetection.service.AvControlService;
import com.ruoyi.business.uav.domain.UavConfig;
import com.ruoyi.business.uav.domain.UavMessage;
import com.ruoyi.business.uav.domain.vo.UavStateMessageVo;
import com.ruoyi.business.uav.mapper.UavConfigMapper;
import com.ruoyi.business.uav.service.UavConfigMessageService;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.web.pb.UavStateMessage;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;


/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav.config
 * @Project：ruoyi-vue-service
 * @name：aaaaa
 * @Date：2024/5/12 20:21
 * @Filename：MqttConsumerCallBack
 */
@Configuration
public class MqttConsumerCallBack implements MqttCallbackExtended {

    private RedisCache redisCache;
    @Autowired
    private UavConfigMapper uavConfigMapper;
    @Autowired
    private AvControlService avControlService;
    @Autowired
    private UavConfigMessageService uavConfigMessageService;
    @Autowired
    private Analyzer analyzer;
    @Autowired
    private ZLMediaKit zlMediaKit;
    /**
     * 客户端断开连接的回调
     */
    private int count = 1;

    @Override
    public void connectionLost(Throwable throwable) {
        //uavConfigMapper.
    }

    /**
     * 消息到达的回调
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        //System.out.println(topic);
        //ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(message.getPayload());
        //ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

        //TelemetryData telemetryData= (TelemetryData) objectInputStream.readObject();


        if (topic.contains("yukong/uav/state/1/")) {
            /*String pattern = "/([^/]+)$"; // 匹配最后一个斜杠后面的内容

            Pattern regex = Pattern.compile(pattern);
            Matcher matcher = regex.matcher(topic);

            if (matcher.find()) {
                String uavId = matcher.group(1);
                QueryWrapper<UavConfig> uavConfigQueryWrapper=new QueryWrapper<>();
                uavConfigQueryWrapper.eq("uav_id",uavId);
                uavConfigMapper.selectOne(uavConfigQueryWrapper);
                //System.out.println(extracted);
            }*/
            UavStateMessage uavStateMessage = UavStateMessage.parseFrom(message.getPayload());
            UavStateMessageVo uavStateMessageVo = new UavStateMessageVo();
            BeanUtils.copyProperties(uavStateMessage, uavStateMessageVo);
            //System.out.println(telemetryData);
            //JSONObject jsonObject=JSONUtil.parseObj(telemetryData,false);
            //Map<String>
            redisCache.setCacheObject("UavStatus", uavStateMessageVo);
        }

        if (topic.contains("yukong/message/company")) {


            String messageJson = new String(message.getPayload());
            JSONObject jsonObject = JSONUtil.parseObj(messageJson);
            //开始直播
            if ("C20001".equals(jsonObject.get("messageType").toString())) {
                Object messageMe = jsonObject.get("message");
                JSONObject mesObject = JSONUtil.parseObj(messageMe);
                Object boxSn = jsonObject.get("boxSn");
                String boxSnStr = JSONUtil.parseObj(boxSn).toString();
                //查询uav配置
                QueryWrapper<UavConfig> uavConfigQueryWrapper=new QueryWrapper<>();
                uavConfigQueryWrapper.eq("uav_id",boxSnStr);
                UavConfig uavConfig = uavConfigMapper.selectOne(uavConfigQueryWrapper);
                //查询是否此飞机订阅了该消息订阅
                if(uavConfig==null){
                    return;
                }
                List<UavMessage> uavMessages = uavConfigMessageService.queryUavMessageByConfigId(uavConfig.getId());
                int i = 0;
                for (UavMessage  uavMessage:uavMessages) {
                    if(uavMessage.getMessageTopic().contains("yukong/message/company")){
                        i++;
                        break;
                    }
                }
                if(i==0){//没有订阅该消息
                    return;
                }
                //开启识别
                String[] split = uavConfig.getUavControlId().split(",");//获取布控id
                for (String controlId:split) {
                    AvControl avControlVo = avControlService.getById(controlId);
                    if(avControlVo==null){
                        break;
                    }
                    Map<String, Object> map = analyzer.controlAdd(avControlVo.getCode(), avControlVo.getAlgorithmCode(), avControlVo.getObjectCode(), avControlVo.getMinInterval(),
                            avControlVo.getClassThresh(), avControlVo.getOverlapThresh(), zlMediaKit.getRtspUrl(avControlVo.getStreamApp(), avControlVo.getStreamName()),
                            avControlVo.getPushStream(), zlMediaKit.getRtspUrl(avControlVo.getPushStreamApp(), avControlVo.getPushStreamName()));

                    if("200".equals(map.get("code").toString()))
                        avControlVo.setState(1L);
                    avControlService.updateById(avControlVo);
                }

            }
            //结束直播
            if ("C20002".equals(jsonObject.get("messageType"))) {
                Object boxSn = jsonObject.get("boxSn");
                String boxSnStr = JSONUtil.parseObj(boxSn).toString();
                //查询uav配置
                QueryWrapper<UavConfig> uavConfigQueryWrapper=new QueryWrapper<>();
                uavConfigQueryWrapper.eq("uav_id",boxSnStr);
                UavConfig uavConfig = uavConfigMapper.selectOne(uavConfigQueryWrapper);
                //开启识别
                String[] split = uavConfig.getUavControlId().split(",");//获取布控id
                for (String controlId:split) {
                    AvControl avControlVo = avControlService.getById(controlId);
                    if(avControlVo==null){
                        break;
                    }
                    Map<String, Object> map = analyzer.controlCancel(avControlVo.getCode());

                    if("200".equals(map.get("code").toString()))
                        avControlVo.setState(0L);
                    avControlService.updateById(avControlVo);
                }
            }
        }
    }

    /**
     * 消息发布成功的回调
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    /**
     * 连接完成回调函数
     *
     * @param b 连接是否成功的布尔值
     * @param s 连接信息字符串
     */
    @Override
    public void connectComplete(boolean b, String s) {

    }
}