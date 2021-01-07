package com.fzcoder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

/**
 * @Description redis缓存配置类
 *
 * @author Frank Fang
 */
@Configuration
public class RedisConfig {
 
    @Bean("redisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
    	RedisTemplate<Object, Object> template = new RedisTemplate<>();
    	template.setConnectionFactory(factory);
        // template.setEnableTransactionSupport(false);
    	// 修改默认的序列化方式
    	template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        // template.setKeySerializer(new StringRedisSerializer());
        // template.setHashKeySerializer(new StringRedisSerializer());
        // template.setValueSerializer(new StringRedisSerializer());
        // template.afterPropertiesSet();
        return template;
    }

    // @Bean("crawlerTemplate")
    /* public RedisTemplate<Object, Object> crawlerRedisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    } */
}