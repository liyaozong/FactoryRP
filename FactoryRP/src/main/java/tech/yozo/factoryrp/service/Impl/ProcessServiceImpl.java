package tech.yozo.factoryrp.service.Impl;

import com.alibaba.fastjson.JSON;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.DeviceProcess;
import tech.yozo.factoryrp.entity.DeviceProcessDetail;
import tech.yozo.factoryrp.entity.DeviceProcessType;
import tech.yozo.factoryrp.entity.SpareParts;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.repository.DeviceProcessDetailRepository;
import tech.yozo.factoryrp.repository.DeviceProcessRepository;
import tech.yozo.factoryrp.repository.DeviceProcessTypeRepository;
import tech.yozo.factoryrp.service.ProcessService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.DeviceProcessAddReq;
import tech.yozo.factoryrp.vo.req.DeviceProcessQueryReq;
import tech.yozo.factoryrp.vo.resp.process.CreateProcessInstanceResp;
import tech.yozo.factoryrp.vo.resp.process.DeviceProcessAddResp;
import tech.yozo.factoryrp.vo.resp.process.ProcessStatusQueryResp;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
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
    private DeviceProcessTypeRepository deviceProcessTypeRepository;

    /**
     * 查询所有流程类型集合
     * @param corporateIdentify
     * @return
     */
    public List<DeviceProcessType> queryAllDecviceProcessType(Long corporateIdentify){

        List<DeviceProcessType> deviceProcessTypeList = deviceProcessTypeRepository.findByCorporateIdentify(corporateIdentify);

        if(!CheckParam.isNull(deviceProcessTypeList) && !deviceProcessTypeList.isEmpty()){
            deviceProcessTypeList.stream().sorted((d1,d2) -> d1.getOrderNumber().compareTo(d2.getOrderNumber()));
        }


        return deviceProcessTypeRepository.findByCorporateIdentify(corporateIdentify);
    }



    public DeviceProcess queryProcessAduitInfo(Long processType,Long processStage,Long corporateIdentify){


        return null;
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


    /**
     * 流程分页查询
     * @param deviceProcessQueryReq
     * @param corporateIdentify
     * @return
     */
    @Override
    public Pagination<DeviceProcess> findByPage(DeviceProcessQueryReq deviceProcessQueryReq,Long corporateIdentify) {

        if (deviceProcessQueryReq.getCurrentPage() > 0) {
            deviceProcessQueryReq.setCurrentPage(deviceProcessQueryReq.getCurrentPage()-1);
        }
        Pageable p = new PageRequest(deviceProcessQueryReq.getCurrentPage(), deviceProcessQueryReq.getItemsPerPage());

        Page<DeviceProcess> page = deviceProcessRepository.findAll((Root<DeviceProcess> root,
                                                                   CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> list = new ArrayList<>();

            if (!CheckParam.isNull(deviceProcessQueryReq.getId())) { //主键
                list.add(criteriaBuilder.equal(root.get("id").as(Long.class), deviceProcessQueryReq.getId()));
            }
            if (!CheckParam.isNull(deviceProcessQueryReq.getProcessName())) { //流程名称
                list.add(criteriaBuilder.like(root.get("processName").as(String.class), "%" + deviceProcessQueryReq.getProcessName() + "%"));
            }
            if (!CheckParam.isNull(deviceProcessQueryReq.getProcessType())) { //流程类型
                list.add(criteriaBuilder.equal(root.get("processType").as(Long.class), deviceProcessQueryReq.getProcessType()));
            }
            if (!CheckParam.isNull(deviceProcessQueryReq.getProcessStage())) { //流程阶段
                list.add(criteriaBuilder.equal(root.get("processStage").as(Long.class), deviceProcessQueryReq.getProcessStage()));
            }
            if (!CheckParam.isNull(deviceProcessQueryReq.getTriggerConditionType())) { //触发条件类型
                list.add(criteriaBuilder.equal(root.get("triggerConditionType").as(Long.class), deviceProcessQueryReq.getTriggerConditionType()));
            }
            if (!CheckParam.isNull(deviceProcessQueryReq.getTriggerCondition())) { //触发条件详情
                list.add(criteriaBuilder.equal(root.get("triggerCondition").as(Long.class), deviceProcessQueryReq.getTriggerCondition()));
            }
            if (!CheckParam.isNull(deviceProcessQueryReq.getProcessRemark())) { //流程阶段
                list.add(criteriaBuilder.like(root.get("processRemark").as(String.class), "%" + deviceProcessQueryReq.getProcessRemark() + "%"));
            }
            list.add(criteriaBuilder.equal(root.get("corporateIdentify").as(Long.class), corporateIdentify));

            Predicate[] predicates = new Predicate[list.size()];
            predicates = list.toArray(predicates);
            return criteriaBuilder.and(predicates);
        }, p);

        Pagination<DeviceProcess> res = new Pagination<>();
        if (page.hasContent()){
            res.setList(page.getContent());
        }
        res.setCurrentPage(deviceProcessQueryReq.getCurrentPage());
        res.setItemsPerPage(deviceProcessQueryReq.getItemsPerPage());
        res.setTotalCount(Integer.valueOf(String.valueOf(page.getTotalElements())));

        return res;
    }

}
