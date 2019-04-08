package com.applet.common.strategy;

import java.lang.annotation.*;


/**
 * @description: 业务处理器注解
 * @author: LUNING
 * @create: 2019/4/8 9:45 AM
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface HandlerType {

    String value();
}
