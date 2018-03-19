package tech.yozo.factoryrp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.DeviceProcessDetail;

import java.util.List;

/**
 * 流程详情Repository
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
@Repository
public interface DeviceProcessDetailRepository extends BaseRepository<DeviceProcessDetail,Long>{


    /**
     * 根据流程步骤,流程ID和企业唯一标识进行查询
     * @param processId
     * @param processStep
     * @param corporateIdentify
     * @return
     */
    DeviceProcessDetail findByProcessIdAndProcessStepAndCorporateIdentify(Long processId,Integer processStep,Long corporateIdentify);


    /**
     * 根据流程id和企业唯一标识进行查找
     * @param processId
     * @param corporateIdentify
     * @return
     */
    List<DeviceProcessDetail> findByProcessIdAndCorporateIdentify(Long processId, Long corporateIdentify);


    /**
     * 根据流程ID和企业唯一标识进行查询
     * @param processIdList
     * @param corporateIdentify
     * @return
     */
    @Query(value = "select d from DeviceProcessDetail d where d.processId in :processIdList and d.corporateIdentify = :corporateIdentify")
    List<DeviceProcessDetail> findByProcessIdInAndCorporateIdentify(@Param("processIdList") List<Long> processIdList,@Param("corporateIdentify") Long corporateIdentify);

    /**
     * 根据流程id和企业唯一标识进行查找最小的详细流程步骤
     * @param processId
     * @param corporateIdentify
     * @return
     */
    @Query(value = "select min(p.processStep) from DeviceProcessDetail p where p.processId = :processId and p.corporateIdentify = :corporateIdentify")
    DeviceProcessDetail findMinProcessStepByProcessIdAndAndCorporateIdentify(@Param("processId") Long processId,@Param("corporateIdentify") Long corporateIdentify);

    /**
     * 根据流程id,企业唯一标识,步数进行查找
     * @param processId
     * @param corporateIdentify
     * @param processStep
     * @return
     */
    DeviceProcessDetail findByProcessIdAndAndCorporateIdentifyAndProcessStep(Long processId, Long corporateIdentify,Integer processStep);

}
