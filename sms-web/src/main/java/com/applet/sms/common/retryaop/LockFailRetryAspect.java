package com.applet.sms.common.retryaop;

import com.applet.sms.common.exceptions.ApiException;
import com.applet.sms.common.exceptions.ApiResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * 更新失败，尝试重试切片
 *
 * @author rstyro
 */
@Slf4j
@Aspect
@Configuration
public class LockFailRetryAspect {


    @Pointcut("@annotation(com.applet.sms.common.retryaop.LockFailRetry)")
    public void retryOnOptFailure() {
        // pointcut mark
    }

    @Around("retryOnOptFailure()")
    public Object doConcurrentOperation(ProceedingJoinPoint pjp) throws Throwable {
        //获取方法上的注解对象
        String methodName = pjp.getSignature().getName();
        Class<?> classTarget = pjp.getTarget().getClass();
        Class<?>[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
        Method objMethod = classTarget.getMethod(methodName, par);
        LockFailRetry lockFailRetry = objMethod.getDeclaredAnnotation(LockFailRetry.class);
        //重试次数
        int retrysNumMax = lockFailRetry.maxretrys();

        int attemptNum = 0;
        do {
            attemptNum++;
            try {
                //再次执行业务代码
                return pjp.proceed();
            } catch (LockFailRetryException ex) {
                if (attemptNum > retrysNumMax) {
                    //大于重试次数，抛出异常结束
                    throw new ApiException(ApiResultEnum.ERROR_TRY_AGAIN_FAILED.ERROR);
                } else {
                    //没达到最大的重试次数，将再次执行
                    log.error("[乐观锁失败重试]：第{}次",attemptNum);
                }
            }
        } while (attemptNum <= retrysNumMax);

        return null;
    }
}
