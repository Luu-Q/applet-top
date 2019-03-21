package com.applet.annotation;

import java.lang.annotation.*;

/**
 * aop 日志注解
 * 使用方法，放到controllter
 * Created by LuNing on 2017/2/22.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LogAnalysis {

    boolean logEnable() default true;

}
