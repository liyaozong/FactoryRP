package cn.tech.yozo.factoryrp.config.redis;

import cn.tech.yozo.factoryrp.utils.CheckParam;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/22
 * @description
 */
@Configuration
public class RedisSessionRepository extends AbstractSessionDAO {

    // Session超时时间，单位为毫秒
    private long expireTime = 120000;


    @Resource
    private RedisTemplate redisTemplate;// Redis操作类

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    public RedisSessionRepository() {
        super();
    }

    public RedisSessionRepository(long expireTime, RedisTemplate redisTemplate) {
        super();
        this.expireTime = expireTime;
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);


       if(!CheckParam.isNull(redisTemplate)){
            redisTemplate.setConnectionFactory(jedisConnectionFactory());
           redisTemplate.setDefaultSerializer(new RedisObjectSerializer());
           redisTemplate.setValueSerializer(new FastJson2JsonRedisSerializer<Object>(Object.class));
           redisTemplate.setKeySerializer(new  FastJson2JsonRedisSerializer<Object>(Object.class));
           redisTemplate.setEnableDefaultSerializer(false);
        }

        System.out.println("sessionId:"+session.getId()+"session"+JSON.toJSONString(session));

       /* try{
            RedisTemplate redisTemplate = RedisConfig.getRedisTemplate();
            redisTemplate.opsForValue().set(session.getId(),session.getId(),expireTime);
            System.out.println("从Redis里面查询出来的token"+JSON.toJSONString(redisTemplate.opsForValue().get(session.getId())));
        }catch (Exception e){
            e.printStackTrace();

        }*/

        redisTemplate.opsForValue().set(session.getId(),session,expireTime);
        //redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.MILLISECONDS);


        Session n = (Session)redisTemplate.opsForValue().get(session.getId());

        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            return null;
        }
        return (Session) redisTemplate.opsForValue().get(sessionId);
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            return;
        }
        session.setTimeout(expireTime);
        redisTemplate.opsForValue().set(session.getId(), session, expireTime, TimeUnit.MILLISECONDS);
    }

    @Override
    public void delete(Session session) {
        if (null == session) {
            return;
        }
        redisTemplate.opsForValue().getOperations().delete(session.getId());
    }


    //@Bean(name = "jedisPoolConfig")
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(6);
        jedisPoolConfig.setMaxTotal(32);
        jedisPoolConfig.setMaxWaitMillis(15000);
        jedisPoolConfig.setTimeBetweenEvictionRunsMillis(60000L);
        jedisPoolConfig.setNumTestsPerEvictionRun(3);

        return jedisPoolConfig;

    }

    //@Bean(name = "jedisConnectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setPoolConfig(jedisPoolConfig());
        jedisConnectionFactory.setHostName(host);
        jedisConnectionFactory.setPort(port);
        jedisConnectionFactory.setUsePool(true);

        JedisShardInfo shardInfo = new JedisShardInfo(host,port);
        jedisConnectionFactory.setShardInfo(shardInfo);
        jedisConnectionFactory.setHostName(host);
        return jedisConnectionFactory;
    }

    /**
     * 拿到活跃的session，可以用来统计在线人数，如果要实现这个功能，可以在将session加入redis时指定一个session前缀，
     * 统计的时候则使用keys("session-prefix*")的方式来模糊查找redis中所有的session集合
     * @return
     */
    @Override
    public Collection<Session> getActiveSessions() {
        return redisTemplate.keys("*");
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}


