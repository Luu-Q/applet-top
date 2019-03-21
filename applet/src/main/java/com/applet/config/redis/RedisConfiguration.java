package com.applet.config.redis;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {

    @Autowired
    private JedisPropertes jedisPropertes;

    public JedisConnectionFactory convertJedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(jedisPropertes.getHost());
        jedisConnectionFactory.setPort(jedisPropertes.getPort());
        jedisConnectionFactory.setPassword(jedisPropertes.getPassword());

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(jedisPropertes.getMaxTotal());
        jedisPoolConfig.setMaxIdle(jedisPropertes.getMaxIdle());
        jedisPoolConfig.setMinIdle(jedisPropertes.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(jedisPropertes.getMaxWaitMillis());
        jedisPoolConfig.setTestOnBorrow(jedisPropertes.isTestOnBorrow());
        jedisPoolConfig.setTestOnReturn(jedisPropertes.isTestOnReturn());
        jedisPoolConfig.setTestWhileIdle(jedisPropertes.isTestWhileIdle());

        jedisConnectionFactory.setPoolConfig(jedisPoolConfig);
        return jedisConnectionFactory;
    }

    @Bean
    public RedisTemplate redisTemplate() {
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(convertJedisConnectionFactory());


        /** Jackson2JsonRedisSerialize 替换默认序列化，json占用的内存最小 */
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        /** Jdk序列化 JdkSerializationRedisSerializer是最高效的,默认的 */
        //JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        /**String序列化*/
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        /**将key value 进行stringRedisSerializer序列化*/
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        /**将HashKey HashValue 进行序列化*/
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
