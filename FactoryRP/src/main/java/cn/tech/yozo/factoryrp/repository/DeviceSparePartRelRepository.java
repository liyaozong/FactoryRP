package cn.tech.yozo.factoryrp.repository;

import cn.tech.yozo.factoryrp.entity.DeviceSparePartRel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author chenxiang
 * @create 2017-12-02 上午10:31
 **/
@Repository
public interface DeviceSparePartRelRepository extends BaseRepository<DeviceSparePartRel,Long>{

    /**
     * 根据企业标识和设备ID查询关联关系
     * @param deviceId
     * @param corporateIdentify
     * @return
     */
    public List<DeviceSparePartRel> findByDeviceIdAndCorporateIdentify(Long deviceId,Long corporateIdentify);

    /**
     * 根据备件ID和企业标识查询关联关系
     * @param sparePartId
     * @param corporateIdentify
     * @return
     */
    public List<DeviceSparePartRel> findBySparePartIdAndCorporateIdentify(Long sparePartId,Long corporateIdentify);
}
