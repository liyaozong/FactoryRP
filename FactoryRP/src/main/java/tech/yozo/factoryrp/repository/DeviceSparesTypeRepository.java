package tech.yozo.factoryrp.repository;

import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.DeviceSparesType;
import tech.yozo.factoryrp.entity.DeviceTroubleType;

import java.util.List;

/**
 * 备件类型Repository
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/5
 * @description
 */
@Repository
public interface DeviceSparesTypeRepository extends BaseRepository<DeviceSparesType,Long> {


    /**
     * 根据企业标识进行查询
     * @param corporateIdentify
     * @return
     */
    List<DeviceSparesType> findByCorporateIdentify(Long corporateIdentify);

    /**
     * 根据父级菜单id进行查询
     * @param parentId
     * @return
     */
    List<DeviceSparesType> findByparentId(Long parentId);

    /**
     * 根据父级菜单id以及企业唯一标识进行查询
     * @param parentId
     * @param corporateIdentify
     * @return
     */
    List<DeviceSparesType> findByParentIdAndCorporateIdentify(Long parentId,Long corporateIdentify);


    /**
     * 根据父级菜单id，名称和企业唯一标识进行查询
     * @param parentId
     * @param corporateIdentify
     * @return
     */
    DeviceSparesType findByParentIdAndCorporateIdentifyAndName(Long parentId, Long corporateIdentify, String name);

}
