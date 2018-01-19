package tech.yozo.factoryrp.service.Impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.*;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.repository.DeviceProcessDetailRepository;
import tech.yozo.factoryrp.repository.DeviceProcessRepository;
import tech.yozo.factoryrp.repository.DeviceProcessTypeRepository;
import tech.yozo.factoryrp.repository.UserRepository;
import tech.yozo.factoryrp.service.ProcessService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.DeviceProcessAddReq;
import tech.yozo.factoryrp.vo.req.DeviceProcessQueryReq;
import tech.yozo.factoryrp.vo.resp.process.*;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.function.Function;
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

    @Resource
    private UserRepository userRepository;

    private static Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);


    /**
     * 流程部署查询
     * 业务逻辑: 不给步数就返回第一步和返回下一步的ID
     *          没有下一步ID就是空
     * @param processType 流程类型
     * @param processStage 流程状态
     * @param processStep 流程步数 可为空
     * @param corporateIdentify 企业唯一标识
     * @return
     */
    public DeviceProcessStepQueryResp processStepQuery(Long processType, Long processStage,Integer processStep, Long corporateIdentify){

        //先查询流程表的数据
        DeviceProcess deviceProcess = deviceProcessRepository.findByProcessTypeAndProcessStageAndCorporateIdentify(processType,
                processStage, corporateIdentify);

        if(!CheckParam.isNull(deviceProcess)){

            //不给步数就返回第一步和返回下一步的ID
            if(CheckParam.isNull(processStep)){

                //查询最小流程步数ID,此处必定是第一步，且值在业务上来说不可能为空
                DeviceProcessDetail minProcessDetail = deviceProcessDetailRepository.findMinProcessStepByProcessIdAndAndCorporateIdentify(deviceProcess.getId(), corporateIdentify);

                //下一步流程的步骤
                Integer nextProcessStep = minProcessDetail.getProcessStep() + 1;

                DeviceProcessDetail nextPeocessDetail = deviceProcessDetailRepository.findByProcessIdAndAndCorporateIdentifyAndProcessStep(deviceProcess.getId(), corporateIdentify, nextProcessStep);

                DeviceProcessStepQueryResp deviceProcessStepQueryResp = new DeviceProcessStepQueryResp();

                deviceProcessStepQueryResp.setCurrentProcessStep(minProcessDetail.getId());
                deviceProcessStepQueryResp.setNextProcessStep(CheckParam.isNull(nextPeocessDetail) ? null : nextPeocessDetail.getId());

                return deviceProcessStepQueryResp;
            }else{

                DeviceProcessDetail currentProcessStep = deviceProcessDetailRepository.findByProcessIdAndAndCorporateIdentifyAndProcessStep(deviceProcess.getId(), corporateIdentify, processStep);

                if(CheckParam.isNull(currentProcessStep)){
                    return null;
                }

                //下一步流程的步骤
                Integer nextProcessStep = currentProcessStep.getProcessStep();

                DeviceProcessDetail nextProcessDetail = deviceProcessDetailRepository.findByProcessIdAndAndCorporateIdentifyAndProcessStep(deviceProcess.getId(), corporateIdentify, nextProcessStep);

                DeviceProcessStepQueryResp deviceProcessStepQueryResp = new DeviceProcessStepQueryResp();

                deviceProcessStepQueryResp.setCurrentProcessStep(currentProcessStep.getId());
                deviceProcessStepQueryResp.setNextProcessStep(nextProcessDetail.getId());

                return deviceProcessStepQueryResp;
            }

        }

        return null;
    }



    /**
     * 查询流程详情-->前端画图所需要的数据
     * @param processId
     * @param corporateIdentify
     * @return
     */
    public DeviceProcessDetailQueryWarpResp queryProcessDetail(Long processId,Long corporateIdentify){


        DeviceProcess deviceProcess = deviceProcessRepository.findOne(processId);

        if(!CheckParam.isNull(deviceProcess)){

            DeviceProcessDetailQueryWarpResp deviceProcessDetailQueryWarpResp = new DeviceProcessDetailQueryWarpResp();

            List<DeviceProcessDetail> deviceProcessDetailList = deviceProcessDetailRepository.findByProcessIdAndAndCorporateIdentify(processId, corporateIdentify);

            List<DeciceProcessDetailQueryResp> processDetailList = new ArrayList<>();

            List<Long> userIds = new ArrayList<>();

            deviceProcessDetailList.stream().forEach(d1 -> {
                DeciceProcessDetailQueryResp deciceProcessDetailQueryResp = new DeciceProcessDetailQueryResp();

                deciceProcessDetailQueryResp.setAuditType(d1.getAuditType());
                deciceProcessDetailQueryResp.setHandleDemandType(d1.getHandleDemandType());

                List<Long> auditorList = JSON.parseArray(d1.getProcessAuditor(), Long.class);

                //设置审核人的名称所用
                userIds.addAll(auditorList);

                deciceProcessDetailQueryResp.setProcessAuditorList(JSON.parseArray(d1.getProcessAuditor(), String.class));
                deciceProcessDetailQueryResp.setProcessId(d1.getProcessId());
                deciceProcessDetailQueryResp.setProcessStep(d1.getProcessStep());

                processDetailList.add(deciceProcessDetailQueryResp);
            });

            //去重操作，真正用来查询的用户集合
            List<Long> userIdList = userIds.stream().distinct().collect(Collectors.toList());

            List<User> userList = userRepository.findByCorporateIdentifyAndUserIdIn(corporateIdentify, userIdList);

            //查询出来的用户集合变成可以用来定位数据的Map
            Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getUserId, Function.identity()));

            processDetailList.stream().forEach(d1 -> {
                if(null != d1.getProcessAuditorList() && !d1.getProcessAuditorList().isEmpty()){
                    List<String> userNameList = new ArrayList<>();
                    d1.getProcessAuditorList().stream().forEach(p1 -> {
                        if(!CheckParam.isNull(userMap.get(Long.valueOf(p1)))){
                            userNameList.add(userMap.get(Long.valueOf(p1)).getUserName());
                        }
                    });
                    d1.setProcessAuditorList(userNameList);
                }

            });

            deviceProcessDetailQueryWarpResp.setProcessDetailList(processDetailList);

            return deviceProcessDetailQueryWarpResp;
        }

        return null;
    }

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


    /**
     * 分步查询流程详细信息
     * @param processType  流程类型
     * @param processStage 流程状态
     * @param corporateIdentify 企业唯一标识
     * @param processStep 步数ID
     * @return
     */
    public  DeviceProcessDetailWarpResp queryProcessAduitInfoByStep(Long processType, Long processStage,Integer processStep, Long corporateIdentify){

        DeviceProcess deviceProcess = deviceProcessRepository.findByProcessTypeAndProcessStageAndCorporateIdentify(processType,
                processStage, corporateIdentify);

        if(!CheckParam.isNull(deviceProcess)){

            //流程详情内容，此处只可能返回一个，因为是根据步骤查询的
            DeviceProcessDetail deviceProcessDetail = deviceProcessDetailRepository.findByProcessIdAndAndCorporateIdentifyAndProcessStep(deviceProcess.getId(), corporateIdentify,processStep);

                if(!CheckParam.isNull(deviceProcessDetail)){
                    List<Long> userIdList =  new ArrayList<>();

                    DeviceProcessDetailWarpResp  deviceProcessDetailWarpResp = new DeviceProcessDetailWarpResp();

                    deviceProcessDetailWarpResp.setAuditType(deviceProcessDetail.getAuditType());
                    deviceProcessDetailWarpResp.setHandleDemandType(deviceProcessDetail.getHandleDemandType());

                    String processAuditor = deviceProcessDetail.getProcessAuditor(); //处理处理人的逻辑
                    List<Long> userIds = JSON.parseObject(processAuditor, List.class);

                    List<DeviceProcessHandlerResp> deviceProcessHandlerRespList = new ArrayList<>();

                    //格式化用户ID
                    userIds.stream().forEach(u1 -> {
                        DeviceProcessHandlerResp deviceProcessHandlerResp = new DeviceProcessHandlerResp();
                        deviceProcessHandlerResp.setUserId(u1);

                        //添加进处理人集合
                        deviceProcessHandlerRespList.add(deviceProcessHandlerResp);

                        userIdList.addAll(userIds);
                    });

                    deviceProcessDetailWarpResp.setHandlerList(deviceProcessHandlerRespList);
                    deviceProcessDetailWarpResp.setProcessStep(deviceProcessDetail.getProcessStep());

                    if(null != userIdList && !userIdList.isEmpty()){
                        List<User> userList = userRepository.findByCorporateIdentifyAndUserIdIn(corporateIdentify, userIdList);

                        //形成键为userId，值为User对象的Map结构，方便接下来定位数据
                        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getUserId, Function.identity()));

                        //处理流程处理人姓名
                        deviceProcessDetailWarpResp.getHandlerList().stream().forEach(h1 -> {
                            h1.setName(String.valueOf(userMap.get(h1.getUserId())));
                        });

                    }

                    return deviceProcessDetailWarpResp;
                }

        }

        return null;
    }

    /**
     * 查询流程详细信息
     * @param processType
     * @param processStage
     * @param corporateIdentify
     * @return
     */
    public List<DeviceProcessDetailWarpResp> queryProcessAduitInfo(Long processType, Long processStage, Long corporateIdentify){

        DeviceProcess deviceProcess = deviceProcessRepository.findByProcessTypeAndProcessStageAndCorporateIdentify(processType,
                processStage, corporateIdentify);

        if(!CheckParam.isNull(deviceProcess)){

            List<DeviceProcessDetailWarpResp> warpRespList =  new ArrayList<>();

            List<DeviceProcessDetail> processDetailList = deviceProcessDetailRepository.findByProcessIdAndAndCorporateIdentify(deviceProcess.getId(), corporateIdentify);

            List<Long> userIdList =  new ArrayList<>();

            processDetailList.stream().forEach(p1 ->{

                DeviceProcessDetailWarpResp  deviceProcessDetailWarpResp = new DeviceProcessDetailWarpResp();

                deviceProcessDetailWarpResp.setAuditType(p1.getAuditType());
                deviceProcessDetailWarpResp.setHandleDemandType(p1.getHandleDemandType());

                String processAuditor = p1.getProcessAuditor(); //处理处理人的逻辑
                List<Long> userIds = JSON.parseObject(processAuditor, List.class);

                List<DeviceProcessHandlerResp> deviceProcessHandlerRespList = new ArrayList<>();

                userIds.stream().forEach(u1 -> {
                    DeviceProcessHandlerResp deviceProcessHandlerResp = new DeviceProcessHandlerResp();
                    deviceProcessHandlerResp.setUserId(u1);
                    deviceProcessHandlerRespList.add(deviceProcessHandlerResp);
                });

                deviceProcessDetailWarpResp.setHandlerList(deviceProcessHandlerRespList);

                deviceProcessDetailWarpResp.setProcessStep(p1.getProcessStep());

                userIdList.addAll(userIds);

                warpRespList.add(deviceProcessDetailWarpResp);

            });

            if(null != userIdList && !userIdList.isEmpty()){
                List<User> userList = userRepository.findByCorporateIdentifyAndUserIdIn(corporateIdentify, userIdList);

                Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getUserId, Function.identity()));

                warpRespList.stream().forEach(p1 -> {
                    p1.getHandlerList().stream().forEach(h1 -> {
                        h1.setName(String.valueOf(userMap.get(h1.getUserId())));
                    });
                });

            }

            return warpRespList;
        }

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
