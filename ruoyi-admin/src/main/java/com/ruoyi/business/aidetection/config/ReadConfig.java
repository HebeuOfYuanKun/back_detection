package com.ruoyi.business.aidetection.config;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
/**
 * 配置读取类
 * 负责从 JSON 配置文件中读取相关配置信息
 */
@Data
@Component
@Order(1)
public class ReadConfig {

    private static final Logger logger = LoggerFactory.getLogger(ReadConfig.class);

    @Value("${config.file.path}")

    private String configFilePath;

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
    @PostConstruct
    public void ReadConfig() {
        try {
            // 加载并解析配置文件
            JSONObject configData = loadConfig(configFilePath);

            // 从 JSON 数据中初始化配置
            initializeConfig(configData);

            logger.info("Configuration loaded successfully. Analyzer Host: {}", analyzerHost);
        } catch (Exception e) {
            logger.error("Failed to load configuration file from path: {}", configFilePath, e);
        }
    }

    /**
     * 加载配置文件并解析为 JSON 对象
     *
     * @param filePath 配置文件路径
     * @return 解析后的 JSON 数据
     * @throws IOException 如果文件读取失败
     */
    private JSONObject loadConfig(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();

        // 使用 try-with-resources 确保资源自动关闭
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }

        return JSONUtil.parseObj(content.toString());
    }

    /**
     * 从 JSON 数据中初始化配置信息
     *
     * @param configData JSON 配置数据
     */
    private void initializeConfig(JSONObject configData) {
        this.host = configData.getStr("host");
        this.mqttAddress = configData.getStr("mqttAddress");
        this.mqttUsername = configData.getStr("mqttUsername");
        this.mqttPassword = configData.getStr("mqttPassword");
        this.rootDir = configData.getStr("rootDir");

        int adminPort = configData.getInt("adminPort");
        int analyzerPort = configData.getInt("analyzerPort");
        int mediaHttpPort = configData.getInt("mediaHttpPort");
        int mediaRtspPort = configData.getInt("mediaRtspPort");

        this.mediaSecret = configData.getStr("mediaSecret");

        this.adminHost = "http://" + host + ":" + adminPort;
        this.analyzerHost = "http://" + host + ":" + analyzerPort;
        this.mediaHttpHost = "http://" + host + ":" + mediaHttpPort;
        this.mediaWsHost = "ws://" + host + ":" + mediaHttpPort;
        this.mediaRtspHost = "rtsp://" + host + ":" + mediaRtspPort;
    }
}
