package tech.yozo.factoryrp.service.Impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.entity.*;
import tech.yozo.factoryrp.enums.inspection.SpotInspectionPlanRecycleTypeEnum;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.repository.*;
import tech.yozo.factoryrp.service.SpotInspectionPlanService;
import tech.yozo.factoryrp.utils.BaseUtil;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.DateTimeUtil;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.SpotInspectionPlanAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionPlanEditReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionPlanQueryReq;
import tech.yozo.factoryrp.vo.resp.inspection.*;
import tech.yozo.factoryrp.vo.resp.inspection.mobile.SpotInspectionPlanDeviceQueryResp;
import tech.yozo.factoryrp.vo.resp.inspection.mobile.SpotInspectionPlanResp;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 巡检计划相关服务
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/25
 * @description
 */
@Service
@Transactional
public class SpotInspectionPlanServiceImpl implements SpotInspectionPlanService {


    @Resource
    private SpotInspectionPlanRepository spotInspectionPlanRepository;

    @Resource
    private SpotInspectionPlanDeviceRepository spotInspectionPlanDeviceRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private DeviceInfoRepository deviceInfoRepository;

    @Resource
    private DepartmentRepository departmentRepository;

    @Resource
    private DeviceTypeRepository deviceTypeRepository;

    @Resource
    private SpotInspectionStandardRepository spotInspectionStandardRepository;

    @Resource
    private SpotInspectionRecordRepository spotInspectionRecordRepository;


    @Resource
    private SpotInspectionRecordDetailRepository spotInspectionRecordDetailRepository;

    private static Logger logger = LoggerFactory.getLogger(SpotInspectionPlanServiceImpl.class);


    /**
     * 查询巡检计划关联的设备
     * @param planId
     * @param corporateIdentify
     * @return
     */
    public List<SpotInspectionPlanDeviceQueryResp> querySpotInspectionPlanDevices(Long planId,Long corporateIdentify){

        List<SpotInspectionPlanDevice> inspectionPlanDeviceList = spotInspectionPlanDeviceRepository.findByCorporateIdentifyAndSpotInspectionPlan(corporateIdentify,planId);

        if(!CheckParam.isNull(inspectionPlanDeviceList) && !inspectionPlanDeviceList.isEmpty()){

            List<Long> deviceInfoIds = new ArrayList<>();

            Map<Long,Long> deviceIdStandardLMap = new HashMap<>();

            inspectionPlanDeviceList.stream().forEach(d1 -> {
                deviceInfoIds.add(d1.getDeviceId());
                deviceIdStandardLMap.put(d1.getDeviceId(),d1.getSpotInspectionStandard());
            });

            if(!CheckParam.isNull(deviceInfoIds) && !deviceInfoIds.isEmpty()) {
                List<DeviceInfo> deviceInfoList = deviceInfoRepository.findByIdsIn(deviceInfoIds);

                List<SpotInspectionPlanDeviceQueryResp> resultList = new ArrayList<>();

                deviceInfoList.stream().forEach(d1 -> {
                    SpotInspectionPlanDeviceQueryResp spotInspectionPlanDeviceQueryResp = new SpotInspectionPlanDeviceQueryResp();

                    spotInspectionPlanDeviceQueryResp.setId(d1.getId());
                    spotInspectionPlanDeviceQueryResp.setCode(d1.getCode());
                    spotInspectionPlanDeviceQueryResp.setDeviceTypeId(d1.getDeviceType());
                    spotInspectionPlanDeviceQueryResp.setInstallationAddress(d1.getInstallationAddress());
                    spotInspectionPlanDeviceQueryResp.setUseDept(d1.getUseDept());
                    spotInspectionPlanDeviceQueryResp.setName(d1.getName());
                    spotInspectionPlanDeviceQueryResp.setSpecification(d1.getSpecification());
                    spotInspectionPlanDeviceQueryResp.setDeviceStandard(deviceIdStandardLMap.get(d1.getId()));

                    resultList.add(spotInspectionPlanDeviceQueryResp);
                });


                return resultList;
            }


        }

        return null;
    }




