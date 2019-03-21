package com.applet.config.redis.redislock;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RedisLockAnnoation {

    String keyPrefix() default "lock";

    /**
     * 要锁定的key中包含的属性，必须标识唯一性。例：#productId
     */
    String[] keys() default {};

    /**
     * 是否阻塞锁；
     * 1. true：获取不到锁，阻塞一定时间；
     * 2. false：获取不到锁，立即返回
     */
    boolean isSpin() default true;

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    int expireTime() default 10000;

    /**
     * 等待时间
     */
    int waitTime() default 50;

    /**
     * 获取不到锁的等待时间
     */
    int retryTimes() default 20;
}
