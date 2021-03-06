package tech.yozo.factoryrp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
     * 根据用户id，企业唯一标识，角色集合进行in查询
     * @param userId
     * @param corporateIdentify
     * @param roleList
     * @return
     */
    @Query("select r from UserRole r where r.userId = :userId and r.corporateIdentify = :corporateIdentify and r.roleId in :roleList")
    List<UserRole> findByUserIdAndCorporateIdentifyAndRoleIdIn(@Param("userId") Long userId, @Param("corporateIdentify")Long corporateIdentify, @Param("roleList")List<Long> roleList);

    /**
     * 根据用户id角色id还有企业标识进行查找
     * @param userId
     * @param roleId
     * @param corporateIdentify
     * @return
     */
    UserRole findByUserIdAndRoleIdAndCorporateIdentify(Long userId,Long roleId,Long corporateIdentify);


    /**
     * 根据用户id和企业标识进行查找
     * @param userId
     * @param corporateIdentify
     * @return
     */
    List<UserRole> findByUserIdAndCorporateIdentify(Long userId,Long corporateIdentify);


    /**
     * 根据角色id和企业标识进行查找
     * @param roleId
     * @param corporateIdentify
     * @return
     */
    List<UserRole> findByRoleIdAndCorporateIdentify(Long roleId,Long corporateIdentify);

}
