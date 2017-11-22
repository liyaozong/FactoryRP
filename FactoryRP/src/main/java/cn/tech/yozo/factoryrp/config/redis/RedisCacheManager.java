package cn.tech.yozo.factoryrp.config.redis;

import cn.tech.yozo.factoryrp.utils.CheckParam;
import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/22
 * @description
 */
@Service
public class RedisCacheManager extends AbstractCacheManager {


    private static final Logger logger = LoggerFactory.getLogger(RedisCacheManager.class);

    @Resource
    private RedisTemplate redisTemplate;// Redis操作类

    private String shiroRedisCacheKeyPrefix = "shiro_redis_cache_key_prefix:";


    @Override
    public <K, V> Cache<K, V> getCache(String name) throws IllegalArgumentException, CacheException {
        if (CheckParam.isNull(name)) {
            logger.error("Cache name cannot be null or empty.");
            throw new IllegalArgumentException("Cache name cannot be null or empty.");
        }
        return super.getCache(getCacheName(name));
    }

    @Override
    protected Cache createCache(String name) throws CacheException {
        RedisShiroCache cache = new RedisShiroCache<>(redisTemplate, getCacheName(name));

        return cache;
    }

    private String getCacheName(String name){
        return shiroRedisCacheKeyPrefix + name;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getShiroRedisCacheKeyPrefix() {
        return shiroRedisCacheKeyPrefix;
    }

    public void setShiroRedisCacheKeyPrefix(String shiroRedisCacheKeyPrefix) {
        this.shiroRedisCacheKeyPrefix = shiroRedisCacheKeyPrefix;
    }
}
