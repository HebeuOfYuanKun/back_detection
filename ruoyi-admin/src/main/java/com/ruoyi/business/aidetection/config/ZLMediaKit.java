/*
package com.ruoyi.business.aidetection.config;

*/
/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.aidetection.config
 * @Project：ruoyi-vue-service
 * @name：ZLMediaKit
 * @Date：2024/4/7 21:29
 * @Filename：ZLMediaKit
 *//*

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@Data
@Slf4j
@DependsOn("readConfig")
public class ZLMediaKit {
    @Autowired
    private ReadConfig config;
    private String defaultPushStreamApp = "analyzer";
    private int timeout = 1;
    private boolean mediaServerState = false;

    public ZLMediaKit(ReadConfig config) {
        this.config = config;
    }

    private String byteFormat(double bytes) {
        double factor = 1024;
        for (String unit : new String[]{"", "K", "M", "G"}) {
            if (bytes < factor) {
                return String.format("%.2f%sbps", bytes, unit);
            }
            bytes /= factor;
        }
        return "";
    }

    public String getHlsUrl(String app, String name) {
        return String.format("%s/%s/%s.hls.m3u8", config.getMediaHttpHost(), app, name);
    }

    public String getFlvUrl(String app, String name) {
        return String.format("%s/%s/%s.live.flv", config.getMediaHttpHost(), app, name);
    }

    public String getRtspUrl(String app, String name) {
        return String.format("%s/%s/%s", config.getMediaRtspHost(), app, name);
    }

