package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.vo.req.AddMaintainPlanReq;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;

/**
 * @author chenxiang
 * @create 2018-03-02 下午2:25
 **/
public interface MaintainPlanService {

    /**
     * 新增保养计划
     * @param plan
     * @param corporateIdentify
     * @param AuthUser
     */
    public void addMaintainPlan(AddMaintainPlanReq plan, Long corporateIdentify, AuthUser AuthUser);
}
