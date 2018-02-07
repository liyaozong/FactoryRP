package tech.yozo.factoryrp.service.Impl;


import com.sun.tools.javac.comp.Check;
import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.SpotInspectionPlan;
import tech.yozo.factoryrp.entity.SpotInspectionRecord;
import tech.yozo.factoryrp.repository.*;
import tech.yozo.factoryrp.service.SpotInspectionRecordService;
import tech.yozo.factoryrp.utils.CheckParam;
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
    private SpotInspectionPlanRepository spotInspectionPlanRepository;

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

        //设置计划执行时间，需要取计划表里面的下次执行时间 同时在巡检记录保存成功之后更新巡检计划的下次执行时间和最后一次的执行时间
        SpotInspectionPlan plan = spotInspectionPlanRepository.findOne(spotInspectionRecordAddReq.getPlanId());

        if(!CheckParam.isNull(plan)){
            spotInspectionRecord.setPlanTime(plan.getNextExecuteTime());
        }

        spotInspectionRecordRepository.save(spotInspectionRecord);


        return null;
    }


}
