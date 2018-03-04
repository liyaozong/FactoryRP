package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.vo.req.SpotInspectionRecordAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionRecordMobileAddReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordAddResp;

/**
 * 巡检记录相关服务
 */
public interface SpotInspectionRecordService {

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
     * @return
     */
    SpotInspectionRecordAddResp spotInspectionItemsRecordMobileAdd(SpotInspectionRecordMobileAddReq spotInspectionRecordMobileAddReq);

}
