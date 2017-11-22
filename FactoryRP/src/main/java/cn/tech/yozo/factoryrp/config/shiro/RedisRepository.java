/*
package cn.tech.yozo.factoryrp.config.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.TimeUnit;

*/
/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/22
 * @description
 *//*

public class RedisRepository  extends AbstractSessionDAO {

    private static Logger logger = LoggerFactory.getLogger(RedisRepository.class);


    @Autowired
    private RedisTemplate redisTemplate;

    */
/**
     * The Redis key prefix for the sessions
     *//*

    private String sessionRedisKeyPrefix = "facteryRP_session_key:";

    private long sessionExpireTime = 300;

    @Override
    public void update(Session session) throws UnknownSessionException {
        this.saveSession(session);
    }

    */
/**
     * save session
     *
     * @param session
     * @throws UnknownSessionException
     *//*

    private void saveSession(Session session) throws UnknownSessionException {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }

        session.setTimeout(sessionExpireTime * 1000);
        redisTemplate.opsForValue().set(getKey(session.getId()), from((SimpleSession) session), sessionExpireTime,
                TimeUnit.SECONDS);
    }

    @Override
    public void delete(Session session) {
        if (session == null || session.getId() == null) {
            logger.error("session or session id is null");
            return;
        }
        redisTemplate.delete(getKey(session.getId()));
    }

    private void delete(Serializable sessionId) {
        if (sessionId == null) {
            logger.error("session or session id is null");
            return;
        }
        redisTemplate.delete(getKey(sessionId));
    }

    // 用来统计当前活动的session
    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();

        Set<String> keys = redisTemplate.keys(getSessionRedisKeyPrefix() + "*");
        if (keys != null && keys.size() > 0) {
            for (String key : keys) {
                MySession mySession = null;
                try {
                    mySession = (MySession) redisTemplate.opsForValue().get(key);
                } catch (JsonSyntaxException e) {
                    logger.error("doReadSession", e);
                    delete(key);
                    continue;
                }
                sessions.add(from(mySession));
            }
        }
        return sessions;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        this.saveSession(session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (sessionId == null) {
            logger.error("session id is null");
            return null;
        }
        MySession mySession = null;
        try {
            mySession = (MySession) redisTemplate.opsForValue().get(getKey(sessionId));

            if (mySession != null) {
                return from(mySession);
            }
        } catch (JsonSyntaxException e) {
            logger.error("doReadSession", e);
            delete(sessionId);
            return null;
        }
        return null;
    }

    */
/**
     * 获得byte[]型的key
     *
     * @param sessionId
     * @return
     *//*

    private String getKey(Serializable sessionId) {
        return getSessionRedisKeyPrefix() + sessionId.toString();
    }

    */
/**
     * Returns the Redis session keys prefix.
     *
     * @return The prefix
     *//*

    public String getSessionRedisKeyPrefix() {
        return sessionRedisKeyPrefix;
    }

    */
/**
     * Sets the Redis sessions key prefix.
     *
     * @param sessionRedisKey
     *            The prefix
     *//*

    public void setSessionRedisKeyPrefix(String sessionRedisKey) {
        this.sessionRedisKeyPrefix = sessionRedisKey;
    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public long getSessionExpireTime() {
        return sessionExpireTime;
    }

    public void setSessionExpireTime(long sessionExpireTime) {
        this.sessionExpireTime = sessionExpireTime;
    }

    private SimpleSession from(MySession session) {
        SimpleSession simpleSession = new SimpleSession();

        simpleSession.setAttributes(session.getAttributes());

        simpleSession.setId(session.getId());

        simpleSession.setHost(session.getHost());
        simpleSession.setLastAccessTime(session.getLastAccessTime());
        simpleSession.setStartTimestamp(session.getStartTimestamp());
        simpleSession.setStopTimestamp(session.getStopTimestamp());
        simpleSession.setTimeout(session.getTimeout());
        simpleSession.setExpired(session.isExpired());

        return simpleSession;
    }

    private MySession from(SimpleSession session) {
        MySession mySession = new MySession();

        mySession.setAttributes(session.getAttributes());
        mySession.setId(session.getId().toString());
        mySession.setTimeout(session.getTimeout());
        mySession.setHost(session.getHost());
        mySession.setLastAccessTime(session.getLastAccessTime());
        mySession.setStartTimestamp(session.getStartTimestamp());
        mySession.setStopTimestamp(session.getStopTimestamp());
        mySession.setExpired(session.isExpired());

        return mySession;
    }

    class MySession implements Serializable {

        private static final long serialVersionUID = -6665379281912426514L;

        private String id;

        private Date startTimestamp;

        private Date stopTimestamp;

        private Date lastAccessTime;

        private long timeout;

        private boolean expired;

        private String host;

        private Map<Object, Object> attributes;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Date getStartTimestamp() {
            return startTimestamp;
        }

        public void setStartTimestamp(Date startTimestamp) {
            this.startTimestamp = startTimestamp;
        }

        public Date getStopTimestamp() {
            return stopTimestamp;
        }

        public void setStopTimestamp(Date stopTimestamp) {
            this.stopTimestamp = stopTimestamp;
        }

        public Date getLastAccessTime() {
            return lastAccessTime;
        }

        public void setLastAccessTime(Date lastAccessTime) {
            this.lastAccessTime = lastAccessTime;
        }

        public long getTimeout() {
            return timeout;
        }

        public void setTimeout(long timeout) {
            this.timeout = timeout;
        }

        public boolean isExpired() {
            return expired;
        }

        public void setExpired(boolean expired) {
            this.expired = expired;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public Map<Object, Object> getAttributes() {
            return attributes;
        }

        public void setAttributes(Map<Object, Object> attributes) {
            this.attributes = attributes;
        }
    }

}
*/
