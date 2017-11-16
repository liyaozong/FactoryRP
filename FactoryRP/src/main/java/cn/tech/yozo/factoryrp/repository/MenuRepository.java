package cn.tech.yozo.factoryrp.repository;

import cn.tech.yozo.factoryrp.entity.Menu;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Repository;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description 系统菜单Repository
 */
@Repository
@CacheConfig(cacheNames = "systemMenus")
public interface MenuRepository extends BaseRepository<Menu,Long>{




}
