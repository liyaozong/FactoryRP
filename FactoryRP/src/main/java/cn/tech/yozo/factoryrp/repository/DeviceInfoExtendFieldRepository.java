package cn.tech.yozo.factoryrp.repository;

import cn.tech.yozo.factoryrp.entity.DeviceInfoExtendField;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 设备信息扩展字段
 */
@Repository
public interface DeviceInfoExtendFieldRepository extends BaseRepository<DeviceInfoExtendField,Long>{
    /**
     * 根据企业标识和数据有效性查询设备扩展字段
     * @param corporateIdentify
     * @param statusFlag
     * @return
     */
    public DeviceInfoExtendField findByCorporateIdentifyAndStatusFlag(Long corporateIdentify, Integer statusFlag);
}
