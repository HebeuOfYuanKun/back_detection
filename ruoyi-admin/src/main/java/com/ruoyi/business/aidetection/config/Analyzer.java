/*
package com.ruoyi.business.aidetection.config;

*/
/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.aidetection.config
 * @Project：ruoyi-vue-service
 * @name：Analyzer
 * @Date：2024/4/9 21:24
 * @Filename：Analyzer
 *//*

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@Data
@Slf4j
public class Analyzer {
    @Autowired
    private ReadConfig readConfig;
    private String analyzerHost;
    private int timeout=1;
    private boolean analyzerServerState=false;

    public Analyzer(ReadConfig readConfig) {
        this.readConfig=readConfig;
        this.analyzerHost=readConfig.getAnalyzerHost();
    }


    public Map<String,Object> controls() {
        boolean state = false;
        String msg = "error";
        JSONArray data = new JSONArray();

        try {
            URL url = new URL(analyzerHost + "/api/controls");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(timeout * 1000);
            connection.setDoOutput(true);

            JSONObject requestData = new JSONObject();

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestData.toString().getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject responseJson = new JSONObject(response.toString());
                msg = responseJson.getString("msg");
                if (responseJson.getInt("code") == 1000) {
                    JSONArray responseData = responseJson.getJSONArray("data");
                    if (responseData != null) {
                        data = responseData;
                    }
                    state = true;
                }
            } else {
                msg = "status_code=" + responseCode;
            }

            connection.disconnect();
            analyzerServerState = true;
        } catch (Exception e) {
            analyzerServerState = false;
            e.printStackTrace();
            msg = e.toString();
        }
        Map<String,Object> map=new HashMap<>();
        map.put("state", state);
        map.put("msg", msg);
        map.put("data", data);
        return map;
    }

    public Map<String,Object> control(String code) {
        boolean state = false;
        String msg = "error";
        JSONObject control = new JSONObject();

        try {
            URL url = new URL(analyzerHost + "/api/control");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(timeout * 1000);
            connection.setDoOutput(true);

            JSONObject requestData = new JSONObject();
            requestData.put("code", code);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestData.toString().getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject responseJson = new JSONObject(response.toString());
                msg = responseJson.getString("msg");
                if (responseJson.getInt("code") == 1000) {
                    control = responseJson.optJSONObject("control");
                    state = true;
                }
            } else {
                msg = "status_code=" + responseCode;
            }

            connection.disconnect();
            analyzerServerState = true;
        } catch (Exception e) {
            analyzerServerState = false;
            msg = e.toString();
        }

        Map<String,Object> map=new HashMap<>();
        map.put("state", state);
        map.put("msg", msg);
        map.put("data", control);
        return map;
    }
    */
/**
     * 控制添加操作
     *
     * @param code 设备代码
     * @param algorithmCode 算法代码
     * @param objects 识别目标参数
     * @param objectCode 预警目标代码
     * @param minInterval 最小间隔时间
     * @param classThresh 分类阈值
     * @param overlapThresh 重叠阈值
     * @param streamUrl 流媒体URL
     * @param pushStream 是否推送流，1表示推送，0表示不推送
     * @param pushStreamUrl 推送流媒体URL
     * @return 包含操作结果的状态码和消息信息的Map对象
     *//*


    public Map<String,Object> controlAdd(String code, String algorithmCode,String objects, String objectCode, Long minInterval, double classThresh, double overlapThresh, String streamUrl,Long pushStream, String pushStreamUrl) {
        int state_code = 500;
        String msg = "error";

        try {
            URL url = new URL(analyzerHost + "/api/control/add");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(timeout * 1000);
            connection.setDoOutput(true);

            JSONObject requestData = new JSONObject();
            requestData.put("code", code);
            requestData.put("objects", objects);
            requestData.put("algorithmCode", algorithmCode);
            requestData.put("objectCode", objectCode);
            requestData.put("minInterval", Long.toString(minInterval));
            requestData.put("classThresh", Double.toString(classThresh));
            requestData.put("overlapThresh", Double.toString(overlapThresh));
            requestData.put("streamUrl", streamUrl);
            requestData.put("pushStream", pushStream==1);
            requestData.put("pushStreamUrl", pushStreamUrl);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestData.toString().getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject responseJson = new JSONObject(response.toString());
                msg = responseJson.getString("msg");
                if (responseJson.getInt("code") == 1000) {
                    state_code = 200;
                }
            } else {
                msg = "state_code=" + responseCode;
            }

            connection.disconnect();
            analyzerServerState = true;
        } catch (Exception e) {
            e.printStackTrace();
            analyzerServerState = false;
            msg = "视频分析器未启动，请启动！";
        }

        Map<String,Object> map=new HashMap<>();
        map.put("code", state_code);
        map.put("msg", msg);
        return map;
    }

    public Map<String,Object> controlCancel(String code) {
        int state_code = 500;
        String msg = "error";

        try {
            URL url = new URL(analyzerHost + "/api/control/cancel");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(timeout * 1000);
            connection.setDoOutput(true);

            JSONObject requestData = new JSONObject();
            requestData.put("code", code);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(requestData.toString().getBytes());
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject responseJson = new JSONObject(response.toString());
                msg = responseJson.getString("msg");
                if (responseJson.getInt("code") == 1000) {
                    state_code = 200;
                }
            } else {
                msg = "status_code=" + responseCode;
            }

            connection.disconnect();
            analyzerServerState = true;
        } catch (Exception e) {
            analyzerServerState = false;
            msg = "视频分析器未启动，请启动！";
        }

        Map<String,Object> map=new HashMap<>();
        map.put("code", state_code);
        map.put("msg", msg);
        return map;
    }
}*/
package com.ruoyi.business.aidetection.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Component
@Data
@Slf4j
@DependsOn("readConfig")