    public String addStreamProxy(String app, String name, String originUrl, String vhost) {
        String key = null;
        try {
            String url = String.format("%s/index/api/addStreamProxy?secret=%s&vhost=%s&app=%s&stream=%s&url=%s",
                    config.getMediaHttpHost(), config.getMediaSecret(), vhost, app, name, originUrl);

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                int code = Integer.parseInt(responseBody);
                if (code == 0) {
                    key = responseBody;
                }
            }
            mediaServerState = true;
        } catch (Exception e) {
            mediaServerState = false;
            System.out.printf("%s.%s() error: %s%n", this.getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(), e.getMessage());
        }
        return key;
    }

    public boolean delStreamProxy(String key) {
        boolean flag = false;
        try {
            String url = String.format("%s/index/api/delStreamProxy?secret=%s&key=%s", config.getMediaHttpHost(),
                    config.getMediaSecret(), key);

            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                int code = Integer.parseInt(responseBody);
                if (code == 0) {
                    flag = true;
                }
            }
            mediaServerState = true;
        } catch (Exception e) {
            mediaServerState = false;
            System.out.printf("%s.%s() error: %s%n", this.getClass().getSimpleName(),
                    Thread.currentThread().getStackTrace()[1].getMethodName(), e.getMessage());
        }
        return flag;
    }
    public List<Map<String, Object>> getMediaList() {
        List<Map<String, Object>> data = new ArrayList<>();
        Response response = null;
        String videoStr = "无";
        String audioStr = "无";
        Integer createStamp= 0;
        Integer fps = 0;
        try {
            String url = String.format("%s/index/api/getMediaList?secret=%s", this.config.getMediaHttpHost(), this.config.getMediaSecret());
            Map<String, String> headers = new HashMap<>();
            headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36 Edg/118.0.2088.57");

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(this.timeout, TimeUnit.SECONDS)
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/118.0.0.0 Safari/537.36 Edg/118.0.2088.57")
                    .build();
             response = client.newCall(request).execute();
            //System.out.printf("getMediaList() error: %s\n", response.body().string());

            //System.out.println(response);
            //System.out.println(response.body().string());
            if (response.code() == 200) {
                Map<String, Object> responseJson = null;
                try {
                    responseJson = new ObjectMapper().readValue(response.body().string(), Map.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if ((int)responseJson.get("code") == 0) {
                    List<Map<String, Object>> responseData = (List<Map<String, Object>>)responseJson.get("data");
                    //System.out.println(responseData);
                    if(responseData != null && responseData.size() > 0) {//无视频流，防止报空指针错误
                        Map<String, Map<String, Map<String, Object>>> dataGroup = new HashMap<>(); // Group data by app and stream
                        for (Map<String, Object> d : responseData) {
                            String app = (String)d.get("app");
                            createStamp = (Integer) d.get("createStamp");
                            String name = (String)d.get("stream");
                            String schema = (String)d.get("schema");
                            String code = String.format("%s_%s", app, name);
                            if (!dataGroup.containsKey(code)) {
                                dataGroup.put(code, new HashMap<>());
                            }
                            dataGroup.get(code).put(schema, d);
                        }

                        for (Map.Entry<String, Map<String, Map<String, Object>>> entry : dataGroup.entrySet())
                        {
                            List<Map<String, Object>> schemasClients = new ArrayList<>();
                            Map<String, Object> d = null;
                            int index = 0;
                            for (Map.Entry<String, Map<String, Object>> schemaEntry : entry.getValue().entrySet()) {
                                schemasClients.add(new HashMap<String, Object>() {{
                                    put("schema", schemaEntry.getKey());
                                    put("readerCount", schemaEntry.getValue().get("readerCount"));
                                }});
                                if (index == 0) {
                                    d = schemaEntry.getValue();
                                }
                                index++;
                            }

                            if (d != null) {

                                List<Map<String, Object>> tracks = (List<Map<String, Object>>)d.get("tracks");
                                if (tracks != null) {
                                    for (Map<String, Object> track : tracks) {
                                        int codecType = (int)track.get("codec_type");
                                        if (codecType == 0) { // Video
                                            Double fpsDouble = (Double)track.get("fps");
                                            fps = fpsDouble.intValue();
                                            int height = (int)track.get("height");
                                            int width = (int)track.get("width");
                                            videoStr = String.format("%s/%d/%dx%d", track.get("codec_id_name"), fps, width, height);
                                        } else if (codecType == 1) { // Audio
                                            int channels = (int)track.get("channels");
                                            int sampleBit = (int)track.get("sample_bit");
                                            int sampleRate = (int)track.get("sample_rate");
                                            audioStr = String.format("%s/%d/%d/%d", track.get("codec_id_name"), channels, sampleRate, sampleBit);
                                        }
                                    }
                                }

                                String produceSpeed = this.byteFormat((int)d.get("bytesSpeed"));
                                String app = (String)d.get("app");
                                String name = (String)d.get("stream");

                                String finalVideoStr = videoStr;
                                String finalAudioStr = audioStr;
                                Map<String, Object> finalD = d;

                                Integer finalFps = fps;
                                Integer finalCreateStamp = createStamp;
                                data.add(new HashMap<String, Object>() {{
                                    put("active", true);
                                    put("code", entry.getKey());
                                    put("app", app);
                                    put("name", name);
                                    put("fps", finalFps);

                                    put("createStamp", finalCreateStamp);
                                    put("produce_speed", produceSpeed);
                                    put("video", finalVideoStr);
                                    put("audio", finalAudioStr);
                                    put("originUrl", finalD.get("originUrl"));
                                    put("originType", finalD.get("originType"));
                                    put("originTypeStr", finalD.get("originTypeStr"));
                                    put("clients", finalD.get("totalReaderCount"));
                                    put("schemas_clients", schemasClients);
                                    put("flvUrl", getFlvUrl(app, name));
                                    put("hlsUrl", getHlsUrl(app, name));
                                }});
                            }
                        }
                    }

                }
            } else {
                log.error("%s error:status: {}", this.getClass().getName(), response.code());
                System.out.printf("%s error:status=%d\n", this.getClass().getName(), response.code());
            }
            this.mediaServerState = true;
        } catch (Exception e) {
            log.error("Error in getMediaList: {}", e.getMessage(), e);
            this.mediaServerState = false;


        }


        return data;
    }
}
*/
package com.ruoyi.business.aidetection.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Data
@Slf4j
@DependsOn("readConfig")
public class ZLMediaKit {

    @Autowired
    private ReadConfig config;

    private String defaultPushStreamApp = "analyzer";
    private int timeout = 1;
    private boolean mediaServerState = false;

    private final OkHttpClient client;

    public ZLMediaKit() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .build();
    }

    public String getHlsUrl(String app, String name) {
        return String.format("%s/%s/%s.hls.m3u8", config.getMediaHttpHost(), app, name);
    }

    public String getFlvUrl(String app, String name) {
        return String.format("%s/%s/%s.live.flv", config.getMediaHttpHost(), app, name);
    }

    public String getRtspUrl(String app, String name) {
        return String.format("%s/%s/%s", config.getMediaRtspHost(), app, name);
    }

    public String addStreamProxy(String app, String name, String originUrl, String vhost) {
        String url = String.format(
                "%s/index/api/addStreamProxy?secret=%s&vhost=%s&app=%s&stream=%s&url=%s",
                config.getMediaHttpHost(), config.getMediaSecret(), vhost, app, name, originUrl
        );

        String responseBody = sendRequest(url, "addStreamProxy");
        if (responseBody != null) {
            // Assuming a successful response contains a non-zero key
            return responseBody;
        }
        return null;
    }

    public boolean delStreamProxy(String key) {
        String url = String.format(
                "%s/index/api/delStreamProxy?secret=%s&key=%s",
                config.getMediaHttpHost(), config.getMediaSecret(), key
        );

        String responseBody = sendRequest(url, "delStreamProxy");
        return responseBody != null && "0".equals(responseBody.trim());
    }

    public List<Map<String, Object>> getMediaList() {
        List<Map<String, Object>> data = new ArrayList<>();
        String url = String.format("%s/index/api/getMediaList?secret=%s", config.getMediaHttpHost(), config.getMediaSecret());

        String responseBody = sendRequest(url, "getMediaList");
        if (responseBody == null) {
            return data; // Return empty list if the request failed
        }

        try {
            Map<String, Object> responseJson = new ObjectMapper().readValue(responseBody, Map.class);
            if ((int) responseJson.get("code") == 0) {
                List<Map<String, Object>> responseData = (List<Map<String, Object>>) responseJson.get("data");
                processMediaList(responseData, data);
            }
        } catch (Exception e) {
            log.error("Error parsing JSON in getMediaList: {}", e.getMessage(), e);
        }
        return data;
    }

    private void processMediaList(List<Map<String, Object>> responseData, List<Map<String, Object>> data) {
        if (responseData == null || responseData.isEmpty()) {
            return;
        }

        Map<String, Map<String, Map<String, Object>>> dataGroup = new HashMap<>();

        // Group data by app and stream
        for (Map<String, Object> item : responseData) {
            String app = (String) item.get("app");
            String stream = (String) item.get("stream");
            String schema = (String) item.get("schema");
            String code = String.format("%s_%s", app, stream);

            dataGroup.computeIfAbsent(code, k -> new HashMap<>()).put(schema, item);
        }

        for (Map.Entry<String, Map<String, Map<String, Object>>> entry : dataGroup.entrySet()) {
            Map<String, Object> d = entry.getValue().values().iterator().next(); // Get first item in the group
            if (d != null) {
                Map<String, Object> mediaInfo = buildMediaInfo(entry.getKey(), d);
                if (mediaInfo != null) {
                    data.add(mediaInfo);
                }
            }
        }
    }
    /**
     * 将字节数格式化为带单位的字符串。
     *
     * @param bytes 字节数
     * @return 格式化后的字符串，如 "1024bps"、"1.00Kbps" 等
     */
    private String byteFormat(double bytes) {
        double factor = 1024;
        for (String unit : new String[]{"", "K", "M", "G"}) {
            if (bytes < factor) {
                return String.format("%.2f%sbps", bytes, unit);
            }
            bytes /= factor;
        }
        return "";
    }


    private Map<String, Object> buildMediaInfo(String code, Map<String, Object> d) {
        Map<String, Object> mediaInfo = new HashMap<>();

        try {
            String app = (String) d.get("app");
            String stream = (String) d.get("stream");
            Integer createStamp = (Integer) d.get("createStamp");
            String produceSpeed = this.byteFormat((int)d.get("bytesSpeed"));

            mediaInfo.put("active", true);
            mediaInfo.put("code", code);
            mediaInfo.put("app", app);
            mediaInfo.put("name", stream);
            mediaInfo.put("createStamp", createStamp);
            mediaInfo.put("produceSpeed", produceSpeed);
            mediaInfo.put("video", createStamp);
            mediaInfo.put("createStamp", createStamp);
            mediaInfo.put("originUrl", d.get("originUrl"));
            mediaInfo.put("originType", d.get("originType"));
            mediaInfo.put("originTypeStr", d.get("originTypeStr"));
            mediaInfo.put("clients", d.get("totalReaderCount"));
            //mediaInfo.put("schemas_clients", schemasClients);

            mediaInfo.put("flvUrl", getFlvUrl(app, stream));
            mediaInfo.put("hlsUrl", getHlsUrl(app, stream));

            List<Map<String, Object>> tracks = (List<Map<String, Object>>) d.get("tracks");
            if (tracks != null) {
                for (Map<String, Object> track : tracks) {
                    int codecType = (int) track.get("codec_type");
                    if (codecType == 0) { // Video
                        double fps = (Double) track.get("fps");  // 强制转换为 Double
                        mediaInfo.put("fps", (int) fps);
                        mediaInfo.put("video", formatTrackInfo(track));
                    } else if (codecType == 1) { // Audio
                        mediaInfo.put("audio", formatTrackInfo(track));
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error building media info: {}", e.getMessage(), e);
        }

        return mediaInfo;
    }

    private String formatTrackInfo(Map<String, Object> track) {
        try {
            String codecName = (String) track.get("codec_id_name");
            if (track.get("fps") != null) {
                double fps = (Double) track.get("fps");  // 强制转换为 Double
                int width =  (int)track.get("width");
                int height = (int) track.get("height");
                return String.format("%s/%d/%dx%d", codecName, (int)fps , width,  height);
            }
            if (track.get("channels") != null) {
                return String.format("%s/%d/%d/%d", track.get("codec_id_name"), track.get("channels"), track.get("sample_bit"),  track.get("sample_rate"));
            }
            return codecName;
        } catch (Exception e) {
            log.warn("Error formatting track info: {}", e.getMessage(), e);
            return "Unknown";
        }
    }

    private String sendRequest(String url, String action) {
        mediaServerState = false;  // 默认失败
        try {
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("User-Agent", "Mozilla/5.0")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    mediaServerState = true;
                    return response.body().string();
                } else {
                    log.warn("{} request failed. Status code: {}", action, response.code());
                }
            }

        } catch (Exception e) {
            log.error("{} request failed: {}", action, e.getMessage(), e);
        }
        return null;
    }
}

