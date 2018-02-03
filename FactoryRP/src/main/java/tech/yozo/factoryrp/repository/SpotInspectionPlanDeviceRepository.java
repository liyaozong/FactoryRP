package tech.yozo.factoryrp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.SpotInspectionPlanDevice;

import java.util.List;

/**
 * 点检计划-设备关联Repository
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/25
 * @description
 */
@Repository
public interface SpotInspectionPlanDeviceRepository extends BaseRepository<SpotInspectionPlanDevice,Long> {


    /**
     * 根据企业唯一表示以及巡检计划ID进行查询
     * @param corporateIdentify
     * @param spotInspectionPlan
     * @return
     */
    @Query(value = "select s from SpotInspectionPlanDevice s where s.corporateIdentify = :corporateIdentify and s.spotInspectionPlan in :spotInspectionPlan")
    List<SpotInspectionPlanDevice> findByCorporateIdentifyAndSpotInspectionPlanIn(@Param("corporateIdentify") Long corporateIdentify,@Param("spotInspectionPlan")List<Long> spotInspectionPlan);


}
