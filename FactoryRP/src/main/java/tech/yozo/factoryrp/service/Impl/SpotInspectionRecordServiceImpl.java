package tech.yozo.factoryrp.service.Impl;


import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.SpotInspectionRecord;
import tech.yozo.factoryrp.repository.*;
import tech.yozo.factoryrp.service.SpotInspectionRecordService;
import tech.yozo.factoryrp.utils.DateTimeUtil;
import tech.yozo.factoryrp.vo.req.SpotInspectionRecordAddReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordAddResp;

import javax.annotation.Resource;

/**
 * 巡检记录相关服务方法
 */
@Service
public class SpotInspectionRecordServiceImpl implements SpotInspectionRecordService {


    @Resource
    private SpotInspectionRecordRepository spotInspectionRecordRepository;

    @Resource
    private SpotInspectionRecordDetailRepository spotInspectionRecordDetailRepository;

    @Resource
    private SpotInspectionItemsRepository spotInspectionItemsRepository;

    @Resource
    private SpotInspectionStandardRepository spotInspectionStandardRepository;

    @Resource
    private DeviceInfoRepository deviceInfoRepository;

    @Resource
    private DeviceTypeRepository deviceTypeRepository;

    /**
     * 新增巡检记录
     * @param spotInspectionRecordAddReq
     * @param corporateIdentify
     * @return
     */
    public SpotInspectionRecordAddResp addSpotInspectionRecord(SpotInspectionRecordAddReq spotInspectionRecordAddReq,Long corporateIdentify){


        SpotInspectionRecord spotInspectionRecord = new SpotInspectionRecord();


        spotInspectionRecord.setExecuteTime(DateTimeUtil.strToDate(spotInspectionRecordAddReq.getExecuteTime()));
        spotInspectionRecord.setExecutor(spotInspectionRecordAddReq.getExecutor());
        spotInspectionRecord.setPlanId(spotInspectionRecordAddReq.getPlanId());
        spotInspectionRecord.setPlanName(spotInspectionRecord.getPlanName());
        spotInspectionRecord.setPlanTime(spotInspectionRecord.getPlanTime());
        spotInspectionRecord.setRecyclePeriod(spotInspectionRecord.getRecyclePeriod());
        spotInspectionRecord.setRecyclePeriodType(spotInspectionRecordAddReq.getRecyclePeriodType());
        spotInspectionRecord.setStandard(spotInspectionRecord.getStandard());
        spotInspectionRecord.setCorporateIdentify(corporateIdentify);
        //spotInspectionRecord.set

        return null;
    }


}