    /**
     * 新增点检计划
     * @param spotInspectionPlanAddReq
     * @param corporateIdentify
     * @return
     */
    public SpotInspectionPlanAddResp addSpotInspectionPlan(SpotInspectionPlanAddReq spotInspectionPlanAddReq,Long corporateIdentify){


        SpotInspectionPlan plan = spotInspectionPlanRepository.findByNameAndCorporateIdentify(spotInspectionPlanAddReq.getName(), corporateIdentify);

        if(!CheckParam.isNull(plan)){
            throw new BussinessException(ErrorCode.SPOTINSPECTIONPLAN_REPET_ERROR.getCode(),
                    ErrorCode.SPOTINSPECTIONPLAN_REPET_ERROR.getMessage());
        }


        SpotInspectionPlan spotInspectionPlan = new SpotInspectionPlan();

        spotInspectionPlan.setName(spotInspectionPlanAddReq.getName());
        spotInspectionPlan.setDepartment(spotInspectionPlanAddReq.getDepartment());
        spotInspectionPlan.setExecutors(JSON.toJSONString(spotInspectionPlanAddReq.getExecutors()));

        try {
            spotInspectionPlan.setEndTime(BaseUtil.strToDate(spotInspectionPlanAddReq.getEndTime()));
            spotInspectionPlan.setNextExecuteTime(BaseUtil.strToDate(spotInspectionPlanAddReq.getNextExecuteTime()));
        } catch (ParseException e) {
            logger.error("日期字符串转换异常 :"+e.getMessage(),e);
        }

        spotInspectionPlan.setPlanStatus(spotInspectionPlanAddReq.getPlanStatus());
        spotInspectionPlan.setRecyclePeriod(spotInspectionPlanAddReq.getRecyclePeriod());
        spotInspectionPlan.setSpotInspectionRange(spotInspectionPlanAddReq.getRange());
        spotInspectionPlan.setRecyclePeriodType(spotInspectionPlanAddReq.getRecyclePeriodType());
        spotInspectionPlan.setCorporateIdentify(corporateIdentify);


        spotInspectionPlanRepository.save(spotInspectionPlan);


        //生成关联信息
        if(!CheckParam.isNull(spotInspectionPlanAddReq.getList()) && !spotInspectionPlanAddReq.getList().isEmpty()){

            List<SpotInspectionPlanDevice> spotInspectionPlanDeviceList = new ArrayList<>();

            spotInspectionPlanAddReq.getList().stream().forEach(d1 -> {
                SpotInspectionPlanDevice spotInspectionPlanDevice = new SpotInspectionPlanDevice();
                spotInspectionPlanDevice.setDeviceType(d1.getDeviceType());
                spotInspectionPlanDevice.setDeviceId(d1.getDeviceId());
                spotInspectionPlanDevice.setCorporateIdentify(corporateIdentify);
                spotInspectionPlanDevice.setSpotInspectionPlan(spotInspectionPlan.getId());
                spotInspectionPlanDevice.setLineOrder(d1.getLineOrder());
                spotInspectionPlanDevice.setSpotInspectionStandard(d1.getSpotInspectionStandard());
                spotInspectionPlanDevice.setDeviceId(d1.getDeviceId());
                spotInspectionPlanDevice.setDeviceType(d1.getDeviceType());

                spotInspectionPlanDeviceList.add(spotInspectionPlanDevice);
            });

            spotInspectionPlanDeviceRepository.save(spotInspectionPlanDeviceList);

        }

        SpotInspectionPlanAddResp spotInspectionPlanAddResp = new SpotInspectionPlanAddResp();

        spotInspectionPlanAddResp.setId(spotInspectionPlan.getId());
        spotInspectionPlanAddResp.setName(spotInspectionPlan.getName());

        return spotInspectionPlanAddResp;
    }


