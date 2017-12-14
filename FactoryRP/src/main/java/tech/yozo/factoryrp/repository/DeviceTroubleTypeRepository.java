package tech.yozo.factoryrp.repository;

import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.DeviceSparesType;
import tech.yozo.factoryrp.entity.DeviceTroubleType;

import java.util.List;

/**
 * 设备故障类型Repository
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/14
 * @description
 */
@Repository
public interface DeviceTroubleTypeRepository extends BaseRepository<DeviceTroubleType,Long> {

    /**
     * 根据备件名称进行查询
     * @param name
     * @return
     */
    DeviceTroubleType findByName(String name);


    /**
     * 根据备件名称和企业唯一标识进行查询
     * @param name
     * @param corporateIdentify
     * @return
     */
    DeviceTroubleType findByNameAndCorporateIdentify(String name,Long corporateIdentify);

    /**
     * 根据父级菜单id以及企业唯一标识进行查询
     * @param parentId
     * @param corporateIdentify
     * @return
     */
    List<DeviceTroubleType> findByParentIdAndCorporateIdentify(Long parentId, Long corporateIdentify);

    /**
     * 根据父级菜单id，名称和企业唯一标识进行查询
     * @param parentId
     * @param corporateIdentify
     * @return
     */
    DeviceTroubleType findByParentIdAndCorporateIdentifyAndName(Long parentId, Long corporateIdentify,String name);

    /**
     * 根据父级菜单id进行查询
     * @param parentId
     * @return
     */
    List<DeviceTroubleType> findByparentId(Long parentId);

    /**
     * 根据企业标识进行查询
     * @param corporateIdentify
     * @return
     */
    List<DeviceTroubleType> findByCorporateIdentify(Long corporateIdentify);

}
