package cn.tech.yozo.factoryrp.config.ehcache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.event.CacheManagerEventListener;
import net.sf.ehcache.event.CacheManagerEventListenerFactory;

import java.util.Properties;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description
 */
public class CustomerCacheManagerEventListenerFactory extends CacheManagerEventListenerFactory {
    @Override
    public CacheManagerEventListener createCacheManagerEventListener(CacheManager cacheManager, Properties properties) {
        return new CustomerCacheManagerEventListener(cacheManager);
    }


}
