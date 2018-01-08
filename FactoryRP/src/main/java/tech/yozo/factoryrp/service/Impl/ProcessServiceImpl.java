package tech.yozo.factoryrp.service.Impl;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.DeviceProcess;
import tech.yozo.factoryrp.entity.DeviceProcessDetail;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.repository.DeviceProcessDetailRepository;
import tech.yozo.factoryrp.repository.DeviceProcessRepository;
import tech.yozo.factoryrp.service.ProcessService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.DeviceProcessAddReq;
import tech.yozo.factoryrp.vo.resp.process.CreateProcessInstanceResp;
import tech.yozo.factoryrp.vo.resp.process.DeviceProcessAddResp;
import tech.yozo.factoryrp.vo.resp.process.ProcessStatusQueryResp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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


    public static void main(String[] args) {
        List<Long> ids = new ArrayList<>();

        ids.add(1L);
        ids.add(2L);

        String string = JSON.toJSONString(ids);

        List<Long> list = JSON.parseObject(string,List.class);
        System.out.println(JSON.toJSONString(ids));
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


        if(!CheckParam.isNull(deviceProcessAddReq.getList()) && !deviceProcessAddReq.getList().isEmpty()){
            List<DeviceProcessDetail> deviceProcessDetailList = new ArrayList<>();

            Long processId = deviceProcess.getId();

            deviceProcessAddReq.getList().stream().forEach(d1 -> {
                DeviceProcessDetail deviceProcessDetail = new DeviceProcessDetail();

                deviceProcessDetail.setProcessAuditor(JSON.toJSONString(d1.getProcessAuditor()));
                deviceProcessDetail.setAuditType(d1.getAuditType());
                deviceProcessDetail.setHandleDemandType(d1.getHandleDemandType());
                deviceProcessDetail.setProcessStep(d1.getProcessStep());
                deviceProcessDetail.setCorporateIdentify(corporateIdentify);
                deviceProcessDetail.setProcessId(processId);

                deviceProcessDetailList.add(deviceProcessDetail);
            });
            deviceProcessDetailRepository.save(deviceProcessDetailList);
        }


        DeviceProcessAddResp deviceProcessAddResp = new DeviceProcessAddResp();

        deviceProcessAddResp.setId(deviceProcess.getId());
        deviceProcessAddResp.setProcessName(deviceProcess.getProcessName());

        return deviceProcessAddResp;
    }

}
