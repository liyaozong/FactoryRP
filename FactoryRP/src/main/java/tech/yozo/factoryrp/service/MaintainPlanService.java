package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.vo.req.*;
import tech.yozo.factoryrp.vo.resp.*;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;

import java.util.List;

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
     * 根据设备分页查询保养计划列表
     * @return
     */
    public Pagination<MaintainPlanListVo> findListByDeviceId(MaintainPlanListForDeviceReq param, Long corporateIdentify);

    /**
     * 查询保养计划简略信息列表
     * @param param
     * @param corporateIdentify
     * @return
     */
    public Pagination<SimpleMaintainPlanVo> findSimpleListByPage(MaintainPlanListForAppReq param,Long corporateIdentify,AuthUser user);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    public AddMaintainPlanReq getDetailById(Long id);

    /**
     * 统计当前登陆人的保养计划数量
     * @param corporateIdentify
     * @return
     */
    public MaintainPlanCountVo getCount(Long corporateIdentify,AuthUser user);

    /**
     * 查询保养计划详情
     * @param id
     * @param user
     * @return
     */
    public MaintainPlanAppQueryVo getDetail(Long id,AuthUser user);

    /**
     * 客户端提交保养记录
     * @param param
     */
    public void appSubmit(MaintainDetailSubmitReq param,AuthUser user);

    /**
     * 批量删除
     * @param ids
     */
    public void batchDelete(List<Long> ids);
}
