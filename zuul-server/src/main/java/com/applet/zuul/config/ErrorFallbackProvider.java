package com.applet.zuul.config;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.nio.charset.Charset;

/**
 * @author wgw
 */
@Slf4j
@Component
public class ErrorFallbackProvider implements FallbackProvider, ApplicationContextAware {

    private static ApplicationContext context = null;

    @Override
    public String getRoute() {
        // 表明是为哪个微服务提供回退，*表示为所有微服务提供回退
        return "*";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
        log.error("ErrorFallbackProvider route:{} Throwable:", route, cause);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        try {
            cause.printStackTrace(printWriter);
            String profiles = "null";
            profiles = context.getEnvironment().getActiveProfiles()[0];
            String errerContent = stringWriter.toString();
            send("profiles:" + profiles + ",route:" + route + ",服务报错:" + errerContent.substring(0, errerContent.length() / 3));
        } finally {
            printWriter.close();
        }
        return this.response(HttpStatus.OK);
    }


    private ClientHttpResponse response(final HttpStatus status) {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return status;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return status.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return status.getReasonPhrase();
            }

            @Override
            public void close() {
            }

            @Override
            public InputStream getBody() throws IOException {
//				DataResult<Object> faild = DataResult.faild(ErrorCode.UNKNOW_ERROR.getCode(), "网络开小差了请再试一下");
                return new ByteArrayInputStream(JSONObject.toJSONString(null).getBytes("UTF-8"));
            }

            @Override
            public HttpHeaders getHeaders() {
                // headers设定
                HttpHeaders headers = new HttpHeaders();
                MediaType mt = new MediaType("application", "json", Charset.forName("UTF-8"));
                headers.setContentType(mt);
                return headers;
            }
        };
    }

    //钉钉机器人
    private void send(String content) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            JSONObject jsonObject = JSONObject.parseObject("{\"msgtype\":\"text\",\"text\":{\"content\":\"" + content + "\"}}");
            restTemplate.postForEntity("url", jsonObject, String.class);
        } catch (Exception e) {
            log.error("钉钉发送消息失败", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

}
