package com.ruoyi.extend.datasecurity.aesencrypt.filter;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.HttpMethod;
import com.ruoyi.extend.datasecurity.aesencrypt.utils.AesEncryptUtils;
import com.ruoyi.extend.datasecurity.aesencrypt.wrapper.GetParamsRequestWrapper;
import com.ruoyi.extend.datasecurity.aesencrypt.wrapper.PostParamsRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 解密过滤器
 *
 * @author Tellsea
 * @date 2021/09/27
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "business.aes-encrypt", name = "enabled", havingValue = "true")
public class AesEncryptFilter extends OncePerRequestFilter {

    private static final String CONTENT_TYPE_CHARSET = "application/json;charset=UTF-8";

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        List<String> whiteUrlList = Arrays.asList(
                "/ruoyi-vue-service/au/weiXinMp/callback",

                "/ruoyi-vue-service/common",
                "/ruoyi-vue-service/profile",
                "/ruoyi-vue-service/common/download",
                "/ruoyi-vue-service/common/download/resource",
                "/ruoyi-vue-service/doc.html",
                "/ruoyi-vue-service/swagger-resources",
                "/ruoyi-vue-service/webjars",
                "/ruoyi-vue-service/druid",
                "/ruoyi-vue-service/actuator"
        );
        for (String whiteUrl : whiteUrlList) {
            if (request.getRequestURI().startsWith(whiteUrl)) {
                filterChain.doFilter(request, response);
                return;
            }
        }
        ObjectMapper om = new ObjectMapper();
        response.setContentType(CONTENT_TYPE_CHARSET);
        if (HttpMethod.POST.name().equals(request.getMethod())) {
            PostParamsRequestWrapper requestWrapper = new PostParamsRequestWrapper(request);
            filterChain.doFilter(requestWrapper, response);
            return;
        }
        String dataParams = request.getParameter("dataParams");
        if (StringUtils.isEmpty(dataParams)) {
            om.writeValue(response.getWriter(), AjaxResult.error("参数解析错误，不能为空"));
            return;
        }
        try {
            String decode = URLDecoder.decode(dataParams, "UTF-8");
            String decrypt = AesEncryptUtils.decrypt(decode);
            JSONObject jSONObject = JSON.parseObject(decrypt);
            HashMap params = new HashMap(request.getParameterMap());
            params.remove("dataParams");
            params.remove("noData");
            for (String str : jSONObject.keySet()) {
                if (str.equals("params")) {
                    params.remove(str);
                } else {
                    params.put(str, jSONObject.get(str));
                }
            }
            GetParamsRequestWrapper requestWrapper = new GetParamsRequestWrapper(request, params);
            filterChain.doFilter(requestWrapper, response);
        } catch (Exception e) {
            log.error("转换错误" + getExceptionInfo(e));
            om.writeValue(response.getWriter(), AjaxResult.error("参数异常"));
            return;
        }
    }

    public static String getExceptionInfo(Exception e) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        e.printStackTrace(new PrintStream(baos));
        return baos.toString();
    }
}
