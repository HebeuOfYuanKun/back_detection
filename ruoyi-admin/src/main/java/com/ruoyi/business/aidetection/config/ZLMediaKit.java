package com.ruoyi.business.aidetection.config;

/**
 * @Author：yuankun
 * @Package：com.ruoyi.business.aidetection.config
 * @Project：ruoyi-vue-service
 * @name：ZLMediaKit
 * @Date：2024/4/7 21:29
 * @Filename：ZLMediaKit
 */
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data

public class ZLMediaKit {
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

                    for (Map.Entry<String, Map<String, Map<String, Object>>> entry : dataGroup.entrySet()) {
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
            } else {
                System.out.printf("%s error:status=%d\n", this.getClass().getName(), response.code());
            }
            this.mediaServerState = true;
        } catch (Exception e) {
            this.mediaServerState = false;
            System.out.println(e);

        }


        return data;
    }
}