    /**
     * 手机端查询点检任务
     * @param userId
     * @param corporateIdentify
     * @return
     */
    @Override
    public List<SpotInspectionPlanResp> queryMobilePlan(Long userId, Long corporateIdentify) {


        List<SpotInspectionPlan> spotInspectionPlans = spotInspectionPlanRepository.findByCorporateIdentify(corporateIdentify);


        if(!CheckParam.isNull(spotInspectionPlans) && !spotInspectionPlans.isEmpty()){

            List<SpotInspectionPlanResp> planResultList = new ArrayList<>();

            spotInspectionPlans = spotInspectionPlans.stream().filter(s1 -> JSON.parseArray(s1.getExecutors(), Long.class).contains(userId)).collect(Collectors.toList());

            if(!CheckParam.isNull(spotInspectionPlans) && !spotInspectionPlans.isEmpty()){


                spotInspectionPlans.stream().forEach(s1 ->{

                    SpotInspectionPlanResp spotInspectionPlanResp = new SpotInspectionPlanResp();

                    spotInspectionPlanResp.setPanId(s1.getId());
                    spotInspectionPlanResp.setNextExecuteTime(DateTimeUtil.dateToStr(s1.getNextExecuteTime())); //设置任务开始时间
                    spotInspectionPlanResp.setPlanName(s1.getName());

                    planResultList.add(spotInspectionPlanResp);
                });

                return planResultList;

            }


        }

        return null;
    }


