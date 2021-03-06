package tech.yozo.factoryrp.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.*;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.repository.*;
import tech.yozo.factoryrp.service.ProcessService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.DeviceProcessAddReq;
import tech.yozo.factoryrp.vo.req.DeviceProcessEditReq;
import tech.yozo.factoryrp.vo.req.DeviceProcessQueryReq;
import tech.yozo.factoryrp.vo.resp.process.*;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 流程相关服务
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
@Service
@Transactional
public class ProcessServiceImpl implements ProcessService {

    @Resource
    private DeviceProcessRepository deviceProcessRepository;

    @Resource
    private DeviceProcessDetailRepository deviceProcessDetailRepository;

    @Resource
    private DeviceProcessTypeRepository deviceProcessTypeRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private DeviceProcessPhaseRepository deviceProcessPhaseRepository;

    private static Logger logger = LoggerFactory.getLogger(ProcessServiceImpl.class);


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
    public DeviceProcessStepQueryResp processStepQuery(String processType, String processStage,Integer processStep, Long corporateIdentify){

        //先查询流程表的数据
        DeviceProcess deviceProcess = deviceProcessRepository.findByProcessTypeAndProcessStageAndCorporateIdentify(processType,
                processStage, corporateIdentify);

        if(!CheckParam.isNull(deviceProcess)){

            //不给步数就返回第一步和返回下一步的ID
            if(CheckParam.isNull(processStep)){

                List<DeviceProcessDetail> deviceProcessDetailList = deviceProcessDetailRepository.findByProcessIdAndCorporateIdentify(deviceProcess.getId(), corporateIdentify);


                //算出流程最小值,业务上来说不可能为空
                DeviceProcessDetail minProcessDetail = deviceProcessDetailList.stream().collect(Collectors.minBy((d1, d2) -> d1.getProcessStep() - d2.getProcessStep())).get();

                //下一步流程的步骤
                Integer nextProcessStep = minProcessDetail.getProcessStep() + 1;

                DeviceProcessDetail nextPeocessDetail = deviceProcessDetailList.stream().filter(d1 -> d1.getProcessStep() == nextProcessStep).collect(Collectors.minBy((d1, d2) -> d1.getProcessStep() - d2.getProcessStep())).get();

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
                Integer nextProcessStep = currentProcessStep.getProcessStep() + 1;

                DeviceProcessDetail nextProcessDetail = deviceProcessDetailRepository.findByProcessIdAndAndCorporateIdentifyAndProcessStep(deviceProcess.getId(), corporateIdentify, nextProcessStep);

                DeviceProcessStepQueryResp deviceProcessStepQueryResp = new DeviceProcessStepQueryResp();

                deviceProcessStepQueryResp.setCurrentProcessStep(currentProcessStep.getId());
                deviceProcessStepQueryResp.setNextProcessStep(CheckParam.isNull(nextProcessDetail) ? null : nextProcessDetail.getId());

                logger.info(JSON.toJSONString(deviceProcessStepQueryResp));
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

            List<DeviceProcessDetail> deviceProcessDetailList = deviceProcessDetailRepository.findByProcessIdAndCorporateIdentify(processId, corporateIdentify);

            List<DeviceProcessDetailQueryResp> processDetailList = new ArrayList<>();

            List<Long> userIds = new ArrayList<>();

            deviceProcessDetailList.stream().forEach(d1 -> {
                DeviceProcessDetailQueryResp deviceProcessDetailQueryResp = new DeviceProcessDetailQueryResp();

                deviceProcessDetailQueryResp.setAuditType(d1.getAuditType());
                deviceProcessDetailQueryResp.setHandleDemandType(d1.getHandleDemandType());

                List<Long> auditorList = JSON.parseArray(d1.getProcessAuditor(), Long.class);

                //设置审核人的名称所用
                userIds.addAll(auditorList);

                deviceProcessDetailQueryResp.setProcessAuditorList(JSON.parseArray(d1.getProcessAuditor(), String.class));
                deviceProcessDetailQueryResp.setProcessId(d1.getProcessId());
                deviceProcessDetailQueryResp.setProcessStep(d1.getProcessStep());

                processDetailList.add(deviceProcessDetailQueryResp);
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
    public List<DeviceProcessType> queryAllDeviceProcessType(Long corporateIdentify){

        List<DeviceProcessType> deviceProcessTypeList = deviceProcessTypeRepository.findByCorporateIdentify(corporateIdentify);

        if(!CheckParam.isNull(deviceProcessTypeList) && !deviceProcessTypeList.isEmpty()){
            deviceProcessTypeList.stream().sorted(Comparator.comparing(DeviceProcessType::getOrderNumber));
        }

        return deviceProcessTypeRepository.findByCorporateIdentify(corporateIdentify);
    }


    /**
     * 根据流程详情ID查询流程审核人员数据
     * @param processStepId
     * @return 流程详情ID
     */
    public  DeviceProcessDetailWarpResp queryProcessAduitInfoByStep(Long processStepId){


        DeviceProcessDetail processDetail = deviceProcessDetailRepository.findOne(processStepId);

        if(!CheckParam.isNull(processDetail)){
            {
                List<Long> userIdList =  new ArrayList<>();

                DeviceProcessDetailWarpResp  deviceProcessDetailWarpResp = new DeviceProcessDetailWarpResp();

                deviceProcessDetailWarpResp.setAuditType(processDetail.getAuditType());
                deviceProcessDetailWarpResp.setHandleDemandType(processDetail.getHandleDemandType());

                String processAuditor = processDetail.getProcessAuditor(); //处理处理人的逻辑
                List<Long> userIds = JSON.parseArray(processAuditor, Long.class);

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
                deviceProcessDetailWarpResp.setProcessStep(processDetail.getProcessStep());

                if(null != userIdList && !userIdList.isEmpty()){
                    List<User> userList = userRepository.findByCorporateIdentifyAndUserIdIn(processDetail.getCorporateIdentify(), userIdList);

                    //形成键为userId，值为User对象的Map结构，方便接下来定位数据
                    Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getUserId, Function.identity()));

                    //处理流程处理人姓名
                    deviceProcessDetailWarpResp.getHandlerList().stream().forEach(h1 -> {
                        if(!CheckParam.isNull(userMap.get(h1.getUserId()))) {
                            h1.setName(userMap.get(h1.getUserId()).getUserName());
                        }
                    });

                }
                return deviceProcessDetailWarpResp;
            }

        }

        return null;
    }

    /**
     * 分步查询流程审核人员详细信息
     * @param processType  流程类型
     * @param processStage 流程状态
     * @param corporateIdentify 企业唯一标识
     * @param processStep 步数ID
     * @return
     */
    public  DeviceProcessDetailWarpResp queryProcessAduitInfoByStep(String processType, String processStage,Integer processStep, Long corporateIdentify){

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
                    List<Long> userIds = JSON.parseArray(processAuditor, Long.class);

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
                            if(!CheckParam.isNull(userMap.get(h1.getUserId()))){
                                h1.setName(userMap.get(h1.getUserId()).getUserName());
                            }
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
    public List<DeviceProcessDetailWarpResp> queryProcessAduitInfo(String processType, String processStage,Long triggerConditionType,Long triggerCondition, Long corporateIdentify){

        DeviceProcess deviceProcess = deviceProcessRepository.findByProcessTypeAndProcessStageAndTriggerConditionTypeAndTriggerConditionAndCorporateIdentify(processType,
                processStage,triggerConditionType,triggerCondition,corporateIdentify);

        if(!CheckParam.isNull(deviceProcess)){

            List<DeviceProcessDetailWarpResp> warpRespList =  new ArrayList<>();

            List<DeviceProcessDetail> processDetailList = deviceProcessDetailRepository.findByProcessIdAndCorporateIdentify(deviceProcess.getId(), corporateIdentify);

            List<Long> userIdList =  new ArrayList<>();

            processDetailList.stream().forEach(p1 ->{

                DeviceProcessDetailWarpResp  deviceProcessDetailWarpResp = new DeviceProcessDetailWarpResp();

                deviceProcessDetailWarpResp.setAuditType(p1.getAuditType());
                deviceProcessDetailWarpResp.setHandleDemandType(p1.getHandleDemandType());

                String processAuditor = p1.getProcessAuditor(); //处理处理人的逻辑
                List<Long> userIds = JSON.parseObject(processAuditor, new TypeReference<List<Long>>(){});

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
                        if (null!=userMap.get(h1.getUserId())){
                            h1.setName(userMap.get(h1.getUserId()).getUserName());
                        }
                    });
                });

            }

            return warpRespList;
        }

        return null;
    }


    /**
     * 编辑设备流程详情
     * @param deviceProcessEditReq
     * @param corporateIdentify
     */
    public void editDeviceProcess(DeviceProcessEditReq deviceProcessEditReq, Long corporateIdentify){


        DeviceProcessType deviceProcessType = deviceProcessTypeRepository.findByCodeAndCorporateIdentify(deviceProcessEditReq.getProcessType(), corporateIdentify);

        if(CheckParam.isNull(deviceProcessType) || CheckParam.isNull(deviceProcessType)){
            throw new BussinessException(ErrorCode.PROCESS_DIC_NOTEXIST_ERROR.getCode(),ErrorCode.PROCESS_DIC_NOTEXIST_ERROR.getMessage());

        }

        DeviceProcessPhase deviceProcessPhase = deviceProcessType.getDeviceProcessPhaseList().stream().filter(d1 -> d1.getCode().equals(deviceProcessEditReq.getProcessStage())).findFirst().get();

        //DeviceProcessPhase deviceProcessPhase = deviceProcessPhaseRepository.findByCodeAndCorporateIdentify(deviceProcessEditReq.getProcessStage(), corporateIdentify);


        DeviceProcess deviceProcess = deviceProcessRepository.findByProcessTypeAndProcessStageAndCorporateIdentifyAndProcessName(deviceProcessType.getCode(),
                deviceProcessType.getCode(),corporateIdentify,deviceProcessEditReq.getProcessName());

        if(CheckParam.isNull(deviceProcess)){
            throw new BussinessException(ErrorCode.PROCESS_NOT_EXIST_ERROR.getCode(),ErrorCode.PROCESS_NOT_EXIST_ERROR.getMessage());
        }

        if(!CheckParam.isNull(deviceProcessEditReq.getProcessName())){
            deviceProcess.setProcessName(deviceProcessEditReq.getProcessName());
        }
        if(!CheckParam.isNull(deviceProcessEditReq.getProcessRemark())){
            deviceProcess.setProcessRemark(deviceProcessEditReq.getProcessRemark());
        }
        if(!CheckParam.isNull(deviceProcessEditReq.getTriggerCondition())){
            deviceProcess.setTriggerCondition(deviceProcessEditReq.getTriggerCondition());
        }
        if(!CheckParam.isNull(deviceProcessEditReq.getTriggerConditionType())){
            deviceProcess.setTriggerConditionType(deviceProcessEditReq.getTriggerConditionType());
        }

        deviceProcess.setCorporateIdentify(corporateIdentify);
        deviceProcess.setProcessType(CheckParam.isNull(deviceProcessType) ? null : deviceProcessType.getCode());
        deviceProcess.setProcessStage(CheckParam.isNull(deviceProcessPhase) ? null : deviceProcessPhase.getCode());

        deviceProcessRepository.save(deviceProcess);

        if(!CheckParam.isNull(deviceProcessEditReq.getList()) && !deviceProcessEditReq.getList().isEmpty()){
            List<Long> detailId = new ArrayList<>();

            deviceProcessEditReq.getList().stream().forEach(d1 -> {
                detailId.add(d1.getDetailId());
            });

            List<DeviceProcessDetail> editDetailList = deviceProcessDetailRepository.findByProcessIdInAndCorporateIdentify(detailId, corporateIdentify);

            editDetailList.stream().forEach(d1 -> {

                if(!CheckParam.isNull(d1.getAuditType())){
                    d1.setAuditType(d1.getAuditType());
                }

                if(!CheckParam.isNull(d1.getProcessAuditor())){
                    d1.setProcessAuditor(JSON.toJSONString(d1.getProcessAuditor()));
                }
                if(!CheckParam.isNull(d1.getHandleDemandType())){
                    d1.setHandleDemandType(d1.getHandleDemandType());
                }
                if(!CheckParam.isNull(d1.getProcessStep())){
                    d1.setProcessStep(d1.getProcessStep());
                }
                if(!CheckParam.isNull(d1.getAuditType())){
                    d1.setAuditType(d1.getAuditType());
                }

                d1.setCorporateIdentify(corporateIdentify);
            });
            deviceProcessDetailRepository.save(editDetailList);
        }

    }
    /**
     * 新增流程
     * @param deviceProcessAddReq
     * @param corporateIdentify
     * @return
     */
    public DeviceProcessAddResp addDeviceProcess(DeviceProcessAddReq deviceProcessAddReq,Long corporateIdentify){

        DeviceProcessType deviceProcessType = deviceProcessTypeRepository.findByCodeAndCorporateIdentify(deviceProcessAddReq.getProcessType(), corporateIdentify);

        if(CheckParam.isNull(deviceProcessType) || CheckParam.isNull(deviceProcessType)){
            throw new BussinessException(ErrorCode.PROCESS_DIC_NOTEXIST_ERROR.getCode(),ErrorCode.PROCESS_DIC_NOTEXIST_ERROR.getMessage());

        }

        DeviceProcessPhase deviceProcessPhase = deviceProcessType.getDeviceProcessPhaseList().stream().filter(d1 -> d1.getCode().equals(deviceProcessAddReq.getProcessStage())).findFirst().get();

        DeviceProcess deviceProcess = deviceProcessRepository.findByProcessTypeAndProcessStageAndCorporateIdentifyAndProcessName(deviceProcessType.getCode(),
                deviceProcessType.getCode(),corporateIdentify,deviceProcessAddReq.getProcessName());

        if(!CheckParam.isNull(deviceProcess)){
            throw new BussinessException(ErrorCode.PROCESS_NAME_REPET_ERROR.getCode(),ErrorCode.PROCESS_NAME_REPET_ERROR.getMessage());
        }

        deviceProcess =  new DeviceProcess();

        deviceProcess.setProcessName(deviceProcessAddReq.getProcessName());
        deviceProcess.setProcessRemark(deviceProcessAddReq.getProcessRemark());
        deviceProcess.setTriggerCondition(deviceProcessAddReq.getTriggerCondition());
        deviceProcess.setTriggerConditionType(deviceProcessAddReq.getTriggerConditionType());
        deviceProcess.setCorporateIdentify(corporateIdentify);

        deviceProcess.setProcessType(CheckParam.isNull(deviceProcessType) ? null : deviceProcessType.getCode());
        deviceProcess.setProcessStage(CheckParam.isNull(deviceProcessPhase) ? null : deviceProcessPhase.getCode());

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
                list.add(criteriaBuilder.equal(root.get("processType").as(String.class), deviceProcessQueryReq.getProcessType()));
            }
            if (!CheckParam.isNull(deviceProcessQueryReq.getProcessStage())) { //流程阶段
                list.add(criteriaBuilder.equal(root.get("processStage").as(String.class), deviceProcessQueryReq.getProcessStage()));
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

    /**
     * 删除设备流程
     * @param processId
     * @param corporateIdentify
     */
    public void deleteDeviceProcess(Long processId,Long corporateIdentify){

        DeviceProcess process = deviceProcessRepository.findOne(processId);

        if(CheckParam.isNull(process)){
            throw new BussinessException(ErrorCode.PROCESS_NOT_EXIST_ERROR.getCode(),ErrorCode.PROCESS_NOT_EXIST_ERROR.getMessage());
        }

        List<DeviceProcessDetail> detailList = deviceProcessDetailRepository.findByProcessIdAndCorporateIdentify(processId, corporateIdentify);

        deviceProcessDetailRepository.deleteInBatch(detailList);
        deviceProcessRepository.delete(process);

    }


}
