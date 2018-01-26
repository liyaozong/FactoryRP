package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.vo.req.SpotInspectionPlanAddReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionPlanAddResp;

/**
 * 巡检计划相关服务
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/25
 * @description
 */
public interface SpotInspectionPlanService {

    /**
     * 新增点检计划
     * @param spotInspectionPlanAddReq
     * @param corporateIdentify
     * @return
     */
    SpotInspectionPlanAddResp addSpotInspectionPlan(SpotInspectionPlanAddReq spotInspectionPlanAddReq, Long corporateIdentify);

}
