package tech.yozo.factoryrp.repository;

import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.DeviceSparesType;

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

}
