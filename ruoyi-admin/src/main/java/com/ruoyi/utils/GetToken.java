/*
package com.ruoyi.utils;

import cn.hutool.json.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
@Component
public class GetToken {
    @Value("${request.url}")
    private String url;
    @Value("${request.AccessKeyId}")
    private String accessKeyId;
    @Value("${request.AccessKeySecret}")
    private String AccessKeySecret;

    */
/*@Autowired
    private RedisCache redisCache;*//*

    private RedisCache redisCache = BeanUtils.getBean(RedisCache.class);
    public Map getXcidAndXtoken(){

        //Object xcidAndXtoken = redisTemplate.opsForValue().get("XcidAndXtoken");

        Object xcidAndXtoken = redisCache.getCacheMap("XcidAndXtoken");
        if(xcidAndXtoken!=null){
            return (Map) xcidAndXtoken;
        }
        RestTemplate restTemplate = new RestTemplate();
        // 设置请求参数
        Map<String, String> map = new HashMap<>();
        String timeStamp=String.valueOf(System.currentTimeMillis());
        map.put("accessKeyId", accessKeyId);
        map.put("encryptStr", encrypByMd5(accessKeyId+AccessKeySecret+timeStamp));
        map.put("timeStamp",timeStamp );
        //Object o=new Object();
        System.out.println(AccessKeySecret);
        System.out.println(accessKeyId);
        System.out.println(url);
        JSONObject res = restTemplate.postForObject(url, map, JSONObject.class);
        //System.out.println(s);
        JSONObject content=res.getJSONObject("content");
        String x_cid=content.get("companyId").toString();
        String x_token = content.get("accessToken").toString();
        Map<String, String> map1 = new HashMap<>();
        map1.put("x-cid", x_cid);
        map1.put("x-token", x_token);
        redisCache.setCacheMap("XcidAndXtoken",map1);
        return map1;

    }
    public static String encrypByMd5(String context) {

        // 获取一个MD5消息摘要实例
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // 更新消息摘要，将输入的文本内容转换为字节数组并进行处理
        md.update(context.getBytes());

        // 计算消息摘要，得到MD5散列值
        byte[] encryContext = md.digest();

        int i;
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < encryContext.length; offset++) {
            // 将字节值转换为无符号整数
            i = encryContext[offset];
            if (i < 0) i += 256;  // 处理负值
            if (i < 16) buf.append("0");  // 补充前导0，以保证每个字节都被表示为两位十六进制数
            buf.append(Integer.toHexString(i));  // 将字节值转换为十六进制字符串并追加到结果字符串
        }

        // 返回MD5散列值的十六进制表示
        return buf.toString();
    }
}
*/
