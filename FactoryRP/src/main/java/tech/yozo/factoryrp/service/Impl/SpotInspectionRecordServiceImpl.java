package tech.yozo.factoryrp.service.Impl;


import com.sun.tools.javac.comp.Check;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.yozo.factoryrp.entity.SpotInspectionPlan;
import tech.yozo.factoryrp.entity.SpotInspectionRecord;
import tech.yozo.factoryrp.entity.SpotInspectionRecordDetail;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.repository.*;
import tech.yozo.factoryrp.service.SpotInspectionRecordService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.DateTimeUtil;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.SpotInspectionRecordAddReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordAddResp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 巡检记录相关服务方法
 */
@Service
@Transactional
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


    private static Logger logger = LoggerFactory.getLogger(SpotInspectionRecordServiceImpl.class);

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

            try {
                Date date = DateTimeUtil.plusDateByParam(plan.getNextExecuteTime(), plan.getRecyclePeriod(), plan.getRecyclePeriodType());
                plan.setNextExecuteTime(date);
            }catch (Exception e){
                logger.info("时间转换出现异常 :"+e.getMessage(),e);
                throw new BussinessException(ErrorCode.TIMEPARSE_ERROR.getCode(),
                        ErrorCode.TIMEPARSE_ERROR.getMessage());
            }
            plan.setLastExecuteTime(new Date());

            spotInspectionPlanRepository.save(plan);
        }

        spotInspectionRecordRepository.save(spotInspectionRecord);

        if(!CheckParam.isNull(spotInspectionRecordAddReq.getDetailList()) && !spotInspectionRecordAddReq.getDetailList().isEmpty()){

            List<SpotInspectionRecordDetail> detailList = new ArrayList<>();

            spotInspectionRecordAddReq.getDetailList().stream().forEach(s1 -> {
                SpotInspectionRecordDetail spotInspectionRecordDetail = new SpotInspectionRecordDetail();

                spotInspectionRecordDetail.setAbnormalDesc(s1.getAbnormalDesc());
                spotInspectionRecordDetail.setRecordId(s1.getRecordId());
                spotInspectionRecordDetail.setRecordResult(s1.getRecordResult());
                spotInspectionRecordDetail.setRemark(s1.getRemark());
                spotInspectionRecordDetail.setStandardItemId(s1.getStandardItemId());
                spotInspectionRecordDetail.setCorporateIdentify(corporateIdentify);

                detailList.add(spotInspectionRecordDetail);
            });

            spotInspectionRecordDetailRepository.save(detailList);

            SpotInspectionRecordAddResp spotInspectionRecordAddResp = new SpotInspectionRecordAddResp();

            spotInspectionRecordAddResp.setId(spotInspectionRecord.getId());

            return spotInspectionRecordAddResp;
        }

        return null;
    }


}
