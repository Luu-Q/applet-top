package com.applet.config.datasource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description: 失败重试
 * @author: LUNING
 * @create: 2019-05-10 21:07
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataType {
    DataSourceType.DataBaseType value() default DataSourceType.DataBaseType.teacher;
}
