package cn.tech.yozo.factoryrp.repository;

import cn.tech.yozo.factoryrp.entity.Role;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description
 */
@Repository
@CacheConfig(cacheNames = "systemRoles")
public interface RoleRepository extends BaseRepository<Role,Long>{


    /**
     * 通过id的in查询
     * @param ids
     * @return
     */
    List<Role> findByIdIn(long [] ids);


    /**
     * 根据企业标识查询角色
     * @param corporateIdentify
     * @return
     */
    List<Role> findByCorporateIdentify(Long corporateIdentify);

}
