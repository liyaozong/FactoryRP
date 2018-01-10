package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.entity.DeviceProcess;
import tech.yozo.factoryrp.entity.DeviceProcessType;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.vo.req.DeviceProcessAddReq;
import tech.yozo.factoryrp.vo.req.DeviceProcessQueryReq;
import tech.yozo.factoryrp.vo.resp.process.CreateProcessInstanceResp;
import tech.yozo.factoryrp.vo.resp.process.DeviceProcessAddResp;
import tech.yozo.factoryrp.vo.resp.process.ProcessStatusQueryResp;

import java.util.List;

/**
 * 流程相关服务
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
public interface ProcessService {


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