    /**
     * 根据部门ID查询点检计划
     * @param spotInspectionPlanQueryReq
     * @param corporateIdentify
     * @return
     */
    public SpotInspectionPlanQueryWarpResp findByPage(SpotInspectionPlanQueryReq spotInspectionPlanQueryReq, Long corporateIdentify){

        if (spotInspectionPlanQueryReq.getCurrentPage() > 0) {
            spotInspectionPlanQueryReq.setCurrentPage(spotInspectionPlanQueryReq.getCurrentPage()-1);
        }

        Pageable p = new PageRequest(spotInspectionPlanQueryReq.getCurrentPage(), spotInspectionPlanQueryReq.getItemsPerPage());

        Page<SpotInspectionPlan> page = spotInspectionPlanRepository.findAll((Root<SpotInspectionPlan> root,
                                                                                      CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> list = new ArrayList<>();

            if (!CheckParam.isNull(spotInspectionPlanQueryReq.getDepartmentId())) { //部门ID
                list.add(criteriaBuilder.equal(root.get("department").as(Long.class), spotInspectionPlanQueryReq.getDepartmentId()));
            }
            if (!CheckParam.isNull(spotInspectionPlanQueryReq.getNextExecuteTime())) { //下次执行时间
                list.add(criteriaBuilder.equal(root.get("nextExecuteTime").as(Date.class), spotInspectionPlanQueryReq.getNextExecuteTime()));
            }
            if (!CheckParam.isNull(spotInspectionPlanQueryReq.getRecyclePeriodType())) { //循环周期类型
                list.add(criteriaBuilder.equal(root.get("recyclePeriodType").as(String.class), spotInspectionPlanQueryReq.getRecyclePeriodType()));
            }

            list.add(criteriaBuilder.equal(root.get("corporateIdentify").as(Long.class), corporateIdentify));

            Predicate[] predicates = new Predicate[list.size()];
            predicates = list.toArray(predicates);
            return criteriaBuilder.and(predicates);
        }, p);

        if(page.hasContent()){

            SpotInspectionPlanQueryWarpResp spotInspectionPlanQueryWarpResp = new SpotInspectionPlanQueryWarpResp();

            //需要格式化数据 需要格式化部门，执行人员名称，以及设备数
            List<SpotInspectionPlan> spotInspectionPlanList = page.getContent();

            List<SpotInspectionPlanQueryResp> spotInspectionPlanQueryRespList = new ArrayList<>();

            List<Long> deptIds = new ArrayList<>();

            List<Long> planIds = new ArrayList<>();

            spotInspectionPlanList.stream().forEach(s1 -> {

                deptIds.add(s1.getDepartment()); //格式化部门名称之用
                planIds.add(s1.getId()); //格式化关联设备数量之用

            });

            List<Department> departmentList = departmentRepository.findByCorporateIdentifyAndIdIn(corporateIdentify, deptIds);

            List<SpotInspectionPlanDevice> devices = spotInspectionPlanDeviceRepository.findByCorporateIdentifyAndSpotInspectionPlanIn(corporateIdentify, planIds);

            Map<Long, Department> departmentMap = departmentList.stream().collect(Collectors.toMap(Department::getId, Function.identity()));
            Map<Long, List<SpotInspectionPlanDevice>> devicesMap =
                    devices.stream().collect(Collectors.groupingBy(SpotInspectionPlanDevice::getSpotInspectionPlan));

            spotInspectionPlanList.stream().forEach(s1 -> {

                SpotInspectionPlanQueryResp spotInspectionPlanQueryResp = new SpotInspectionPlanQueryResp();

                spotInspectionPlanQueryResp.setName(s1.getName());


                 //格式化部门设备数量
                if(!CheckParam.isNull(departmentMap) && !departmentMap.isEmpty() && !CheckParam.isNull(departmentMap.get(s1.getDepartment()))){
                    spotInspectionPlanQueryResp.setDepartmentName(departmentMap.get(s1.getDepartment()).getName());
                }
                if(!CheckParam.isNull(devicesMap) && !devicesMap.isEmpty() && !CheckParam.isNull(devicesMap.get(s1.getId()))){
                    spotInspectionPlanQueryResp.setDeviceCount(devicesMap.get(s1.getId()).size());
                }

                spotInspectionPlanQueryResp.setPlanStatus(s1.getPlanStatus());
                spotInspectionPlanQueryResp.setLastExecuteTime(DateTimeUtil.dateToStr(s1.getLastExecuteTime()));
                spotInspectionPlanQueryResp.setNextExecuteTime(DateTimeUtil.dateToStr(s1.getNextExecuteTime()));
                spotInspectionPlanQueryResp.setExecutors(JSON.parseArray(s1.getExecutors(),Long.class));
                spotInspectionPlanQueryResp.setRecyclePeriod(SpotInspectionPlanRecycleTypeEnum.handlerRecycleTimer(s1.getRecyclePeriod(),s1.getRecyclePeriodType()));
                spotInspectionPlanQueryResp.setId(s1.getId());

                spotInspectionPlanQueryRespList.add(spotInspectionPlanQueryResp);
            });


            spotInspectionPlanQueryWarpResp.setSpotInspectionPlanQueryRespList(spotInspectionPlanQueryRespList);
            spotInspectionPlanQueryWarpResp.setTotalCount(page.getTotalElements());
            spotInspectionPlanQueryWarpResp.setCurrentPage(spotInspectionPlanQueryReq.getCurrentPage());
            spotInspectionPlanQueryWarpResp.setItemsPerPage(spotInspectionPlanQueryReq.getItemsPerPage());

            return spotInspectionPlanQueryWarpResp;
        }

        return null;
    }

