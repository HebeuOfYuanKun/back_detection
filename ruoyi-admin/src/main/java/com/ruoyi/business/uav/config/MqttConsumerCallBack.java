package com.ruoyi.business.uav.config;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.utils.GetLive;
import com.ruoyi.web.pb.TelemetryData;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
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
    /**
     * 客户端断开连接的回调
     */
    private int count = 1;

    @Override
    public void connectionLost(Throwable throwable) {

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


        if ("yukong/uav/state/1/M12220220426063".equals(topic)) {
            TelemetryData telemetryData = TelemetryData.parseFrom(message.getPayload());
            //TelemetryDataVo telemetryDataVo = new TelemetryDataVo();
            //BeanUtils.copyProperties(telemetryData, telemetryDataVo);

            //redisCache.setCacheObject("status", telemetryDataVo);
        }

        if ("yukong/message/company/1".equals(topic)) {

            String messageJson = new String(message.getPayload());
            //System.out.println(messageJson);
            //System.out.println(messageJson);
            JSONObject jsonObject = JSONUtil.parseObj(messageJson);
            if ("C20001".equals(jsonObject.get("messageType").toString())) {
                //System.out.println("开始直播");
                //if(mqttProviderConfig.)
                //获取直播地址
                //String liveRtmp = getLive.getLiveRtmpAndHTTP().get("rtmp").toString();
                Object messageMe = jsonObject.get("message");
                JSONObject mesObject = JSONUtil.parseObj(messageMe);
                Object pullUrl = mesObject.get("pullUrl");
                JSONObject pullUrlObj = JSONUtil.parseObj(pullUrl);
                String liveRtmp = pullUrlObj.get("rtmp").toString();
                //String liveHttp = getLive.getLiveRtmpAndHTTP().get("flv").toString();
                String result = liveRtmp.substring(21);
                //String res=liveHttp.substring(22);
            }
            //结束直播
            if ("C20002".equals(jsonObject.get("messageType"))) {


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