package com.applet.zuul.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Component
public class AccessFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger("accesslog");

    @Autowired
    DiscoveryClient discovery;

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE; // 定义filter的类型，有pre、route、post、error四种
    }

    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER + 2;// 定义filter的顺序，数字越小表示顺序越高，越先执行
    }

    @Override
    public boolean shouldFilter() {
        return true;// 表示是否需要执行该filter，true表示执行，false表示不执行
    }

    @Override
    public Object run() throws ZuulException {
        try {
            RequestContext ctx = RequestContext.getCurrentContext();
            HttpServletRequest request = ctx.getRequest();
            String method = request.getMethod();
            String requestURI = request.getRequestURI();
            String remoteAddr = request.getHeader("X-Forwarded-For");
            //String remoteAddr = request.getRemoteAddr();
            String userAgent = request.getHeader("User-Agent");
            String queryString = request.getQueryString();

            int status = ctx.getResponseStatusCode();

//			long startTime = (long)ctx.get("beginTime");
            long startTime = 0;
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            String ssoToken = request.getHeader(HttpHeaders.AUTHORIZATION);
            Map<String, String> zuulRequestHeaders = ctx.getZuulRequestHeaders();

            ctx.getResponse().addHeader("Access-Control-Allow-Origin", "*");
            //ctx.getResponse().addHeader("Access-Control-Allow-Credentials", "true");
            ctx.getResponse().addHeader("Access-Control-Request-Method", "PUT,POST,GET,OPTIONS,DELETE");
            ctx.getResponse().addHeader("Access-Control-Max-Age", "600");
            ctx.getResponse().addHeader("Access-Control-Allow-Headers", "Content-Type, X-PINGOTHER, Access-Control-Request-Method, Access-Control-Allow-Headers, Authorization, X-Requested-With");
            ctx.getResponse().setContentType("application/json;charset=utf-8");
            log.info("httpMethod:[{}],\t requestURI:[{}],\t queryString:[{}],\t remoteAddr:[{}],\t userAgent:[{}],\t status:[{}],\t duration:[{}],\t token:[{}],\t zuulRequestHeaders:[{}];",
                    method, requestURI, queryString, remoteAddr, userAgent, status, duration, ssoToken, JSONObject.toJSONString(zuulRequestHeaders));

            List<String> services = discovery.getServices();
        } catch (Exception e) {
            log.error("AccessFilter Exception:", e);
        }
        return null;
    }
}
