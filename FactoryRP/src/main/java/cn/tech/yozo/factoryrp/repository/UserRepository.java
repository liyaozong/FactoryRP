package cn.tech.yozo.factoryrp.repository;

import cn.tech.yozo.factoryrp.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description
 */
@Repository
@CacheConfig(cacheNames = "systemUsers")
public interface UserRepository extends BaseRepository<User,Long>{


    /**
     * 根据企业标识进行查找
     * @param corporateIdentify
     * @return
     */
    List<User> findByCorporateIdentify(Long corporateIdentify);

    /**
     * 通过用户名和企业标识进行查找
     * @param userName
     * @param corporateIdentify
     * @return
     */
    @Cacheable
    User findByUserNameAndCorporateIdentify(String userName,Long corporateIdentify);


    /**
     *通过用户名进行查找
     * @param userName
     * @return
     */
    User findByUserName(String userName);

}
