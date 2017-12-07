package tech.yozo.factoryrp.repository;

import tech.yozo.factoryrp.entity.Menu;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description 系统菜单Repository
 */
@Repository
@CacheConfig(cacheNames = "systemMenus")
public interface MenuRepository extends BaseRepository<Menu,Long>{


    /**
     * 根据名称,url,企业唯一标识进行查找
     * @param name
     * @param url
     * @param corporateIdentify
     * @return
     */
    Menu findByNameAndUrlAndCorporateIdentify(String name,String url,Long corporateIdentify);


    /**
     * 根据企业标识进行查找
     * @param corporateIdentify
     * @return
     */
    List<Menu> findByCorporateIdentify(Long corporateIdentify);

}