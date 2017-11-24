package cn.tech.yozo.factoryrp.config.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/22
 * @description
 */
@Configuration
@EnableCaching
@Order(0)
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    //private static RedisTemplate redisTemplate = null;

    @Bean
    public CacheManager cacheManager() {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate());
        cacheManager.setDefaultExpiration(1800);
        return cacheManager;
    }



    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(6);
        jedisPoolConfig.setMaxTotal(32);
        jedisPoolConfig.setMaxWaitMillis(15000);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(60000L);
        jedisPoolConfig.setNumTestsPerEvictionRun(3);

        return jedisPoolConfig;

    }

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setUsePool(true);

        return jedisConnectionFactory;
    }


    @Bean
    public RedisTemplate redisTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
/*        redisTemplate.setDefaultSerializer(fastJson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(fastJson2JsonRedisSerializer());
        redisTemplate.setKeySerializer(fastJson2JsonRedisSerializer());*/
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(fastJson2JsonRedisSerializer());
        redisTemplate.setEnableDefaultSerializer(false);
        return redisTemplate;
    }

    @Bean
    public RedisSerializer fastJson2JsonRedisSerializer() {
        return new FastJson2JsonRedisSerializer<String>(String.class);
    }






    /* @Bean
    public JacksonJsonRedisSerializer JacksonJsonRedisSerializer(){
        JacksonJsonRedisSerializer JacksonJsonRedisSerializer = new JacksonJsonRedisSerializer(Object.class);
    }*/

}
