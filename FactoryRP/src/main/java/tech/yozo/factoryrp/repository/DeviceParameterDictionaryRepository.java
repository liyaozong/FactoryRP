package tech.yozo.factoryrp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.DeviceParameterDictionary;

import java.util.List;

/**
 *
 * 设备参数字典Repository
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/10
 * @description
 */
@Repository
public interface DeviceParameterDictionaryRepository extends BaseRepository<DeviceParameterDictionary,Long> {


    /**
     * 根据code和corporateIdentify进行查找
     * @param code
     * @param corporateIdentify
     * @return
     */
    List<DeviceParameterDictionary> findByCodeAndCorporateIdentify(String code,Long corporateIdentify);

    /**
     * 根据code进行查找
     * @param code
     * @return
     */
    List<DeviceParameterDictionary> findByCode(String code);


    /**
     * 根据name进行查找
     * @param name
     * @return
     */
    List<DeviceParameterDictionary> findByName(String name);


    /**
     * 根据code和name和corporateIdentify进行查找
     * @param code
     * @param name
     * @param corporateIdentify
     * @return
     */
    DeviceParameterDictionary findByCodeAndNameAndCorporateIdentify(String code,String name,Long corporateIdentify);


    /**
     * 查询最大的type值
     * @param code
     * @return
     */
    @Query(value = "select COALESCE(max(dic.type),0) from DeviceParameterDictionary dic where dic.code = :code and dic.corporateIdentify = :corporateIdentify")
    Integer findMaxTypeByCodeAndCorporateIdentify(@Param("code") String code,@Param("corporateIdentify") Long corporateIdentify);

}
