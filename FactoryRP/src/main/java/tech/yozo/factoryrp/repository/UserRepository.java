package tech.yozo.factoryrp.repository;

import tech.yozo.factoryrp.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
     * 根据用户名和企业唯一标识进行查询
     * @param userName
     * @param corporateIdentify
     * @return
     *//*
    User findByUserNameAndCorporateIdentify(String userName,String corporateIdentify);*/

    /**
     * 根据企业标识进行查找
     * @param corporateIdentify
     * @return
     */
    List<User> findByCorporateIdentify(Long corporateIdentify);


    /**
     * 根据用户id和企业标识进行查询
     * @param id
     * @param corporateIdentify
     * @return
     */
    User findByIdAndCorporateIdentify(Long id,Long corporateIdentify);

    /**
     * 通过用户名和企业标识进行查找
     * @param userName
     * @param corporateIdentify
     * @return
     */
    @Query(value = "select u from User u where userName = :userName and corporateIdentify = :corporateIdentify")
    User findByUserNameAndCorporateIdentify(@Param("userName") String userName, @Param("corporateIdentify")Long corporateIdentify);


    /**
     *通过用户名进行查找
     * @param userName
     * @return
     */
    User findByUserName(String userName);

}
