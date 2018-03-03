package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.vo.req.AddMaintainPlanReq;
import tech.yozo.factoryrp.vo.req.MaintainPlanListForAppReq;
import tech.yozo.factoryrp.vo.req.MaintainPlanListReq;
import tech.yozo.factoryrp.vo.resp.MaintainPlanDetailVo;
import tech.yozo.factoryrp.vo.resp.MaintainPlanListVo;
import tech.yozo.factoryrp.vo.resp.SimpleMaintainPlanVo;
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

    /**
     * 分页查询列表
     * @param param
     * @return
     */
    public Pagination<MaintainPlanListVo> findByPage(MaintainPlanListReq param,Long corporateIdentify);

    /**
     * 查询保养计划简略信息列表
     * @param param
     * @param corporateIdentify
     * @return
     */
    public Pagination<SimpleMaintainPlanVo> findSimpleListByPage(MaintainPlanListForAppReq param,Long corporateIdentify);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    public MaintainPlanDetailVo getDetailById(Long id);
}
