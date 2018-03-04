package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.vo.req.SpotInspectionPlanAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionPlanQueryReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionPlanAddResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionPlanDetailWarpResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionPlanQueryWarpResp;
import tech.yozo.factoryrp.vo.resp.inspection.mobile.SpotInspectionPlanDeviceQueryResp;
import tech.yozo.factoryrp.vo.resp.inspection.mobile.SpotInspectionPlanResp;

import java.util.List;

/**
 * 巡检计划相关服务
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/25
 * @description
 */
public interface SpotInspectionPlanService {

    /**
     * 根据点检计划ID查询点检计划详情-WEB
     * @param planId
     * @param corporateIdentify
     * @return
     */
    SpotInspectionPlanDetailWarpResp querySpotInspectionPlanDetailByPlanId(Long planId, Long corporateIdentify);

    /**
     * 查询巡检计划关联的设备
     * @param planId
     * @param corporateIdentify
     * @return
     */
    List<SpotInspectionPlanDeviceQueryResp> querySpotInspectionPlanDevices(Long planId, Long corporateIdentify);

    /**
     * 新增点检计划
     * @param spotInspectionPlanAddReq
     * @param corporateIdentify
     * @return
     */
    SpotInspectionPlanAddResp addSpotInspectionPlan(SpotInspectionPlanAddReq spotInspectionPlanAddReq, Long corporateIdentify);


    /**
     * 手机端查询点检任务
     * @param userId
     * @param corporateIdentify
     * @return
     */
    List<SpotInspectionPlanResp> queryMobilePlan(Long userId, Long corporateIdentify);

    /**
     * 根据部门ID查询点检计划
     * @param spotInspectionPlanQueryReq
     * @param corporateIdentify
     * @return
     */
    SpotInspectionPlanQueryWarpResp findByPage(SpotInspectionPlanQueryReq spotInspectionPlanQueryReq, Long corporateIdentify);

}
