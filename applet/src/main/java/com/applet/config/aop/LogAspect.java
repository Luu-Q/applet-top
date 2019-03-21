package com.applet.config.aop;


import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * aop
 * Created by LuNing on 2017/2/22.
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger asLog = LoggerFactory.getLogger("sync_analysis_log");

    @Pointcut(value = "@annotation(com.applet.annotation.LogAnalysis)")
    public void webLog(){

    }

    @Around("webLog()")
    public Object around(final ProceedingJoinPoint point) throws Throwable {
        Stopwatch stopWatch = Stopwatch.createStarted();
        Object restResponse = null;
        Map<String, Object> reqMap = new HashMap<>();
        try {
            String classType = point.getTarget().getClass().getName();
            Class<?> clazz = Class.forName(classType);
            String methodName = point.getSignature().getName();
            List<String> paramterNameList = getParamterName(clazz, methodName);
            if (paramterNameList != null) {
                for (int i = 0; i < paramterNameList.size(); i++) {
                    if(point.getArgs()[i] instanceof org.springframework.validation.BeanPropertyBindingResult) continue;
                    reqMap.put(paramterNameList.get(i), point.getArgs()[i]);
                }
            }
            restResponse =  point.proceed(point.getArgs());
        } finally {
            AnalysisLog logEntity = new AnalysisLog();
            logEntity.setRequestContent(reqMap);
            logEntity.setResponseContent(restResponse);
            logEntity.setRuntime(stopWatch.stop().elapsed(TimeUnit.MILLISECONDS));
            asLog.info(JSON.toJSONString(logEntity));
        }
        return restResponse;
    }

    private List<String> getParamterName(Class clazz, String methodName) {
        LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                String[] params = u.getParameterNames(method);
                return Arrays.asList(params);
            }
        }
        return null;
    }
}
