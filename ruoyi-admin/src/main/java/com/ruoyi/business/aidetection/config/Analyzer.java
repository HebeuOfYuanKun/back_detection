package com.ruoyi.business.aidetection.config;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.aidetection.config
 * @Project：ruoyi-vue-service
 * @name：Analyzer
 * @Date：2024/4/9 21:24
 * @Filename：Analyzer
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class Analyzer {
    private ReadConfig readConfig;
    private String analyzerHost;
    private int timeout=10;
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
                System.out.println(response);
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

    public Map<String,Object> controlAdd(String code, String algorithmCode, String objectCode, int minInterval, double classThresh, double overlapThresh, String streamUrl, boolean pushStream, String pushStreamUrl) {
        boolean state = false;
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
            requestData.put("algorithmCode", algorithmCode);
            requestData.put("objectCode", objectCode);
            requestData.put("minInterval", Integer.toString(minInterval));
            requestData.put("classThresh", Double.toString(classThresh));
            requestData.put("overlapThresh", Double.toString(overlapThresh));
            requestData.put("streamUrl", streamUrl);
            requestData.put("pushStream", pushStream);
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
        return map;
    }

    public Map<String,Object> controlCancel(String code) {
        boolean state = false;
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
        return map;
    }
}