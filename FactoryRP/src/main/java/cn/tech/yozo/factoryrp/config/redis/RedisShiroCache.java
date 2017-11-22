package cn.tech.yozo.factoryrp.config.redis;

import com.alibaba.fastjson.JSON;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/22
 * @description
 */
public class RedisShiroCache<K, V> implements Cache<K, V> {

    @Resource
    private RedisTemplate redisTemplate;

    private long expireTime = 240;

    private Class<K> keyClass = null;

    private String cacheName;

    public RedisShiroCache(RedisTemplate redisTemplate, String cacheName) {
        super();
        this.redisTemplate = redisTemplate;
        this.cacheName = cacheName;
    }




    @Override
    public V get(K key) throws CacheException {
        if (key != null) {
            keyClass = (Class<K>) key.getClass();
        }

        return (V) redisTemplate.opsForValue().get(getKeyStr(key));
    }

    @Override
    public V put(K key, V value) throws CacheException {
        if (key != null) {
            keyClass = (Class<K>) key.getClass();
        }
        redisTemplate.opsForValue().set(getKeyStr(key), value, expireTime, TimeUnit.SECONDS);
        return value;
    }

    @Override
    public V remove(K key) throws CacheException {
        V value = get(key);
        redisTemplate.delete(getKeyStr(key));
        return value;
    }

    @Override
    public void clear() throws CacheException {
        Set<String> keySet = (Set<String>) redisTemplate.keys(cacheName + ":*");
        if (keySet != null && keySet.size() > 0) {
            redisTemplate.delete(keySet);
        }
    }

    @Override
    public int size() {
        long size = redisTemplate.keys(cacheName + ":*").size();
        return (int) size;
    }

    @Override
    public Set<K> keys() {
        HashSet<K> keys = new HashSet<>();
        Set<String> keySet = (Set<String>) redisTemplate.keys(cacheName + ":*");

        if (keySet != null && keySet.size() > 0) {
            for (String key : keySet) {
                keys.add(parseKey(key));
            }
        }
        return keys;
    }

    @Override
    public Collection<V> values() {
        HashSet<V> values = new HashSet<>();
        Set<String> keySet = (Set<String>) redisTemplate.keys(cacheName + ":*");
        if (keySet != null && keySet.size() > 0) {
            for (String key : keySet) {
                values.add((V) redisTemplate.opsForValue().get(key));
            }
        }
        return values();
    }

    private String getKeyStr(K key) {
        StringBuffer keySb = new StringBuffer();

        keySb.append(cacheName);
        keySb.append(":");
        keySb.append(JSON.toJSONString(key));
        return keySb.toString();
    }

    private K parseKey(String keyStr) {
        if (keyStr.startsWith(cacheName)) {
            String keyJson = keyStr.substring(cacheName.length());

            try {
                K key = JSON.parseObject(keyJson, keyClass);

                return key;
            } catch (Exception e) {

            }
        }
        return null;
    }

}
