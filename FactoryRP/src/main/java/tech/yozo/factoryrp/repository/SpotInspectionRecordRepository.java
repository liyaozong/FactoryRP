package tech.yozo.factoryrp.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.SpotInspectionRecord;
import tech.yozo.factoryrp.entity.SpotInspectionRecordDetail;

import java.util.Date;
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


    /**
     * 根据企业唯一标识，巡检计划ID创建时间进行查询 查询出来表示当前巡检计划在周期内被执行过
     * @param corporateIdentify
     * @param planId
     * @param compareTime
     * @return
     */
    @Query(value = "select s from SpotInspectionRecord s where s.corporateIdentify = :corporateIdentify " +
            "and s.planId = :planId and s.createTime > :compareTime")
    SpotInspectionRecord findByCorporateIdentifyAndPlanIdAndCreateTimeGreaterThan(@Param("corporateIdentify") Long corporateIdentify,
                                                                                  @Param("planId") Long planId,
                                                                                  @Param("compareTime") Date compareTime);



}
