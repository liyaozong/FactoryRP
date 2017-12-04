package tech.yozo.factoryrp.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/23
 * @description
 */
@Service
public class RedisRepository {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;



    public String get(Object key){
        return stringRedisTemplate.opsForValue().get(key);
    }
}
