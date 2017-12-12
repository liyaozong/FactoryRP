package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.vo.req.AddTroubleRecordReq;
import tech.yozo.factoryrp.vo.req.TroubleListReq;
import tech.yozo.factoryrp.vo.req.WorkOrderListReq;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import tech.yozo.factoryrp.vo.resp.device.trouble.SimpleTroubleRecordVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WaitAuditWorkOrderVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderCountVo;

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
     * @param id
     */
    public void startRepair(Long id, AuthUser user);
}
