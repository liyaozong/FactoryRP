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
     * 根据企业唯一标识以及巡检计划ID进行查询
     * @param corporateIdentify
     * @param spotInspectionPlan
     * @return
     */
    @Query(value = "select s from SpotInspectionPlanDevice s where s.corporateIdentify = :corporateIdentify and s.spotInspectionPlan in :spotInspectionPlan")
    List<SpotInspectionPlanDevice> findByCorporateIdentifyAndSpotInspectionPlanIn(@Param("corporateIdentify") Long corporateIdentify,@Param("spotInspectionPlan")List<Long> spotInspectionPlan);


    /**
     * 根据企业唯一标识和巡检计划ID查询巡检计划
     * @param corporateIdentify
     * @param spotInspectionPlan
     * @return
     */
    @Query(value = "select s from SpotInspectionPlanDevice s where s.corporateIdentify = :corporateIdentify and s.spotInspectionPlan = :spotInspectionPlan")
    List<SpotInspectionPlanDevice> findByCorporateIdentifyAndSpotInspectionPlan(@Param("corporateIdentify") Long corporateIdentify,@Param("spotInspectionPlan") Long spotInspectionPlan);


    /**
     * 根据企业唯一标识，巡检标准ID，设备ID进行查询
     * @param corporateIdentify
     * @param deviceId
     * @param spotInspectionStandard
     * @return
     */
   /* @Query(value = "select s from SpotInspectionPlanDevice s where s.corporateIdentify = :corporateIdentify and s.deviceId = :deviceId and s.spotInspectionStandard = :spotInspectionStandard")
    List<SpotInspectionPlanDevice> findByCorporateIdentifyAndDeviceIdAndSpotInspectionStandard(@Param("corporateIdentify") Long corporateIdentify,
                                                                                               @Param("deviceId") Long deviceId,
                                                                                               @Param("spotInspectionStandard") Long spotInspectionStandard);

*/
    /**
     * 根据企业唯一标识，设备ID和巡检计划ID进行查询
     * @param corporateIdentify
     * @param deviceId
     * @param spotInspectionPlan
     * @return
     */
    @Query(value = "select s from SpotInspectionPlanDevice s where s.corporateIdentify = :corporateIdentify and s.deviceId = :deviceId and s.spotInspectionPlan = :spotInspectionPlan")
    SpotInspectionPlanDevice findByCorporateIdentifyAndDeviceIdAndSpotInspectionPlan(@Param("corporateIdentify") Long corporateIdentify,@Param("deviceId") Long deviceId,
                                                                                               @Param("spotInspectionPlan") Long spotInspectionPlan);


}