    /**
     * 根据点检计划ID查询点检计划详情-WEB
     * @param planId
     * @param corporateIdentify
     * @return
     */
    public SpotInspectionPlanDetailWarpResp querySpotInspectionPlanDetailByPlanId(Long planId, Long corporateIdentify){

        List<SpotInspectionPlanDevice> planDeviceList = spotInspectionPlanDeviceRepository.findByCorporateIdentifyAndSpotInspectionPlan(corporateIdentify, planId);

        if(!CheckParam.isNull(planDeviceList) && !planDeviceList.isEmpty()){

            SpotInspectionPlan plan = spotInspectionPlanRepository.findOne(planId);

            SpotInspectionPlanDetailWarpResp spotInspectionPlanDetailWarpResp = new SpotInspectionPlanDetailWarpResp();

            Department department = departmentRepository.findOne(plan.getDepartment());

            spotInspectionPlanDetailWarpResp.setDepartmentName(CheckParam.isNull(department) ? null : department.getName());
            spotInspectionPlanDetailWarpResp.setDepartment(CheckParam.isNull(department) ? null : department.getId());
            spotInspectionPlanDetailWarpResp.setEndTime(plan.getEndTime());
            spotInspectionPlanDetailWarpResp.setLastExecuteTime(plan.getLastExecuteTime());
            spotInspectionPlanDetailWarpResp.setNextExecuteTime(plan.getNextExecuteTime());
            spotInspectionPlanDetailWarpResp.setPlanStatus(plan.getPlanStatus());
            spotInspectionPlanDetailWarpResp.setRecyclePeriod(plan.getRecyclePeriod());
            spotInspectionPlanDetailWarpResp.setRecyclePeriodType(plan.getRecyclePeriodType());
            spotInspectionPlanDetailWarpResp.setSpotInspectionRange(plan.getSpotInspectionRange());
            spotInspectionPlanDetailWarpResp.setName(plan.getName());

            List<User> userList = userRepository.findByCorporateIdentifyAndUserIdIn(corporateIdentify, JSON.parseArray(plan.getExecutors(), Long.class));

            if(!CheckParam.isNull(userList) && !userList.isEmpty()){
                spotInspectionPlanDetailWarpResp.setExecutors(userList.stream().map(User::getUserId).collect(Collectors.toList()));
            }

            List<SpotInspectionPlanDeviceInfoResp> deviceInfoList = new ArrayList<>();

            List<Long> deviceInfoIds = new ArrayList<>();
            List<Long> deviceTypeIds = new ArrayList<>();
            List<Long> planStandardList = new ArrayList<>();


            planDeviceList.forEach(p1 -> {
                deviceInfoIds.add(p1.getDeviceId());
                deviceTypeIds.add(p1.getDeviceType());
                planStandardList.add(p1.getSpotInspectionStandard());
            });

            List<DeviceInfo> deviceInfos = deviceInfoRepository.findByIdsIn(deviceInfoIds);

            if(!CheckParam.isNull(deviceInfos) && !deviceInfos.isEmpty()){

                //形成设备Map
                Map<Long, DeviceInfo> deviceInfoMap = deviceInfos.stream().collect(Collectors.toMap(DeviceInfo::getId, Function.identity()));

                List<DeviceType> deviceTypeList = deviceTypeRepository.findByCorporateIdentifyAndIdIn(corporateIdentify, deviceTypeIds);
                Map<Long,DeviceType> deviceTypeMap = deviceTypeList.stream().collect(Collectors.toMap(DeviceType::getId, Function.identity()));

                List<SpotInspectionStandard> standardList = spotInspectionStandardRepository.findByCorporateIdentifyAndIdIn(corporateIdentify, planStandardList);
                Map<Long, SpotInspectionStandard> standardMap = standardList.stream().collect(Collectors.toMap(SpotInspectionStandard::getId, Function.identity()));


                planDeviceList.stream().forEach(p1 -> {

                    if(!CheckParam.isNull(deviceInfoMap.get(p1.getDeviceId()))){

                        SpotInspectionPlanDeviceInfoResp info = new SpotInspectionPlanDeviceInfoResp();

                        DeviceInfo deviceInfo = deviceInfoMap.get(p1.getDeviceId());

                        info.setDeviceCode(deviceInfo.getCode());
                        info.setDeviceName(deviceInfo.getName());
                        info.setDeviceSpecification(deviceInfo.getSpecification());
                        info.setPlanDeviceId(p1.getId());

                        //boolean b = !CheckParam.isNull(CheckParam.isNull(deviceInfoMap)) && !CheckParam.isNull(deviceTypeMap.get(deviceInfo.getDeviceType()));

                        if(!CheckParam.isNull(CheckParam.isNull(deviceTypeMap)) && !CheckParam.isNull(deviceTypeMap.get(deviceInfo.getDeviceType()))){
                            info.setDeviceTypeName(deviceTypeMap.get(deviceInfo.getDeviceType()).getName());
                            info.setDeviceTypeId(deviceTypeMap.get(deviceInfo.getDeviceType()).getId());

                        }

                        //格式化巡检标准名称
                        if(!CheckParam.isNull(CheckParam.isNull(standardMap)) && !CheckParam.isNull(standardMap.get(p1.getSpotInspectionStandard()))){
                            info.setStandardName(standardMap.get(p1.getSpotInspectionStandard()).getName());
                            info.setStandardId(standardMap.get(p1.getSpotInspectionStandard()).getId());
                        }


                        info.setLineOrder(p1.getLineOrder());
                        info.setDeviceId(p1.getDeviceId());
                        deviceInfoList.add(info);
                    }
                });

                spotInspectionPlanDetailWarpResp.setInfoList(deviceInfoList);

                return spotInspectionPlanDetailWarpResp;
            }
        }


        return null;
    }


