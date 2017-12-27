package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.vo.req.DeviceProcessAddReq;
import tech.yozo.factoryrp.vo.resp.process.CreateProcessInstanceResp;
import tech.yozo.factoryrp.vo.resp.process.DeviceProcessAddResp;

/**
 * 流程相关服务
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
public interface ProcessService {

    /**
     * 开启流程
     * 生成流程实例 返回状态为开启的流程实例
     * @param processType 流程类型
     * @param processStage 流程阶段
     * @return
     */
    CreateProcessInstanceResp createProcessInstance(Long processType, Long processStage, Long corporateIdentify) throws BussinessException;

    /**
     * 新增流程
     * @param deviceProcessAddReq
     * @param corporateIdentify
     * @return
     */
    DeviceProcessAddResp addDeviceProcess(DeviceProcessAddReq deviceProcessAddReq, Long corporateIdentify);

}
