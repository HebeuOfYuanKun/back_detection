package com.ruoyi.utils;


import cn.hutool.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Exchanger;

@Component
public class GetLive {
    @Value("${request.live.url}")
    private String url;
    @Autowired
    private GetToken getToken;
    public Map getLiveRtmpAndHTTP(){
        String params=null;
        HttpHeaders headers = new HttpHeaders();
        String x_cid=getToken.getXcidAndXtoken().get("x-cid").toString();
        String x_token=getToken.getXcidAndXtoken().get("x-token").toString();
        headers.add("x-cid",x_cid);
        headers.add("x-token",x_token);
        HttpEntity<Object> entity = new HttpEntity<>(params, headers);
        RestTemplate restTemplate=new RestTemplate();
        ResponseEntity<JSONObject> res=restTemplate.exchange(url, HttpMethod.GET, entity, JSONObject.class);
        JSONObject content=res.getBody().getJSONObject("content");
        Map map=new HashMap();
        map.put("rtmp",content.get("rtmp").toString());
        map.put("flv",content.get("flv").toString());
        return map;
    }

}