    /**
     * 删除巡检计划
     * @param planId
     * @param corporateIdentify
     */
    public void deleteSpotInspectionPlanDetailByPlanId(Long planId, Long corporateIdentify){

        SpotInspectionPlan plan = spotInspectionPlanRepository.findOne(planId);

        if(CheckParam.isNull(plan)){
            throw new BussinessException(ErrorCode.NO_SPOTINSPECTIONPLAN__EXIST_ERROR.getCode(),
                    ErrorCode.NO_SPOTINSPECTIONPLAN__EXIST_ERROR.getMessage());
        }

        List<SpotInspectionPlanDevice> planDeviceList = spotInspectionPlanDeviceRepository.findByCorporateIdentifyAndSpotInspectionPlan(corporateIdentify,
                planId);

        /**
         * 删除巡检计划需要删除巡检计划相关信息
         */
        if(!CheckParam.isNull(planDeviceList) && !planDeviceList.isEmpty()){
            spotInspectionPlanRepository.delete(plan);
            spotInspectionPlanDeviceRepository.delete(planDeviceList);
        }

    }

    /**
     * 批量删除巡检计划
     * @param ids
     * @param corporateIdentify
     */
    public void batchDeleteSpotInspectionPlanDetailByPlanId(List<Long> ids, Long corporateIdentify){

        List<SpotInspectionPlan> planList = spotInspectionPlanRepository.findByCorporateIdentifyAndIdIn(corporateIdentify, ids);


        if(!CheckParam.isNull(planList) && !planList.isEmpty()){

            List<Long> idList = new ArrayList<>();

            List<SpotInspectionPlanDevice> planDeviceList = spotInspectionPlanDeviceRepository.findByCorporateIdentifyAndSpotInspectionPlanIn(corporateIdentify, idList);

            spotInspectionPlanRepository.deleteInBatch(planList);

            if(!CheckParam.isNull(planDeviceList) && !planDeviceList.isEmpty()) {
                spotInspectionPlanDeviceRepository.deleteInBatch(planDeviceList);
            }

        }

    }

    /**
     * 编辑巡检计划
     * @param spotInspectionPlanEditReq
     * @param corporateIdentify
     */
    public void editSpotInspectionPlan(SpotInspectionPlanEditReq spotInspectionPlanEditReq, Long corporateIdentify){

        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>巡检计划编辑接口入参<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+JSON.toJSONString(spotInspectionPlanEditReq)
                +">>>>>>>>>>>>>>>corporateIdentify<<<<<<<<<<<<<"+corporateIdentify);

        SpotInspectionPlan plan = spotInspectionPlanRepository.findOne(spotInspectionPlanEditReq.getPlanId());

        if(CheckParam.isNull(plan)){
            throw new BussinessException(ErrorCode.NO_SPOTINSPECTIONPLAN__EXIST_ERROR.getCode(),
                    ErrorCode.NO_SPOTINSPECTIONPLAN__EXIST_ERROR.getMessage());
        }

