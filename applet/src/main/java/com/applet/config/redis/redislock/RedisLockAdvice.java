package com.applet.config.redis.redislock;

import com.google.common.base.Joiner;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.Instant;

//https://www.jianshu.com/p/e72baf5e5617
@Component
@Aspect
public class RedisLockAdvice {

    private static final Logger logger = LoggerFactory.getLogger(RedisLockAdvice.class);

    @Autowired
    private RedisDistributionLock redisDistributionLock;

    @Around("@annotation(com.applet.config.redis.redislock.RedisLockAnnoation)")
    public Object processAround(ProceedingJoinPoint pjp) throws Throwable {
        //获取方法上的注解对象
        String methodName = pjp.getSignature().getName();
        Class<?> classTarget = pjp.getTarget().getClass();
        Class<?>[] par = ((MethodSignature) pjp.getSignature()).getParameterTypes();
        Method objMethod = classTarget.getMethod(methodName, par);
        RedisLockAnnoation redisLockAnnoation = objMethod.getDeclaredAnnotation(RedisLockAnnoation.class);

        //拼装分布式锁的key
        Object[] args = pjp.getArgs();
        String targetName = classTarget.getName();
        String redisKey = getLockKey(targetName, methodName,args,redisLockAnnoation);

        long now = Instant.now().toEpochMilli();

        //执行分布式锁的逻辑
        //阻塞锁
        if (redisLockAnnoation.isSpin()) {
            long expireTime = redisLockAnnoation.expireTime();
            String identifier = String.valueOf(now + expireTime);

            int lockRetryTime = 0;
            try {
                while (!redisDistributionLock.lock(redisKey,expireTime,now)) {
                    if (lockRetryTime++ > redisLockAnnoation.retryTimes()) {
                        logger.error("lock exception. key:{}, lockRetryTime:{}", redisKey, lockRetryTime);
                        throw new RuntimeException("获取锁失败");
                    }
                    /*
                     * 延迟100 毫秒, 这里使用随机时间可能会好一点,可以防止饥饿进程的出现,即,当同时到达多个进程,
                     * 只会有一个进程获得锁,其他的都用同样的频率进行尝试,后面有来了一些进行,也以同样的频率申请锁,这将可能导致前面来的锁得不到满足.
                     * 使用随机的等待时间可以一定程度上保证公平性
                     */
                    Thread.sleep(redisLockAnnoation.waitTime());
                }
                return pjp.proceed();
            } finally {
                redisDistributionLock.unlock(redisKey,identifier);
            }
        } else {//非阻塞锁
            long expireTime = RedisDistributionLock.defaultExpireTime;
            String identifier = String.valueOf(now + expireTime);

            try {
                if (!redisDistributionLock.lock(redisKey,now)) {
                    logger.error("lock exception. key:{}", redisKey);
                    throw new RuntimeException("获取锁失败");
                }
                return pjp.proceed();
            } finally {
                redisDistributionLock.unlock(redisKey,identifier);
            }
        }
    }

    private String getLockKey(String targetName, String methodName, Object[] arguments,RedisLockAnnoation redisLockAnnoation) {

        StringBuilder sb = new StringBuilder();
        sb.append(redisLockAnnoation.keyPrefix()).append("_").append(targetName).append(".").append(methodName);
        String[] keys = redisLockAnnoation.keys();

        if(keys != null) {
            String keyStr = Joiner.on(".").skipNulls().join(keys);
            String[] parameters = ReflectParamNames.getNames(targetName, methodName);
            ExpressionParser parser = new SpelExpressionParser();
            Expression expression = parser.parseExpression(keyStr);
            EvaluationContext context = new StandardEvaluationContext();
            int length = parameters.length;
            if (length > 0) {
                for (int i = 0; i < length; i++) {
                    context.setVariable(parameters[i], arguments[i]);
                }
            }
            String keysValue = expression.getValue(context, String.class);
            sb.append("#").append(keysValue);
        }
        return sb.toString();
    }
}