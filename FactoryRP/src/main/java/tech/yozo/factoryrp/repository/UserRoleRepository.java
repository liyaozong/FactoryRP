package tech.yozo.factoryrp.repository;

import tech.yozo.factoryrp.entity.UserRole;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description
 */
@Repository
@CacheConfig(cacheNames = "systemUserRoles")
public interface UserRoleRepository extends BaseRepository<UserRole,Long>{


    /**
     * 根据用户ID进行查找
     * @param userId
     * @return
     */
    List<UserRole> findByUserId(Long userId);


    /**
     * 根据用户id角色id还有企业标识进行查找
     * @param userId
     * @param roleId
     * @param corporateIdentify
     * @return
     */
    UserRole findByUserIdAndRoleIdAndCorporateIdentify(Long userId,Long roleId,Long corporateIdentify);

}
