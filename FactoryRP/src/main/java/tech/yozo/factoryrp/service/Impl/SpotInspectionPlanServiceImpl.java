package tech.yozo.factoryrp.service.Impl;

import com.alibaba.fastjson.JSON;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.yozo.factoryrp.entity.*;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.repository.DepartmentRepository;
import tech.yozo.factoryrp.repository.SpotInspectionPlanDeviceRepository;
import tech.yozo.factoryrp.repository.SpotInspectionPlanRepository;
import tech.yozo.factoryrp.repository.UserRepository;
import tech.yozo.factoryrp.service.SpotInspectionPlanService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.SpotInspectionPlanAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionPlanQueryReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionPlanAddResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionPlanQueryResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionPlanQueryWarpResp;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
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
    private DepartmentRepository departmentRepository;



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
        spotInspectionPlan.setEndTime(spotInspectionPlanAddReq.getEndTime());
        spotInspectionPlan.setNextExecuteTime(spotInspectionPlanAddReq.getNextExecuteTime());
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
                spotInspectionPlanDevice.setRelateDevices(JSON.toJSONString(d1.getRelateDevices()));
                spotInspectionPlanDevice.setCorporateIdentify(corporateIdentify);
                spotInspectionPlanDevice.setSpotInspectionPlan(spotInspectionPlan.getId());
                spotInspectionPlanDevice.setLineOrder(d1.getLineOrder());

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
     * 根据部门
     * @param spotInspectionPlanQueryReq
     * @param corporateIdentify
     * @return
     */
    public SpotInspectionPlanQueryWarpResp findByPage(SpotInspectionPlanQueryReq spotInspectionPlanQueryReq, Long corporateIdentify){

        Pageable p = new PageRequest(spotInspectionPlanQueryReq.getCurrentPage(), spotInspectionPlanQueryReq.getItemsPerPage());

        Page<SpotInspectionPlan> page = spotInspectionPlanRepository.findAll((Root<SpotInspectionPlan> root,
                                                                                      CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> list = new ArrayList<>();

            if (!CheckParam.isNull(spotInspectionPlanQueryReq.getDepartmentId())) { //部门ID
                list.add(criteriaBuilder.equal(root.get("department").as(Long.class), spotInspectionPlanQueryReq.getDepartmentId()));
            }
            if (!CheckParam.isNull(spotInspectionPlanQueryReq.getNextExecuteTime())) { //下次执行时间
                list.add(criteriaBuilder.equal(root.get("remark").as(Date.class), spotInspectionPlanQueryReq.getNextExecuteTime()));
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

            List<Long> userIds = new ArrayList<>();

            List<Long> planIds = new ArrayList<>();

            spotInspectionPlanList.stream().forEach(s1 -> {

                deptIds.add(s1.getDepartment()); //格式化部门名称之用
                userIds.addAll(JSON.parseArray(s1.getExecutors(),Long.class)); //格式化执行人姓名之用
                planIds.add(s1.getId()); //格式化关联设备数量之用

            });


            List<User> userList = userRepository.findByCorporateIdentifyAndUserIdIn(corporateIdentify, userIds);

            List<Department> departmentList = departmentRepository.findByCorporateIdentifyAndIdIn(corporateIdentify, deptIds);


            List<SpotInspectionPlanDevice> devices = spotInspectionPlanDeviceRepository.findByCorporateIdentifyAndSpotInspectionPlanIn(corporateIdentify, planIds);

            Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getUserId, Function.identity()));
            Map<Long, Department> departmentMap = departmentList.stream().collect(Collectors.toMap(Department::getId, Function.identity()));
            Map<Long, List<SpotInspectionPlanDevice>> devicesMap =
                    devices.stream().collect(Collectors.groupingBy(SpotInspectionPlanDevice::getSpotInspectionPlan));

            spotInspectionPlanList.stream().forEach(s1 -> {

                SpotInspectionPlanQueryResp spotInspectionPlanQueryResp = new SpotInspectionPlanQueryResp();

                spotInspectionPlanQueryResp.setName(s1.getName());

                 if(!CheckParam.isNull(userMap) && !userMap.isEmpty()){
                     //s1.setExecutorsName();
                 }

                 //格式化部门
                if(!CheckParam.isNull(departmentMap) && !departmentMap.isEmpty() && !CheckParam.isNull(departmentMap.get(s1.getDepartment()))){
                    spotInspectionPlanQueryResp.setDepartmentName(departmentMap.get(s1.getDepartment()).getName());
                }
                if(!CheckParam.isNull(devicesMap) && !devicesMap.isEmpty() && !CheckParam.isNull(devicesMap.get(s1.getId()))){
                    spotInspectionPlanQueryResp.setDeviceCount(devicesMap.get(s1.getId()).size());
                }

                spotInspectionPlanQueryResp.setPlanStatus(s1.getPlanStatus());
                spotInspectionPlanQueryResp.setLastExecuteTime(s1.getLastExecuteTime());
                spotInspectionPlanQueryResp.setNextExecuteTime(s1.getNextExecuteTime());

                spotInspectionPlanQueryRespList.add(spotInspectionPlanQueryResp);
            });


        }


        return null;
    }

}
