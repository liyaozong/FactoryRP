package tech.yozo.factoryrp.repository;

import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.SpotInspectionStandard;

/**
 * 巡检标准Repository
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/22
 * @description
 */
@Repository
public interface SpotInspectionStandardRepository extends BaseRepository<SpotInspectionStandard,Long> {


    /**
     * 根据名称和企业唯一标识进行查询
     * @param name
     * @param corporateIdentify
     * @return
     */
    SpotInspectionStandard findByNameAndCorporateIdentify(String name,Long corporateIdentify);

}
