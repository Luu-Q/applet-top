package com.applet.config.datasource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DataSourceAop {

    @Pointcut("@annotation(ai.zile.teacher.omp.config.datasource.DataType)")
    public void dataSourceTypeAop() {
    }

    @Around("dataSourceTypeAop()")
    public Object dataConfiguring(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        Class<?> classTarget = pjp.getTarget().getClass();
        Class<?>[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
        Method objMethod = classTarget.getMethod(methodName, par);
        DataType dataType = objMethod.getDeclaredAnnotation(DataType.class);
        DataSourceType.DataBaseType value = dataType.value();
        try {
            DataSourceType.setDataBaseType(value);
            return pjp.proceed();
        } finally {
            DataSourceType.clearDataBaseType();
        }
    }
}