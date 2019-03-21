package com.applet.config.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @ClassName: WebAppConfigurer
 * @Description: TODO(跨域拦截器)
 * @author shota
 * @date 2018年9月17日
 *
 */
public class CorsInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
			throws Exception {

		String origin = httpServletRequest.getHeader("Origin");
		httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "*");
		httpServletResponse.setHeader("Access-Control-Allow-Headers",
				"Origin,Content-Type,Accept,token,X-Requested-With");
		httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

		return true;
	}
	// 其他postHandle,afterCompletion空继承
}
