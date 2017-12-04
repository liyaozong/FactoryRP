package tech.yozo.factoryrp.repository;

import tech.yozo.factoryrp.entity.Permission;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Repository;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description
 */
@Repository
@CacheConfig(cacheNames = "systemPermissions")
public interface PermissionRepository extends BaseRepository<Permission,Long> {
}
