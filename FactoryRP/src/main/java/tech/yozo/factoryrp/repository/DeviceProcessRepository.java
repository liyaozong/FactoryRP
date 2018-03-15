package tech.yozo.factoryrp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.DeviceProcess;

/**
 *
 * 设备流程类型
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
@Repository
public interface DeviceProcessRepository extends BaseRepository<DeviceProcess,Long> {



    /**
     * 根据企业唯一标识和流程名称进行查询
     * @param processName
     * @param corporateIdentify
     * @return
     */
    DeviceProcess findByProcessNameAndCorporateIdentify(String processName,Long corporateIdentify);


    /**
     * 根据流程类型，对应阶段以及企业唯一标识进行查询
     * @param processType
     * @param processStage
     * @param corporateIdentify
     * @return
     */
    DeviceProcess findByProcessTypeAndProcessStageAndCorporateIdentify(String processType,String processStage,Long corporateIdentify);


    /**
     * 根据条件查询流程
     * @param processType
     * @param processStage
     * @param triggerConditionType
     * @param triggerCondition
     * @param corporateIdentify
     * @return
     */
    DeviceProcess findByProcessTypeAndProcessStageAndTriggerConditionTypeAndTriggerConditionAndCorporateIdentify(String processType,String processStage,Long triggerConditionType,Long triggerCondition,Long corporateIdentify);


    /**
     * 根据流程类型，对应阶段,企业唯一标识,流程名称以及进行查询
     * @param processType
     * @param processStage
     * @param corporateIdentify
     * @param processName
     * @return
     */
    DeviceProcess findByProcessTypeAndProcessStageAndCorporateIdentifyAndProcessName(String processType,String processStage,Long corporateIdentify,String processName);
}
