package tech.yozo.factoryrp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.yozo.factoryrp.entity.MenuRole;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.UserRole;

import java.util.List;

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


    /**
     * 根据企业标识和角色id进行查询
     * @param roleId
     * @param corporateIdentify
     * @return
     */
    List<MenuRole> findByRoleIdAndCorporateIdentify(Long roleId,Long corporateIdentify);


    /**
     * 根据菜单id进行查询
     * @param menuId
     * @param corporateIdentify
     * @return
     */
    List<MenuRole> findByMenuIdAndCorporateIdentify(Long menuId,Long corporateIdentify);

    /**
     * 根据菜单id集合进行in查询
     * @param roleId
     * @param corporateIdentify
     * @param menuIdList
     * @return
     */
    @Query("select m from MenuRole m where m.roleId = :roleId and m.corporateIdentify = :corporateIdentify and m.menuId in :menuIdList")
    List<MenuRole> findByRoleIdAndCorporateIdentifyAndMenuIdIn(@Param("roleId") Long roleId, @Param("corporateIdentify")Long corporateIdentify, @Param("menuIdList")List<Long> menuIdList);




}
