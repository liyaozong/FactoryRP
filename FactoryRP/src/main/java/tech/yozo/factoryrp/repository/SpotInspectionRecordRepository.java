package tech.yozo.factoryrp.repository;


import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.SpotInspectionRecord;

import java.util.List;

/**
 * 异常记录 Repository
 */
@Repository
public interface SpotInspectionRecordRepository extends BaseRepository<SpotInspectionRecord,Long>   {


    /**
     * 根据企业唯一标识，巡检计划ID进行查询
     * @param CorporateIdentify
     * @param planId
     * @return
     */
    List<SpotInspectionRecord> findByCorporateIdentifyAndPlanId(Long CorporateIdentify, Long planId);



}
