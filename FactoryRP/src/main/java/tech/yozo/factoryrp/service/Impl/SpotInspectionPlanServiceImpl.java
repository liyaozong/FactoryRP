package tech.yozo.factoryrp.service.Impl;

import com.alibaba.fastjson.JSON;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.yozo.factoryrp.entity.SpotInspectionPlan;
import tech.yozo.factoryrp.entity.SpotInspectionPlanDevice;
import tech.yozo.factoryrp.entity.SpotInspectionStandard;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.repository.SpotInspectionPlanDeviceRepository;
import tech.yozo.factoryrp.repository.SpotInspectionPlanRepository;
import tech.yozo.factoryrp.service.SpotInspectionPlanService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.SpotInspectionPlanAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionPlanQueryReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionPlanAddResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionPlanQueryWarpResp;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

            //需要格式化数据
            List<SpotInspectionPlan> spotInspectionPlanList = page.getContent();

            spotInspectionPlanList.stream().forEach(s1 -> {

            });

        }


        return null;
    }

}
