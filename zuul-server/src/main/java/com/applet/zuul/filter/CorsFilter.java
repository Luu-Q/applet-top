package com.applet.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CorsFilter extends ZuulFilter {


    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE; // 定义filter的类型，有pre、route、post、error四种
    }

    @Override
    public int filterOrder() {
        return 0;// 定义filter的顺序，数字越小表示顺序越高，越先执行
    }

    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        String method = ctx.getRequest().getMethod();
		return "OPTIONS".equals(method);
    }

    @Override
    public Object run() throws ZuulException {
        try {// filter需要执行的具体操作
            RequestContext ctx = RequestContext.getCurrentContext();
            ctx.setResponseStatusCode(200);
            ctx.setSendZuulResponse(false);
        } catch (Exception e) {
            log.error("CorsFilter error", e);
        }
        return null;
    }
}
