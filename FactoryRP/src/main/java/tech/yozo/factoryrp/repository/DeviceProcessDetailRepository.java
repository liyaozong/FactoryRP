package tech.yozo.factoryrp.repository;

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
    List<DeviceProcessDetail> findByProcessIdAndAndCorporateIdentify(Long processId, Long corporateIdentify);

}
