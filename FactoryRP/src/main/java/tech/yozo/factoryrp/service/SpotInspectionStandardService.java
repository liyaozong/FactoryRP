package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.vo.req.SpotInspectionStandardAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionStandardQueryReq;
import tech.yozo.factoryrp.vo.resp.inspection.*;
import tech.yozo.factoryrp.vo.resp.inspection.mobile.SpotInspectionItemsQueryWarpResp;
import tech.yozo.factoryrp.vo.resp.inspection.mobile.SpotInspectionStandardDetailMobileQueryResp;

import java.util.List;

/**
 * 巡检标准服务
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/22
 * @description
 */
public interface SpotInspectionStandardService {


    /**
     * 手机端根据巡检计划ID和设备code查询巡检项目
     * @param planId
     * @param deviceCode
     * @param corporateIdentify
     * @return
     */
    SpotInspectionItemsQueryWarpResp queryMobileInspectionItemByPlanIdAndDeviceId(Long planId, String deviceCode, Long corporateIdentify);

    /**
     * 根据设备ID查询点检标准
     * @param deviceId
     * @param corporateIdentify
     * @return
     */
    List<SpotInspectionStandardQueryResp> queryStandardByDeviceId(Long deviceId, Long corporateIdentify);

    /**
     * 批量删除点检标准
     * @param ids
     * @param corporateIdentify
     */
    void deleteSpotInspectionStandardByIds(List<Long> ids,Long corporateIdentify);


    /**
     * 查询手机端巡检项目详情
     * @param standardId
     * @param corporateIdentify
     * @return
     */
    //SpotInspectionStandardDetailMobileQueryResp queryMobileInspectionStandardDetail(Long standardId, Long corporateIdentify);

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
     * 通过巡检标准ID和巡检计划ID进行查询巡检项目相关数据
     * @param standardId
     * @param planId
     * @param corporateIdentify
     * @return
     */
    List<SpotInspectionStandardItemsQueryResp> findSpotInspectionStandardItemByStandardIdAndPlanId(Long standardId, Long planId, Long corporateIdentify);

    /**
     * 删除巡检标准
     * @param standardId
     * @param corporateIdentify
     */
    void deleteInspectionStandard(Long standardId,Long corporateIdentify);
}
