package tech.yozo.factoryrp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.DeviceProcessType;

import java.util.List;

/**
 * 设备流程类型 Repository
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/10
 * @description
 */
@Repository
public interface DeviceProcessTypeRepository extends BaseRepository<DeviceProcessType,Long> {


    /**
     * 根据企业唯一标识进行查询
     * @param corporateIdentify
     * @return
     */
    List<DeviceProcessType> findByCorporateIdentify(Long corporateIdentify);


    /**
     * 根据code和企业唯一标识进行查询 此处只会返回一个
     * @param code
     * @param corporateIdentify
     * @return
     */
    @Query(value = "select d from DeviceProcessType d where d.code =:code and d.corporateIdentify = :corporateIdentify")
    DeviceProcessType findByCodeAndCorporateIdentify(@Param("code") String code,@Param("corporateIdentify") Long corporateIdentify);

}
