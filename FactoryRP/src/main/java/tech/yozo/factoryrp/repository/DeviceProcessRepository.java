package tech.yozo.factoryrp.repository;

import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.DeviceProcess;

/**
 *
 * 设备流程类型
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
@Repository
public interface DeviceProcessRepository extends BaseRepository<DeviceProcess,Long> {


    /**
     * 根据企业唯一标识和流程名称进行查询
     * @param processName
     * @param corporateIdentify
     * @return
     */
    DeviceProcess findByProcessNameAndCorporateIdentify(String processName,Long corporateIdentify);


}
