package com.ruoyi.business.uav.config;

import com.ruoyi.business.uav.domain.vo.UavConfigVo;
import lombok.Data;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.uav.config
 * @Project：ruoyi-vue-service
 * @name：aa
 * @Date：2024/5/12 20:21
 * @Filename：MqttConsumerConfig
 */
//接受天宇信息
@Configuration
@Data
public class MqttConsumerConfig {

    @Autowired
    private MqttConsumerCallBack mqttConsumerCallBack;

    /**
     * 客户端对象
     */
    private MqttClient client;
    private Boolean isConnected = false;


    /**
     * 客户端连接服务端
     */
    public void connect(UavConfigVo uavConfigVo) throws MqttException{

            //创建MQTT客户端对象
            client = new MqttClient(uavConfigVo.getAddress()+":"+uavConfigVo.getPort(),uavConfigVo.getUavClient(),new MemoryPersistence());
            //连接设置
            MqttConnectOptions options = new MqttConnectOptions();
            //是否清空session，设置为false表示服务器会保留客户端的连接记录，客户端重连之后能获取到服务器在客户端断开连接期间推送的消息
            //设置为true表示每次连接到服务端都是以新的身份
            options.setCleanSession(false);
            //设置连接用户名
            options.setUserName(uavConfigVo.getUsername());
            //设置连接密码
            options.setPassword(uavConfigVo.getPassword().toCharArray());
            //设置超时时间，单位为秒
            options.setConnectionTimeout(100);
            //设置心跳时间 单位为秒，表示服务器每隔1.5*20秒的时间向客户端发送心跳判断客户端是否在线
            options.setKeepAliveInterval(20);
            //设置自动重连
            options.setAutomaticReconnect(true);
            //设置遗嘱消息的话题，若客户端和服务器之间的连接意外断开，服务器将发布客户端的遗嘱信息
            options.setWill("willTopic",(uavConfigVo.getUavClient() + "与服务器断开连接").getBytes(),0,false);
            //设置回调
            client.setCallback(mqttConsumerCallBack);

            client.connect(options);
            //订阅主题
            //消息等级，和主题数组一一对应，服务端将按照指定等级给订阅了主题的客户端推送消息
            /*int[] qos = {1,1,1};
            //主题
            String[] topics = {"yukong/message/company/#","cumt/message/#","yukong/uav/state/#"};
            //订阅主题
            client.subscribe(topics,qos);*/

    }
    public void subscribe(String[] topic,int[] qos) throws MqttException{
        //订阅主题
            //消息等级，和主题数组一一对应，服务端将按照指定等级给订阅了主题的客户端推送消息
            client.subscribe(topic,qos);
    }
    /**
     * 断开连接
     */
    public void disConnect() throws Exception{

            client.disconnect();

    }

}