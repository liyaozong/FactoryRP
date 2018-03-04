package tech.yozo.factoryrp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.SpotInspectionRecordDetail;

import java.util.Date;
import java.util.List;


/**
 * 点检记录详情Repository
 */
@Repository
public interface SpotInspectionRecordDetailRepository extends BaseRepository<SpotInspectionRecordDetail,Long>  {


    /**
     * 根据企业唯一标识,巡检项目ID和巡检记录ID进行查询
     * @param corporateIdentify
     * @param standardItemId
     * @param recordId
     * @return
     */
    @Query(value = "select s from SpotInspectionRecordDetail s where s.corporateIdentify = :corporateIdentify " +
            "and s.standardItemId = :standardItemId and s.recordId = :recordId")
    List<SpotInspectionRecordDetail> findByCorporateIdentifyAndStandardItemIdAndRecordId(@Param("corporateIdentify") Long corporateIdentify,
                                                                                         @Param("standardItemId") Long standardItemId,
                                                                                         @Param("recordId") Long recordId);


    /**
     * 根据企业唯一标识和巡检项目ID进行IN和时间比较查询 小于
     * @param corporateIdentify
     * @param standardItemIdList
     * @param compareTime
     * @return
     */
    @Query(value = "select s from SpotInspectionRecordDetail s where s.corporateIdentify = :corporateIdentify " +
            "and s.standardItemId in :standardItemIdList and s.createTime < :compareTime")
    List<SpotInspectionRecordDetail> findByCorporateIdentifyAndStandardItemIdInAndCreateTimeLessThan(@Param("corporateIdentify") Long corporateIdentify,
                                                                                @Param("standardItemIdList") List<Long> standardItemIdList,
                                                                                @Param("compareTime") Date compareTime);

    /**
     * 根据企业唯一标识和巡检项目ID进行IN和时间比较查询 大于
     * @param corporateIdentify
     * @param standardItemIdList
     * @param compareTime
     * @return
     */
    @Query(value = "select s from SpotInspectionRecordDetail s where s.corporateIdentify = :corporateIdentify " +
            "and s.standardItemId in :standardItemIdList and s.createTime > :compareTime")
    List<SpotInspectionRecordDetail> findByCorporateIdentifyAndStandardItemIdInAndCreateTimeGreaterThan(@Param("corporateIdentify") Long corporateIdentify,
                                                                                                     @Param("standardItemIdList") List<Long> standardItemIdList,
                                                                                                     @Param("compareTime") Date compareTime);

}
