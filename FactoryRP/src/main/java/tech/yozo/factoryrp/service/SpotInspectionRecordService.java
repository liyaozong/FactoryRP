package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.vo.req.SpotInspectionRecordAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionRecordBatchDeleteReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionRecordMobileAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionRecordPageQueryReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordAddResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordDetailWarpResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordPageQueryResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordResp;

import java.util.List;

/**
 * 巡检记录相关服务
 */
public interface SpotInspectionRecordService {


    /**
     * 巡检记录分页查询
     * @param spotInspectionRecordPageQueryReq
     * @param corporateIdentify
     * @return
     */
    Pagination<SpotInspectionRecordPageQueryResp> findByPage(SpotInspectionRecordPageQueryReq spotInspectionRecordPageQueryReq,Long corporateIdentify);

    /**
     * 根据点检记录ID查询点检详情
     * @param recordId
     * @param planId
     * @param corporateIdentify
     * @return
     */
    SpotInspectionRecordDetailWarpResp querySpotInspectionRecordDetailByRecordId(Long recordId,Long planId,Long corporateIdentify);

    /**
     * 根据巡检ID查询巡检记录
     * @param planId
     * @param corporateIdentify
     * @return
     */
    List<SpotInspectionRecordResp> querySpotInspectionRecordByPlanId(Long planId,Long corporateIdentify);

    /**
     * 新增巡检记录
     * @param spotInspectionRecordAddReq
     * @param corporateIdentify
     * @return
     */
    SpotInspectionRecordAddResp addSpotInspectionRecord(SpotInspectionRecordAddReq spotInspectionRecordAddReq, Long corporateIdentify);


    /**
     * 手机端提交巡检记录
     * @param spotInspectionRecordMobileAddReq
     * @param corporateIdentify
     * @param userId
     * @return
     */
    SpotInspectionRecordAddResp spotInspectionItemsRecordMobileAdd(SpotInspectionRecordMobileAddReq spotInspectionRecordMobileAddReq,Long corporateIdentify,Long userId);


    /**
     * 巡检记录批量删除
     * @param spotInspectionRecordBatchDeleteReq
     * @param corporateIdentify
     */
    void batchDeleteSpotInspectionRecord(SpotInspectionRecordBatchDeleteReq spotInspectionRecordBatchDeleteReq,Long corporateIdentify);
}
