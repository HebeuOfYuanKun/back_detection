package com.ruoyi;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;

import com.ruoyi.business.aidetection.config.Analyzer;
import com.ruoyi.business.aidetection.config.ZLMediaKit;
import com.ruoyi.business.aidetection.service.Impl.AvControlServiceImpl;
import com.ruoyi.framework.websocket.WebSocketUsers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@SpringBootTest
class RuoYiApplicationTest {

    @Autowired
    private ZLMediaKit zlMediaKit;
    @Autowired
    private Analyzer analyzer;
    @Autowired
    private AvControlServiceImpl avControlService;
    @Autowired
    private ApplicationContext applicationContext;
    @Test
    public void contextLoads() {
        System.out.println(zlMediaKit.getMediaList());
    }
    @Test
    public void testAnalyzer() {
        //System.out.println(analyzer.controls());

        //WebSocketUsers.sendMessageToUsersByText(String.valueOf(99999));
        //System.out.println(avControlService.queryList(null));
    }

    @Test
    public void test() {




        /*String baseDir = Settings.BASE_DIR;
        String baseDirLastDir = Paths.get(baseDir).getParent().toString();
        String filename = Paths.get(baseDirLastDir, "config.json").toString();*/

                try {
                    BufferedReader reader = new BufferedReader(new FileReader("D:/BaiduNetdiskDownload/download/VideoAnalyzer_v3/BXC_VideoAnalyzer_v3.1.1/config.json")) ;
                    String line;
                    StringBuilder content = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        // 拼接每一行的内容
                        content.append(line);
                    }
                    String fileContent = content.toString();
                    System.out.println(fileContent);
                    //String content = Files.readString(Path.of(filename), StandardCharsets.UTF_8);
                    JSONObject jsonObject = JSONUtil.parseObj(fileContent);

            //System.out.println("Config.<init> " + ReadConfig.class.getResource("").getPath());
            System.out.println("Config.<init> " +jsonObject+ jsonObject.get("host"));

             /*host = configData.getString("host");
            mqttAddress = configData.getString("mqttAddress");
            mqttUsername = configData.getString("mqttUsername");
            mqttPassword = configData.getString("mqttPassword");
            rootDir = configData.getString("rootDir");
            int adminPort = configData.getIntValue("adminPort");
            int analyzerPort = configData.getIntValue("analyzerPort");
            int mediaHttpPort = configData.getIntValue("mediaHttpPort");
            int mediaRtspPort = configData.getIntValue("mediaRtspPort");
            mediaSecret = configData.getString("mediaSecret");

            adminHost = "http://" + host + ":" + adminPort;
            analyzerHost = "http://" + host + ":" + analyzerPort;
            mediaHttpHost = "http://" + host + ":" + mediaHttpPort;
            mediaWsHost = "ws://" + host + ":" + mediaHttpPort;
            mediaRtspHost = "rtsp://" + host + ":" + mediaRtspPort;*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



}
