package tech.yozo.factoryrp.repository;

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

}
