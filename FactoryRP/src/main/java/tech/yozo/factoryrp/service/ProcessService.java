package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.entity.DeviceProcess;
import tech.yozo.factoryrp.entity.DeviceProcessDetail;
import tech.yozo.factoryrp.entity.DeviceProcessType;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.vo.req.DeviceProcessAddReq;
import tech.yozo.factoryrp.vo.req.DeviceProcessQueryReq;
import tech.yozo.factoryrp.vo.resp.process.*;

import java.util.List;

/**
 * 流程相关服务
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
public interface ProcessService {

    /**
     * 流程步骤查询
     * 业务逻辑: 不给步数就返回第一步和返回下一步的ID
     *          没有下一步ID就是空
     * @param processType 流程类型
     * @param processStage 流程状态
     * @param processStep 流程步数 可为空
     * @param corporateIdentify 企业唯一标识
     * @return
     */
    DeviceProcessStepQueryResp processStepQuery(Long processType, Long processStage,Integer processStep, Long corporateIdentify);

    /**
     * 分步查询流程详细信息
     * @param processType  流程类型
     * @param processStage 流程状态
     * @param corporateIdentify 企业唯一标识
     * @param processStep 步数ID
     * @return
     */
    DeviceProcessDetailWarpResp queryProcessAduitInfoByStep(Long processType, Long processStage,Integer processStep, Long corporateIdentify);

    /**
     * 查询流程详情-->前端画图所需要的数据
     * @param processId
     * @param corporateIdentify
     * @return
     */
    DeviceProcessDetailQueryWarpResp queryProcessDetail(Long processId, Long corporateIdentify);

    /**
     * 查询流程详细信息
     * @param processType
     * @param processStage
     * @param corporateIdentify
     * @return
     */
    List<DeviceProcessDetailWarpResp> queryProcessAduitInfo(Long processType, Long processStage, Long corporateIdentify);

    /**
     * 查询所有流程类型集合
     * @param corporateIdentify
     * @return
     */
    List<DeviceProcessType> queryAllDecviceProcessType(Long corporateIdentify);

    /**
     * 查询流程状态 查询当前流程实例的状态以及流程详细步骤的状态
     * @param processInstanceId 流程实例ID
     * @param corporateIdentify 企业唯一标识
     * @return
     * @throws BussinessException
     */
    //ProcessStatusQueryResp queryProcessStatus(Long processInstanceId, Long corporateIdentify) throws BussinessException;

    /**
     * 开启流程
     * 生成流程实例 返回状态为开启的流程实例
     * @param processType 流程类型
     * @param processStage 流程阶段
     * @return
     */
    //CreateProcessInstanceResp createProcessInstance(Long processType, Long processStage, Long corporateIdentify) throws BussinessException;

    /**
     * 新增流程
     * @param deviceProcessAddReq
     * @param corporateIdentify
     * @return
     */
    DeviceProcessAddResp addDeviceProcess(DeviceProcessAddReq deviceProcessAddReq, Long corporateIdentify);


    /**
     * 流程分页查询
     * @param deviceProcessQueryReq
     * @param corporateIdentify
     * @return
     */
    Pagination<DeviceProcess> findByPage(DeviceProcessQueryReq deviceProcessQueryReq, Long corporateIdentify);

}
