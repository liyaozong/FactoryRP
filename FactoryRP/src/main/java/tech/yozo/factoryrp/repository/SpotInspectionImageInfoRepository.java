package tech.yozo.factoryrp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.SpotInspectionImageInfo;

import java.util.List;

/**
 * 点巡检图片信息
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/15
 * @description
 */
@Repository
public interface SpotInspectionImageInfoRepository extends BaseRepository<SpotInspectionImageInfo,Long>{


    /**
     * 根据企业唯一标识，巡检计划ID，巡检标准ID，巡检记录ID进行查询
     * @param corporateIdentify
     * @param spotInspectionPlan
     * @param spotInspectionStandard
     * @param recordId
     * @return
     */
    @Query(value = "select s from SpotInspectionImageInfo s where s.corporateIdentify = :corporateIdentify and s.spotInspectionPlan = :spotInspectionPlan and s.spotInspectionStandard = " +
            ":spotInspectionStandard and s.recordId = :recordId ")
    List<SpotInspectionImageInfo> findByCorporateIdentifyAndSpotInspectionPlanAndSpotInspectionStandardAndRecordId(@Param("corporateIdentify") Long corporateIdentify,
                                                                                                                   @Param("spotInspectionPlan") Long spotInspectionPlan,
                                                                                                                   @Param("spotInspectionStandard") Long spotInspectionStandard,
                                                                                                                   @Param("recordId") Long recordId
    );



}
