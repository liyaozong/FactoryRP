package cn.tech.yozo.factoryrp.config.ehcache;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Status;
import net.sf.ehcache.event.CacheManagerEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description
 */
public class CustomerCacheManagerEventListener implements CacheManagerEventListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private  CacheManager cacheManager;

    public CustomerCacheManagerEventListener(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void init() throws CacheException {
        logger.info("init ehcache...");
    }

    @Override
    public Status getStatus() {
        return null;
    }

    @Override
    public void dispose() throws CacheException {
        logger.info("ehcache dispose...");
    }

    @Override
    public void notifyCacheAdded(String s) {
        logger.info("cacheAdded... {}", s);
        logger.info(cacheManager.getCache(s).toString());
    }

    @Override
    public void notifyCacheRemoved(String s) {

    }

}
