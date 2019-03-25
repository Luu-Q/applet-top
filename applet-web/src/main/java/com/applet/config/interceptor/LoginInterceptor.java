package com.applet.config.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    private final Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        response.setCharacterEncoding("utf-8");
        response.setContentType("text/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out, "utf-8"));
        //返回json格式的提示

//        log.info("拦截 token：" + token);
//        if (StringUtils.isEmpty(token) || StringUtils.isBlank(token)) {
//            pw.println(new Gson().toJson(new ResultModel(1001, "用户 Token 不存在", null)));
//            pw.flush();
//            pw.close();
//            return false;
//        }
//
//        TokenService tokenService = SpringUtil.getBean(TokenService.class);
//        if (StringUtils.isNotBlank(token) && !tokenService.checkTokenValid(token)) {
//            pw.println(new Gson().toJson(new ResultModel(1001, "用户 Token 失效，请重新获取", null)));
//            pw.flush();
//            pw.close();
//            return false;
//        }
//
//        // token 自动延时
//        tokenService.tokenDelay(token);

        return true;
    }

}
