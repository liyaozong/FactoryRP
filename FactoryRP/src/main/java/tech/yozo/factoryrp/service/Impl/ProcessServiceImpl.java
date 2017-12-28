package tech.yozo.factoryrp.service.Impl;

import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.DeviceProcess;
import tech.yozo.factoryrp.entity.DeviceProcessDetail;
import tech.yozo.factoryrp.entity.ProcessInstance;
import tech.yozo.factoryrp.entity.ProcessRuntimeInfo;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.repository.*;
import tech.yozo.factoryrp.service.ProcessService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.DeviceProcessAddReq;
import tech.yozo.factoryrp.vo.resp.process.DeviceProcessAddResp;
import tech.yozo.factoryrp.vo.resp.process.CreateProcessInstanceResp;
import tech.yozo.factoryrp.vo.resp.process.ProcessStatusQueryResp;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 流程相关服务
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
@Service
public class ProcessServiceImpl implements ProcessService {

    @Resource
    private DeviceProcessRepository deviceProcessRepository;


    @Resource
    private DeviceProcessDetailRepository deviceProcessDetailRepository;


    @Resource
    private ProcessRuntimeInfoRepository processRuntimeInfoRepository;


    @Resource
    private ProcessInstanceRepository processInstanceRepository;


    /**
     * 查询流程状态 查询当前流程实例的状态以及流程详细步骤的状态
     * @param processInstanceId 流程实例ID
     * @param corporateIdentify 企业唯一标识
     * @return
     * @throws BussinessException
     */
    public ProcessStatusQueryResp queryProcessStatus(Long processInstanceId,Long corporateIdentify) throws BussinessException{

        ProcessInstance processInstance = processInstanceRepository.findOne(processInstanceId);

        if(CheckParam.isNull(processInstance)){
            return null;
            //throw new BussinessException(ErrorCode.PROCESS_INSTANCE_NOT_EXIST_ERROR.getCode(),ErrorCode.PROCESS_INSTANCE_NOT_EXIST_ERROR.getMessage());
        }

        ProcessStatusQueryResp processStatusQueryResp = new ProcessStatusQueryResp();

        processStatusQueryResp.setId(processInstance.getId());
        processStatusQueryResp.setProcessInstanceStatus(processInstance.getProcessStatus()); //设置流程实例状态

        //查询流程详情相关信息
        DeviceProcessDetail deviceProcessDetail = deviceProcessDetailRepository.findByProcessIdAndProcessStepAndCorporateIdentify(processInstance.getProcessId(),
                processInstance.getCurrentStep(),corporateIdentify);

        //查询流程运行时信息，设置状态
        if(!CheckParam.isNull(deviceProcessDetail)){
            ProcessRuntimeInfo processRuntimeInfo = processRuntimeInfoRepository.findByProcessDetailIdAndProcessInstanceIdAndCorporateIdentify(deviceProcessDetail.getId(),
                    processInstance.getId(), corporateIdentify);
            if(!CheckParam.isNull(deviceProcessDetail)){
                processStatusQueryResp.setProcessDetailStatus(processRuntimeInfo.getProcessRuntimeStatus());

            }
        }

        return processStatusQueryResp;
    }

    /**
     * 开启流程
     * 生成流程实例 返回状态为开启的流程实例
     * @param processType 流程类型
     * @param processStage 流程阶段
     * @return
     */
    public CreateProcessInstanceResp createProcessInstance(Long processType,Long processStage,Long corporateIdentify) throws BussinessException{
        DeviceProcess deviceProcess = deviceProcessRepository.findByProcessTypeAndProcessStageAndCorporateIdentify(processType,
                processStage, corporateIdentify);

        if(CheckParam.isNull(deviceProcess)){
            throw new BussinessException(ErrorCode.PROCESS_NOT_EXIST_ERROR.getCode(),ErrorCode.PROCESS_NOT_EXIST_ERROR.getMessage());
        }

        ProcessInstance processInstance = new ProcessInstance();

        processInstance.setCurrentStep(null); //设置当前步骤为空，代表流程实例刚刚开启
        processInstance.setProcessId(deviceProcess.getId());
        processInstance.setProcessStatus(1); //初始化流程实例代表刚刚开启
        processInstance.setCorporateIdentify(corporateIdentify);

        processInstanceRepository.save(processInstance);

        //返回流程id给调用者
        CreateProcessInstanceResp createProcessInstanceResp = new CreateProcessInstanceResp();
        createProcessInstanceResp.setId(processInstance.getId());

        return createProcessInstanceResp;
    }




    /**
     * 新增流程
     * @param deviceProcessAddReq
     * @param corporateIdentify
     * @return
     */
    public DeviceProcessAddResp addDeviceProcess(DeviceProcessAddReq deviceProcessAddReq,Long corporateIdentify){

        DeviceProcess deviceProcess = deviceProcessRepository.findByProcessNameAndCorporateIdentify(deviceProcessAddReq.getProcessName(), corporateIdentify);


        if(!CheckParam.isNull(deviceProcess)){
            throw new BussinessException(ErrorCode.PROCESS_NAME_REPET_ERROR.getCode(),ErrorCode.PROCESS_NAME_REPET_ERROR.getMessage());
        }

        deviceProcess =  new DeviceProcess();

        deviceProcess.setProcessName(deviceProcessAddReq.getProcessName());
        deviceProcess.setProcessRemark(deviceProcessAddReq.getProcessRemark());
        deviceProcess.setProcessType(deviceProcessAddReq.getProcessType());
        deviceProcess.setTriggerCondition(deviceProcessAddReq.getTriggerCondition());
        deviceProcess.setTriggerConditionType(deviceProcessAddReq.getTriggerConditionType());
        deviceProcess.setProcessStage(deviceProcessAddReq.getProcessStage());
        deviceProcess.setCorporateIdentify(corporateIdentify);

        deviceProcessRepository.save(deviceProcess);

        DeviceProcessAddResp deviceProcessAddResp = new DeviceProcessAddResp();

        deviceProcessAddResp.setId(deviceProcess.getId());
        deviceProcessAddResp.setProcessName(deviceProcess.getProcessName());

        return deviceProcessAddResp;
    }

}