public class Analyzer {

    private static final String API_CONTROLS = "/api/controls";
    private static final String API_CONTROL = "/api/control";
    private static final String API_CONTROL_ADD = "/api/control/add";
    private static final String API_CONTROL_CANCEL = "/api/control/cancel";

    @Autowired
    private ReadConfig readConfig;

    private String analyzerHost;
    private int timeout = 1;
    private boolean analyzerServerState = false;

    public Analyzer(ReadConfig readConfig) {
        this.readConfig = readConfig;
        this.analyzerHost = readConfig.getAnalyzerHost();
    }

    /**
     * 获取所有控制信息
     */
    public Map<String, Object> controls() {
        return sendRequest(API_CONTROLS, createSafeJson().toString(), "controls");
    }

    /**
     * 获取单个控制信息
     */
    public Map<String, Object> control(String code) {
        JSONObject requestData = createSafeJson("code", code);
        if (requestData == null) {
            return createErrorResponse("Failed to create JSON for control request");
        }
        return sendRequest(API_CONTROL, requestData.toString(), "control");
    }

    /**
     * 添加控制信息
     */
    public Map<String, Object> controlAdd(String code, String algorithmCode, String objects, String objectCode,
                                          Long minInterval, double classThresh, double overlapThresh,
                                          String streamUrl, Long pushStream, String pushStreamUrl) {

        JSONObject requestData = createSafeJson();
        try {
            requestData.put("code", code);
            requestData.put("objects", objects);
            requestData.put("algorithmCode", algorithmCode);
            requestData.put("objectCode", objectCode);
            requestData.put("minInterval", minInterval);
            requestData.put("classThresh", classThresh);
            requestData.put("overlapThresh", overlapThresh);
            requestData.put("streamUrl", streamUrl);
            requestData.put("pushStream", pushStream == 1);
            requestData.put("pushStreamUrl", pushStreamUrl);
        } catch (Exception e) {
            log.error("Error creating JSON for controlAdd: {}", e.getMessage(), e);
            return createErrorResponse("Failed to create JSON for controlAdd request");
        }

        return sendRequest(API_CONTROL_ADD, requestData.toString(), "controlAdd");
    }

    /**
     * 取消控制
     */
    public Map<String, Object> controlCancel(String code) {
        JSONObject requestData = createSafeJson("code", code);
        if (requestData == null) {
            return createErrorResponse("Failed to create JSON for controlCancel request");
        }
        return sendRequest(API_CONTROL_CANCEL, requestData.toString(), "controlCancel");
    }

    /**
     * 通用发送 HTTP 请求方法
     */
    private Map<String, Object> sendRequest(String apiEndpoint, String requestData, String action) {
        String code = "500";
        String msg = "Error occurred";
        Object data = null;

        try {
            // 构建 URL 和连接
            URL url = new URL(analyzerHost + apiEndpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setConnectTimeout(timeout * 1000);
            connection.setDoOutput(true);

            // 发送请求
            try (OutputStream outputStream = connection.getOutputStream()) {
                outputStream.write(requestData.getBytes());
                outputStream.flush();
            }

            // 处理响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder responseBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    JSONObject responseJson = new JSONObject(responseBuilder.toString());
                    msg = responseJson.optString("msg", "No message");

                    if (responseJson.optInt("code", -1) == 1000) {
                        code = "200";
                        data = responseJson.opt("data");
                    }
                }
            } else {
                msg = "HTTP Error: status_code=" + responseCode;
                log.warn("{} request failed with status code: {}", action, responseCode);
            }

            connection.disconnect();
            analyzerServerState = true;
        } catch (Exception e) {
            analyzerServerState = false;
            msg = "Analyzer server is not running or request failed!";
            log.error("{} request failed: {}", action, e.getMessage(), e);
        }

        // 构建结果
        Map<String, Object> result = new HashMap<>();
        result.put("code", code);
        result.put("msg", msg);
        result.put("data", data);
        return result;
    }

    /**
     * 安全创建 JSON 对象
     */
    private JSONObject createSafeJson() {
        try {
            return new JSONObject();
        } catch (Exception e) {
            log.error("Error creating empty JSON object: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 安全创建带参数的 JSON 对象
     */
    private JSONObject createSafeJson(String key, Object value) {
        try {
            return new JSONObject().put(key, value);
        } catch (Exception e) {
            log.error("Error creating JSON object with key: {}, value: {}, error: {}", key, value, e.getMessage(), e);
            return null;
        }
    }

    /**
     * 创建错误响应
     */
    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("code", "500");
        errorResponse.put("msg", message);
        errorResponse.put("data", null);
        return errorResponse;
    }
}

