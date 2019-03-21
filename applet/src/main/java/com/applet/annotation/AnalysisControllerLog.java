package com.applet.annotation;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface AnalysisControllerLog {
    String description() default "";
    String actionType() default "0";
}
