package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.vo.req.SpotInspectionStandardAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionStandardQueryReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionStandardAddResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionStandardDetailQueryResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionStandardQueryResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionStandardQueryWarpResp;

import java.util.List;

/**
 * 巡检标准服务
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/22
 * @description
 */
public interface SpotInspectionStandardService {

    /**
     * 批量删除点检标准
     * @param ids
     * @param corporateIdentify
     */
    void deleteSpotInspectionStandardByIds(List<Long> ids,Long corporateIdentify);

    /**
     * 查询点巡检标准详情
     * @param standardId
     * @param corporateIdentify
     * @return
     */
    SpotInspectionStandardDetailQueryResp queryInspectionStandardDetail(Long standardId, Long corporateIdentify);

    /**
     * 点检标准分页查询
     * @param spotInspectionStandardQueryReq
     * @param corporateIdentify
     * @return
     */
    SpotInspectionStandardQueryWarpResp findByPage(SpotInspectionStandardQueryReq spotInspectionStandardQueryReq, Long corporateIdentify);

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
