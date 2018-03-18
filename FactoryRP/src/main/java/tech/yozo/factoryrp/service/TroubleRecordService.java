package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.vo.req.*;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import tech.yozo.factoryrp.vo.resp.device.trouble.*;

import java.util.List;

/**
 * @author chenxiang
 * @create 2017-12-03 下午10:58
 **/
public interface TroubleRecordService {

    /**
     * 新增故障
     * @param param
     * @param corporateIdentify
     */
    public void addTroubleRecord(AddTroubleRecordReq param,Long corporateIdentify,AuthUser user);


    /**
     * 分页查询故障列表
     * @param param
     * @return
     */
    public Pagination<SimpleTroubleRecordVo> findByPage(TroubleListReq param);

    /**
     * 批量删除故障信息
     * @param ids
     */
    public void batchDelete(List<Long> ids);

    /**
     * 查询工单
     * @return
     */
    public Pagination<WaitAuditWorkOrderVo> findWorkOrderByPage(WorkOrderListReq req, Long corporateIdentify,
                                                                Integer status,AuthUser user);

    /**
     * 查询当前人员待审核工单
     * @return
     */
    public Pagination<WorkOrderWebListVo> findWaitAuditWorkOrder(WorkOrderListReq req, Long corporateIdentify, AuthUser user);

    /**
     * 工单统计
     * @param corporateIdentify
     * @param user
     * @return
     */
    public WorkOrderCountVo getCount(Long corporateIdentify,AuthUser user);

    /**
     * 抢单
     * @param id
     * @param user
     */
    public void obtainOrder(Long id,AuthUser user);

    /**
     * 维修派工
     * @param param
     */
    public void allocateWorker(AllocateWorkerReq param);

    /**
     * 撤销工单
     * @param id
     * @param user
     */
    public void cancelOrder(Long id,AuthUser user);

    /**
     * 开始维修
     */
    public void startRepair(StartRepairReq param, AuthUser user);

    /**
     * 完成维修
     */
    public void endRepair(EndRepairReq param, AuthUser user);

    /**
     * 完成维修并提交
     * @param user
     */
    public void submitRepair(SubmitRepairReq param,AuthUser user);

    /**
     * 查询工单详情(包括故障信息、处理意见、工作量、更换的配件)
     * @param id
     * @param user
     * @return
     */
    public WorkOrderDetailVo getDetail(Long id,AuthUser user);

    /**
     * 根据主键查询故障详情
     * @param id
     * @return
     */
    public SingleTroubleDetail getDetailById(Long id);


    /**
     * 验证工单
     */
    public void validate(ValidateRepairReq param, AuthUser user);

    /**
     * 审核工单
     * @param param
     * @param user
     */
    public void audit(AuditWorkNumReq param,AuthUser user);
}
