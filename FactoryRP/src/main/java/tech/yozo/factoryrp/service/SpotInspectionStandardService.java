package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.vo.req.SpotInspectionStandardAddReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionStandardAddResp;

/**
 * 巡检便准服务
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/22
 * @description
 */
public interface SpotInspectionStandardService {


    /**
     * 点检标准新增方法
     * @param spotInspectionStandardAddReq
     * @param corporateIdentify
     * @return
     */
    SpotInspectionStandardAddResp addSpotInspectionStandard(SpotInspectionStandardAddReq spotInspectionStandardAddReq, Long corporateIdentify);
}
