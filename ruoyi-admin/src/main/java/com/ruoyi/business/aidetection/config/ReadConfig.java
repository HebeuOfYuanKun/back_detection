package com.ruoyi.business.aidetection.config;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Data
@Component
public class ReadConfig {

    private String host;
    private String rootDir;
    private String mqttAddress;
    private String mqttUsername;
    private String mqttPassword;
    private String adminHost;
    private String analyzerHost;
    private String mediaHttpHost;
    private String mediaWsHost;
    private String mediaRtspHost;
    private String mediaSecret;

    public ReadConfig() {
        /*String baseDir = Settings.BASE_DIR;
        String baseDirLastDir = Paths.get(baseDir).getParent().toString();
        String filename = Paths.get(baseDirLastDir, "config.json").toString();*/

        try {
            BufferedReader reader = new BufferedReader(new FileReader("D:/BaiduNetdiskDownload/download/VideoAnalyzer_v3/BXC_VideoAnalyzer_v3.2/config.json")) ;
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                // 拼接每一行的内容
                content.append(line);
            }
            String fileContent = content.toString();
            //System.out.println(fileContent);
            //String content = Files.readString(Path.of(filename), StandardCharsets.UTF_8);
            JSONObject configData = JSONUtil.parseObj(fileContent);

            System.out.println(configData);


            host = configData.getStr( "host" );
            mqttAddress = configData.getStr("mqttAddress");
            mqttUsername = configData.getStr("mqttUsername");
            mqttPassword = configData.getStr("mqttPassword");
            rootDir = configData.getStr("rootDir");
            int adminPort = configData.getInt("adminPort");
            int analyzerPort = configData.getInt("analyzerPort");
            int mediaHttpPort = configData.getInt("mediaHttpPort");
            int mediaRtspPort = configData.getInt("mediaRtspPort");
            mediaSecret = configData.getStr("mediaSecret");

            adminHost = "http://" + host + ":" + adminPort;
            analyzerHost = "http://" + host + ":" + analyzerPort;
            mediaHttpHost = "http://" + host + ":" + mediaHttpPort;
            mediaWsHost = "ws://" + host + ":" + mediaHttpPort;
            mediaRtspHost = "rtsp://" + host + ":" + mediaRtspPort;
            System.out.println(analyzerHost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}