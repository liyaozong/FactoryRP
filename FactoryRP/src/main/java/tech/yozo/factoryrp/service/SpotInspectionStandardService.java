package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.vo.req.SpotInspectionStandardAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionStandardQueryReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionStandardAddResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionStandardQueryResp;

import java.util.List;

/**
 * 巡检标准服务
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/22
 * @description
 */
public interface SpotInspectionStandardService {


    /**
     * 点检标准分页查询
     * @param spotInspectionStandardQueryReq
     * @param corporateIdentify
     * @return
     */
    List<SpotInspectionStandardQueryResp> findByPage(SpotInspectionStandardQueryReq spotInspectionStandardQueryReq, Long corporateIdentify);

    /**
     * 点检标准新增方法
     * @param spotInspectionStandardAddReq
     * @param corporateIdentify
     * @return
     */
    SpotInspectionStandardAddResp addSpotInspectionStandard(SpotInspectionStandardAddReq spotInspectionStandardAddReq, Long corporateIdentify);


    /**
     * 删除巡检标准
     * @param standardId
     * @param corporateIdentify
     */
    void deleteInspectionStandard(Long standardId,Long corporateIdentify);
}