        if(!CheckParam.isNull(spotInspectionPlanEditReq.getName())){
            plan.setName(spotInspectionPlanEditReq.getName());
        }
        if(!CheckParam.isNull(spotInspectionPlanEditReq.getDepartment())){
            plan.setDepartment(spotInspectionPlanEditReq.getDepartment());
        }
        if(!CheckParam.isNull(spotInspectionPlanEditReq.getExecutors())){
            plan.setExecutors(JSON.toJSONString(spotInspectionPlanEditReq.getExecutors()));
        }

        try {
        if(!CheckParam.isNull(spotInspectionPlanEditReq.getEndTime())){
            plan.setEndTime(spotInspectionPlanEditReq.getEndTime());
        }
        if(!CheckParam.isNull(spotInspectionPlanEditReq.getNextExecuteTime())){
            plan.setNextExecuteTime(BaseUtil.strToDateTime(spotInspectionPlanEditReq.getNextExecuteTime()));
        }
        } catch (ParseException e) {
            logger.error("日期字符串转换异常 :"+e.getMessage(),e);
        }

        if(!CheckParam.isNull(spotInspectionPlanEditReq.getPlanStatus())){
            plan.setPlanStatus(spotInspectionPlanEditReq.getPlanStatus());
        }
        if(!CheckParam.isNull(spotInspectionPlanEditReq.getRecyclePeriod())){
            plan.setRecyclePeriod(spotInspectionPlanEditReq.getRecyclePeriod());
        }
        if(!CheckParam.isNull(spotInspectionPlanEditReq.getSpotInspectionRange())){
            plan.setSpotInspectionRange(spotInspectionPlanEditReq.getSpotInspectionRange());
        }
        if(!CheckParam.isNull(spotInspectionPlanEditReq.getRecyclePeriodType())){
            plan.setRecyclePeriodType(spotInspectionPlanEditReq.getRecyclePeriodType());
        }
        if(!CheckParam.isNull(corporateIdentify)){
            plan.setCorporateIdentify(corporateIdentify);
        }

        spotInspectionPlanRepository.save(plan);

        //需要删除巡检计划设备的逻辑
        if(!CheckParam.isNull(spotInspectionPlanEditReq.getPlanDeviceDeleteList()) && !spotInspectionPlanEditReq.getPlanDeviceDeleteList().isEmpty()){
            //查询之前需要去重
            List<SpotInspectionPlanDevice> planDeviceList = spotInspectionPlanDeviceRepository.findByIdIn(spotInspectionPlanEditReq.getPlanDeviceDeleteList().stream().distinct().collect(Collectors.toList()));

            if(!CheckParam.isNull(planDeviceList) && !planDeviceList.isEmpty()){
                spotInspectionPlanDeviceRepository.deleteInBatch(planDeviceList);
            }
        }

