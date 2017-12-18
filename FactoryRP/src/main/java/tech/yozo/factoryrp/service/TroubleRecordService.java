package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.vo.req.*;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import tech.yozo.factoryrp.vo.resp.device.trouble.SimpleTroubleRecordVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WaitAuditWorkOrderVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderCountVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderDetailVo;

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
     * @param id
     * @param user
     */
    public void submitRepair(Long id,AuthUser user);

    /**
     * 查询工单详情(包括故障信息、处理意见、工作量、更换的配件)
     * @param id
     * @param user
     * @return
     */
    public WorkOrderDetailVo getDetail(Long id,AuthUser user);
}
