package com.applet.config.aop;

import com.alibaba.fastjson.JSON;
import com.applet.common.annotation.AnalysisControllerLog;
import com.applet.common.result.ResultModel;
import com.applet.entity.log.AnalysisLog;
import com.applet.service.log.AnalysisLogService;
import com.google.common.base.Stopwatch;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@Order(-5)
public class AnalysisLogAspect {

    @Autowired
    private AnalysisLogService analysisLogService;

    @Pointcut("@annotation(com.applet.common.annotation.AnalysisControllerLog)")
    public void controllerAspect(){}

    @Around("controllerAspect()")
    public ResultModel recordLog(ProceedingJoinPoint joinPoint) throws Throwable {
        ResultModel resMsg;

        com.applet.entity.log.AnalysisLog systemLog = new AnalysisLog();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        systemLog.setRequest_ip(request.getRemoteAddr());
        systemLog.setAction_method(joinPoint.getSignature().getName());
        systemLog.setAction_date(new Date().getTime());
        Map<String, String> requestMap = parseFrom(request);

        List<String> paramsList = new ArrayList<>();
        for(Object object : joinPoint.getArgs()){
            if(object instanceof HttpServletRequest || object instanceof BindingResult){
                continue;
            }
            paramsList.add(JSON.toJSONString(object));
        }
        if(!requestMap.isEmpty()){
            paramsList.add(JSON.toJSONString(requestMap));
        }
        systemLog.setRequest_params(String.join(",", paramsList));
        Integer actionType = getControllerMethodDescription(joinPoint);
        systemLog.setLog_type(actionType);

        Stopwatch stopWatch = Stopwatch.createStarted();
        resMsg = (ResultModel) joinPoint.proceed(joinPoint.getArgs());
        systemLog.setRun_time((int)stopWatch.stop().elapsed(TimeUnit.MILLISECONDS));
        if(resMsg != null){
            if (resMsg.succeed()) {
                systemLog.setOperation_type(1);
            } else {
                systemLog.setOperation_type(2);
            }
            systemLog.setResponse_msg(JSON.toJSONString(resMsg));
            systemLog.setDescription(String.valueOf(actionType) + ":" + resMsg.getMsg());
        }
        analysisLogService.saveAnalysisLog(systemLog);
        return resMsg;
    }

    public Integer getControllerMethodDescription(ProceedingJoinPoint point) throws Exception{
        String actionType = "0";
        String targetName=point.getTarget().getClass().getName();
        String methodName=point.getSignature().getName();
        Object[] args=point.getArgs();
        Class targetClass=Class.forName(targetName);
        Method[] methods=targetClass.getMethods();
        for(Method method:methods){
            if(method.getName().equals(methodName)){
                Class[] clazzs=method.getParameterTypes();
                if(clazzs.length==args.length){
                    actionType=method.getAnnotation(AnalysisControllerLog.class).actionType();
                    break;
                }
            }
        }
        return Integer.valueOf(actionType);
    }

    public Map<String, String> parseFrom(HttpServletRequest request) {

        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while(parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            parameters.put(parameterName, request.getParameter(parameterName));
        }
        return parameters;
    }

    @AfterThrowing(pointcut = "controllerAspect()",throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint,Throwable e) throws Throwable{
        System.out.println("进入异常！");
//        AnalysisLog systemLog=new AnalysisLog();
//        Object proceed=null;
//
//        HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
//        request.getSession().getAttribute("user");
//        systemLog.setUserid("conquer");
//
//        String ip=request.getRemoteAddr();
//        systemLog.setRequestip(ip);
//        systemLog.setType("2");
//        systemLog.setExceptioncode(e.getClass().getName());
//        systemLog.setExceptiondetail(e.getMessage());
//        systemLogService.saveUser(systemLog);
    }

}