        if(!CheckParam.isNull(spotInspectionPlanEditReq.getPlanDeviceList()) && !spotInspectionPlanEditReq.getPlanDeviceList().isEmpty()) {

            List<SpotInspectionPlanDevice> spotInspectionPlanDeviceList = new ArrayList<>();

            spotInspectionPlanEditReq.getPlanDeviceList().stream().forEach(d1 -> {
                SpotInspectionPlanDevice spotInspectionPlanDevice = new SpotInspectionPlanDevice();
                spotInspectionPlanDevice.setDeviceType(d1.getDeviceType());
                spotInspectionPlanDevice.setDeviceId(d1.getDeviceId());
                spotInspectionPlanDevice.setCorporateIdentify(corporateIdentify);
                spotInspectionPlanDevice.setSpotInspectionPlan(d1.getSpotInspectionStandard());
                spotInspectionPlanDevice.setLineOrder(d1.getLineOrder());
                spotInspectionPlanDevice.setSpotInspectionStandard(d1.getSpotInspectionStandard());
                spotInspectionPlanDevice.setDeviceId(d1.getDeviceId());
                spotInspectionPlanDevice.setDeviceType(d1.getDeviceType());

                spotInspectionPlanDeviceList.add(spotInspectionPlanDevice);
            });
            spotInspectionPlanDeviceRepository.save(spotInspectionPlanDeviceList);
        }

        }

    /**
     * 执行巡检计划
     * @param spotInspectionPlanExecuteWarpReq
     * @param corporateIdentify
     * @param userId
     */
    public void executeSpotInspectionPlan(SpotInspectionPlanExecuteWarpReq spotInspectionPlanExecuteWarpReq,Long corporateIdentify,Long userId){
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>执行巡检计划接口入参<<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+JSON.toJSONString(spotInspectionPlanExecuteWarpReq)
                +">>>>>>>>>>>>>>>corporateIdentify<<<<<<<<<<<<<"+corporateIdentify);

        SpotInspectionPlan plan = spotInspectionPlanRepository.findOne(spotInspectionPlanExecuteWarpReq.getPlanId());

        if(CheckParam.isNull(plan)){
            throw new BussinessException(ErrorCode.NO_SPOTINSPECTIONPLAN__EXIST_ERROR.getCode(),
                    ErrorCode.NO_SPOTINSPECTIONPLAN__EXIST_ERROR.getMessage());
        }

        Date currentTime = new Date();

        //保证在一个巡检计划周期内，巡检记录只能有一条
        Date date = DateTimeUtil.subtractDateByParam(currentTime, plan.getRecyclePeriod(), plan.getRecyclePeriodType());

        //如果能查询出来表示执行过
        SpotInspectionRecord record = spotInspectionRecordRepository.findByCorporateIdentifyAndPlanIdAndCreateTimeGreaterThan(corporateIdentify, plan.getId(), date);

        if(CheckParam.isNull(record)){
            record = new SpotInspectionRecord();
        }
        record.setPlanTime(plan.getNextExecuteTime());
        try {
            plan.setNextExecuteTime(DateTimeUtil.plusDateByParam(new Date(), plan.getRecyclePeriod(), plan.getRecyclePeriodType()));
        } catch (Exception e) {
            logger.info("时间转换出现异常 :" + e.getMessage(), e);
            throw new BussinessException(ErrorCode.TIMEPARSE_ERROR.getCode(),
                    ErrorCode.TIMEPARSE_ERROR.getMessage());
        }
        plan.setLastExecuteTime(new Date());

        spotInspectionPlanRepository.save(plan);


        record.setExecuteTime(currentTime);
        record.setExecutor(userId); //执行者
        record.setPlanId(spotInspectionPlanExecuteWarpReq.getPlanId());
        record.setCorporateIdentify(corporateIdentify);

        record.setPlanName(plan.getName());
        record.setPlanTime(plan.getNextExecuteTime());
        record.setRecyclePeriod(plan.getRecyclePeriod());
        record.setRecyclePeriodType(plan.getRecyclePeriodType());

        spotInspectionRecordRepository.save(record);

        if (!CheckParam.isNull(spotInspectionPlanExecuteWarpReq.getList()) && !spotInspectionPlanExecuteWarpReq.getList().isEmpty()) {
            //巡检记录ID
            Long recordId = record.getId();
            List<SpotInspectionRecordDetail> detailList = new ArrayList<>();

            spotInspectionPlanExecuteWarpReq.getList().stream().forEach(s1 ->{
                if((!CheckParam.isNull(s1.getItemList()) && !spotInspectionPlanExecuteWarpReq.getList().isEmpty())){
                    s1.getItemList().stream().forEach(s2 ->{
                        SpotInspectionRecordDetail spotInspectionRecordDetail = new SpotInspectionRecordDetail();

                        spotInspectionRecordDetail.setAbnormalDesc(s2.getAbnormalDesc());
                        spotInspectionRecordDetail.setRecordId(recordId);
                        spotInspectionRecordDetail.setRecordResult(s2.getRecordResult());
                        spotInspectionRecordDetail.setRemark(s2.getRemark());
                        spotInspectionRecordDetail.setStandardItemId(s2.getItemId());
                        spotInspectionRecordDetail.setCorporateIdentify(corporateIdentify);

                        detailList.add(spotInspectionRecordDetail);
                    });
                }
            });

            spotInspectionRecordDetailRepository.save(detailList);

        }


    }


}
