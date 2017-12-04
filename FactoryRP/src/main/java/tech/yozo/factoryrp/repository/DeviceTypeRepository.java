package tech.yozo.factoryrp.repository;

import tech.yozo.factoryrp.entity.DeviceType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceTypeRepository extends BaseRepository<DeviceType,Long>{
    /**
     * 根据企业标识和数据有效性查询设备类型列表
     * @param corporateIdentify
     * @param statusFlag
     * @return
     */
    public List<DeviceType> findByCorporateIdentifyAndStatusFlag(Long corporateIdentify, Integer statusFlag);

    /**
     * 根据上级ID查询
     * @param parentId
     * @return
     */
    public List<DeviceType> findByParentId(Long parentId);
}
