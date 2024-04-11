package com.ruoyi.extend.datasecurity.aesencrypt.wrapper;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.extend.datasecurity.aesencrypt.utils.AesEncryptUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 重写 HttpServletRequestWrapper 处理 Post
 *
 * @author Tellsea
 * @date 2022/9/3
 */
@Slf4j
public class PostParamsRequestWrapper extends HttpServletRequestWrapper {

    private static final String CONTENT_TYPE_CHARSET = "application/json;charset=UTF-8";

    public PostParamsRequestWrapper(HttpServletRequest request) {
        super(request);
        // 由于我们只做POST请求, 所以这里不做任何处理
    }

    /**
     * 重写getInputStream方法
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        //非json类型，直接返回
        //if (!super.getHeader(HttpHeaders.CONTENT_TYPE).equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE)) {
        //	return super.getInputStream();
        //}
        //从输入流中取出body串, 如果为空，直接返回
        String reqBodyStr = IOUtils.toString(super.getInputStream(), "utf-8");
        if (StringUtils.isEmpty(reqBodyStr)) {
            return super.getInputStream();
        }
        reqBodyStr = URLDecoder.decode(reqBodyStr, "UTF-8");
        HttpServletResponse response = ServletUtils.getResponse();
        ObjectMapper om = new ObjectMapper();
        response.setContentType(CONTENT_TYPE_CHARSET);
        if (ObjectUtil.isNull(JSON.parseObject(reqBodyStr).get("dataParams"))) {
            om.writeValue(response.getWriter(), AjaxResult.error("参数异常"));
            return null;
        }
        try {
            //reqBodyStr转为Map对象
            Map<String, Object> paramMap = new ObjectMapper().readValue(AesEncryptUtils.decrypt(JSON.parseObject(reqBodyStr).get("dataParams").toString()), new TypeReference<HashMap<String, Object>>() {
            });
            //重新构造一个输入流对象
            byte[] bytes = JSON.toJSONString(paramMap).getBytes("utf-8");
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            return new MyServletInputStream(bis);
        } catch (Exception e) {
            om.writeValue(response.getWriter(), AjaxResult.error("参数异常"));
            return null;
        }
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    class MyServletInputStream extends ServletInputStream {

        private ByteArrayInputStream bis;

        public MyServletInputStream(ByteArrayInputStream bis) {
            this.bis = bis;
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {

        }

        @Override
        public int read() throws IOException {
            return bis.read();
        }
    }
}
