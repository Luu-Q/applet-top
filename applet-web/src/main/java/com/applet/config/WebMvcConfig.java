package com.applet.config;

import com.google.common.collect.Lists;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootConfiguration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");

        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**","/static/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/","classpath:/META-INF/resources/static/");
        super.addResourceHandlers(registry);
    }

    /**
     * 设置项目默认页面，不设置自动找index.html
     *
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        super.addViewControllers(registry);
//        registry.addViewController("/index");
//        registry.addViewController("/user");
//        registry.addRedirectViewController("/index","/templates/index.html");
//        registry.addRedirectViewController("/","/templates/login.html");
//        registry.addStatusController("/403", HttpStatus.FORBIDDEN);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor())
//                .addPathPatterns("/**")             // 拦截配置
//                .excludePathPatterns(                // 排除配置
//                        Lists.newArrayList("/error"));

    }
}
