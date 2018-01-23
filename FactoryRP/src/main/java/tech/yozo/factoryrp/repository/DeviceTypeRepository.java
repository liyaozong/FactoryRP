package tech.yozo.factoryrp.repository;

import org.springframework.data.repository.query.Param;
import tech.yozo.factoryrp.entity.DeviceTroubleType;
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
    List<DeviceType> findByCorporateIdentifyAndStatusFlag(Long corporateIdentify, Integer statusFlag);

    /**
     * 根据上级ID查询
     * @param parentId
     * @return
     */
    List<DeviceType> findByParentId(Long parentId);


    /**
     * 根据父级菜单id以及企业唯一标识进行查询
     * @param parentId
     * @param corporateIdentify
     * @return
     */
    List<DeviceType> findByParentIdAndCorporateIdentify(Long parentId, Long corporateIdentify);


    /**
     * 根据父级菜单id，名称和企业唯一标识进行查询
     * @param parentId
     * @param corporateIdentify
     * @return
     */
    DeviceType findByParentIdAndCorporateIdentifyAndName(Long parentId, Long corporateIdentify,String name);

}
