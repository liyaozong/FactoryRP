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
     * 根据用户id和企业唯一标识进行查找
     * @param userId
     * @param corporateIdentify
     * @return
     */
    User findByUserIdAndCorporateIdentify(Long userId,Long corporateIdentify);

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
     * 根据用户名和企业标识码进行查找
     * @param userName
     * @param corporateCode
     * @return
     */
    @Query(value = "select u from User u where u.userName = :userName and u.corporateCode = :corporateCode")
    User findByUserNameAndCorporateCode(@Param("userName")String userName,@Param("corporateCode") String corporateCode);

    /**
     *通过用户名进行查找
     * @param userName
     * @return
     */
    User findByUserName(String userName);


    /**
     * 根据企业唯一标识和userId进行in查询
     * @param corporateIdentify
     * @param userIdList
     * @return
     */
    @Query(value = "select u from User u where u.userId in :userIdList and u.corporateCode = :corporateCode")
    List<User> findByCorporateIdentifyAndUserIdIn(@Param("corporateIdentify") Long corporateIdentify,@Param("userIdList") List<Long> userIdList);

}
