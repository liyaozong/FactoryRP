package tech.yozo.factoryrp.repository;

import tech.yozo.factoryrp.entity.DeviceInfo;
import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.DeviceType;

import java.util.List;

/**
 * 设备信息
 */
@Repository
public interface DeviceInfoRepository extends BaseRepository<DeviceInfo,Long>{

    DeviceInfo findByCodeAndCorporateIdentify(String code,Long corporateIdentify);

    /**
     * 根据企业唯一标识和设备类型进行查找
     * @param corporateIdentify
     * @param deviceType
     * @return
     */
    List<DeviceInfo> findByCorporateIdentifyAndDeviceType(Long corporateIdentify,Long deviceType);
}
