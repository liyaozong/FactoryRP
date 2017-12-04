package tech.yozo.factoryrp.repository;

import tech.yozo.factoryrp.entity.MenuRole;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Repository;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/21
 * @description
 */
@Repository
@CacheConfig(cacheNames = "menuRoles")
public interface MenuRoleRepository extends BaseRepository<MenuRole,Long>{


    /**
     * 根据角色id，菜单id,企业唯一标识进行查询
     * @param roleId
     * @param menuId
     * @param corporateIdentify
     * @return
     */
    MenuRole findByRoleIdAndMenuIdAndCorporateIdentify(Long roleId,Long menuId,Long corporateIdentify);

}
