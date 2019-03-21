package com.applet.config.redis.redislock;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisDistributionLock {

    private static final Logger logger = LoggerFactory.getLogger(RedisDistributionLock.class);

    //key的TTL,一天
    private static final int finalDefaultTTLwithKey = 24 * 3600;

    //锁默认超时时间,20秒
    public static final long defaultExpireTime = 20 * 1000;

    private static final boolean Success = true;

    @Autowired
    private RedisTemplate<String, String> redisTemplateForGeneralize;

    /**
     * 加锁,锁默认超时时间20秒
     * 获得 lock. 实现思路: 主要是使用了redis 的setnx命令,缓存了锁. reids缓存的key是锁的key,所有的共享,
     * value是锁的到期时间(注意:这里把过期时间放在value了,没有时间上设置其超时时间) 执行过程:
     * 1.通过setnx尝试设置某个key的值,成功(当前没有这个锁)则返回,成功获得锁
     * 2.锁已经存在则获取锁的到期时间,和当前时间比较,超时的话,则设置新的值
     *
     * @param resource
     * @return
     */
    public boolean lock(String resource,long now) {
        return this.lock(resource, defaultExpireTime,now);
    }

    /**
     * 加锁,同时设置锁超时时间
     *
     * @param key        分布式锁的key
     * @param expireTime 单位是ms，超时时间
     * @return
     */
    public synchronized boolean lock(String key, long expireTime,long now) {

        logger.debug("redis lock debug, start. key:[{}], expireTime:[{}]", key, expireTime);
        long lockExpireTime = now + expireTime;

        //setnx
        boolean executeResult = redisTemplateForGeneralize.opsForValue().setIfAbsent(key, String.valueOf(lockExpireTime));
        logger.debug("redis lock debug, setnx. key:[{}], expireTime:[{}], executeResult:[{}]", key, expireTime, executeResult);

        //取锁成功,为key设置expire
        if (executeResult == Success) {
            redisTemplateForGeneralize.expire(key, finalDefaultTTLwithKey, TimeUnit.SECONDS);
            return true;
        }
        //没有取到锁,继续流程
        else {
            Object valueFromRedis = this.getKeyWithRetry(key, 3);
            // 避免获取锁失败,同时对方释放锁后,造成NPE
            // 判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断 oldExpireTime <= now 是过不去的
            if (valueFromRedis != null) {
                //已存在的锁超时时间
                long oldExpireTime = Long.parseLong((String) valueFromRedis);
                logger.debug("redis lock debug, key already seted. key:[{}], oldExpireTime:[{}]", key, oldExpireTime);
                //锁过期时间小于当前时间,锁已经超时,重新取锁
                if (oldExpireTime <= now) {
                    logger.debug("redis lock debug, lock time expired. key:[{}], oldExpireTime:[{}], now:[{}]", key, oldExpireTime, now);
                    // 获取上一个锁到期时间，并设置现在的锁到期时间，只有一个线程才能获取上一个线上的设置时间，因为getSet是同步的
                    String valueFromRedis2 = redisTemplateForGeneralize.opsForValue().getAndSet(key, String.valueOf(lockExpireTime));
                    long currentExpireTime = Long.parseLong(valueFromRedis2);
                    // 判断currentExpireTime与oldExpireTime是否相等 [分布式的情况下]:如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                    if (currentExpireTime == oldExpireTime) {
                        //相等,则取锁成功
                        logger.debug("redis lock debug, getSet. key:[{}], currentExpireTime:[{}], oldExpireTime:[{}], lockExpireTime:[{}]", key, currentExpireTime, oldExpireTime, lockExpireTime);
                        // 防止误删（覆盖，因为key是相同的）了他人的锁 ——--- 这里达不到效果，这里值会被覆盖，但是因为什么相差了很少的时间，所以可以接受
                        redisTemplateForGeneralize.expire(key, finalDefaultTTLwithKey, TimeUnit.SECONDS);
                        return true;
                    } else {
                        //不相等,取锁失败
                        return false;
                    }
                }
            } else {
                logger.warn("redis lock,lock have been release. key:[{}]", key);
                return false;
            }
        }
        return false;
    }

    private Object getKeyWithRetry(String key, int retryTimes) {
        int failTime = 0;
        while (failTime < retryTimes) {
            try {
                return redisTemplateForGeneralize.opsForValue().get(key);
            } catch (Exception e) {
                failTime++;
                if (failTime >= retryTimes) {
                    throw e;
                }
            }
        }
        return null;
    }

    /**
     * 解锁
     *
     * @param key
     * @return
     */
    public synchronized boolean unlock(String key, String identifier) {
        logger.debug("redis unlock debug, start. key:[{}],identifier:[{}]", key, identifier);
        if (identifier == null || "".equals(identifier)) {
            return false;
        }
        boolean releaseFlag = false;
        while (true) {
            try {
                // 监视lock，准备开始事务
                redisTemplateForGeneralize.watch(key);
                // 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁
                String identifierValue = redisTemplateForGeneralize.opsForValue().get(key);
                if (StringUtils.isBlank(identifierValue)) {
                    redisTemplateForGeneralize.unwatch();
                    releaseFlag = false;
                    break;
                }
                if (identifier.equals(identifierValue)) {
                    redisTemplateForGeneralize.setEnableTransactionSupport(true);
                    redisTemplateForGeneralize.multi();
                    redisTemplateForGeneralize.delete(key);
                    List<Object> results = redisTemplateForGeneralize.exec();
                    if (results == null) {
                        continue;
                    }
                    releaseFlag = true;
                }
                redisTemplateForGeneralize.unwatch();
                break;
            } catch (Exception e) {
                logger.warn("释放锁异常", e);
                e.printStackTrace();
            }
        }
        return releaseFlag;
    }
}