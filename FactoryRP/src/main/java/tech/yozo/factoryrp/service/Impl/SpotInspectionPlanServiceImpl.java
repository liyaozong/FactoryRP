package tech.yozo.factoryrp.service.Impl;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.SpotInspectionPlan;
import tech.yozo.factoryrp.entity.SpotInspectionPlanDevice;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.repository.SpotInspectionPlanDeviceRepository;
import tech.yozo.factoryrp.repository.SpotInspectionPlanRepository;
import tech.yozo.factoryrp.service.SpotInspectionPlanService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.SpotInspectionPlanAddReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionPlanAddResp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 巡检计划相关服务
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/25
 * @description
 */
@Service
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


}
