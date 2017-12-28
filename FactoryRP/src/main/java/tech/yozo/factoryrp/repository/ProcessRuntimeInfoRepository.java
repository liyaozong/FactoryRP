package tech.yozo.factoryrp.repository;

import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.ProcessRuntimeInfo;

import java.util.List;

/**
 * 流程运行信息Repository
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
@Repository
public interface ProcessRuntimeInfoRepository extends BaseRepository<ProcessRuntimeInfo,Long> {


    /**
     * 根据企业唯一标识以及流程实例id进行查询流程运行信息
     * @param processInstanceId
     * @param corporateIdentify
     * @return
     */
    List<ProcessRuntimeInfo> findByProcessInstanceIdAndCorporateIdentify(Long processInstanceId, Long corporateIdentify);


    /**
     * 根据企业唯一表示,流程实例ID,流程详情ID进行查询
     * @param processDetailId
     * @param corporateIdentify
     * @param processInstanceId
     * @return
     */
    ProcessRuntimeInfo findByProcessDetailIdAndProcessInstanceIdAndCorporateIdentify(Long processDetailId,Long processInstanceId,Long corporateIdentify);

}
