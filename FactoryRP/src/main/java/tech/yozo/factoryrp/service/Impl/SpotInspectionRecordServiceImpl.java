package tech.yozo.factoryrp.service.Impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.yozo.factoryrp.entity.*;
import tech.yozo.factoryrp.enums.inspection.SpotInspectionDeviceAbnormalEnums;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.repository.*;
import tech.yozo.factoryrp.service.SpotInspectionRecordService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.DateTimeUtil;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.SpotInspectionRecordAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionRecordMobileAddReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordAddResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordDeatailResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordDetailWarpResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordResp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 巡检记录相关服务方法
 */
@Service
@Transactional
public class SpotInspectionRecordServiceImpl implements SpotInspectionRecordService {

    @Resource
    private DeviceInfoRepository deviceInfoRepository;

    @Resource
    private DeviceTypeRepository deviceTypeRepository;

    @Resource
    private SpotInspectionPlanDeviceRepository spotInspectionPlanDeviceRepository;

    @Resource
    private SpotInspectionRecordDetailRepository spotInspectionRecordDetailRepository;

    @Resource
    private SpotInspectionRecordRepository spotInspectionRecordRepository;

    @Resource
    private SpotInspectionPlanRepository spotInspectionPlanRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private SpotInspectionItemsRepository spotInspectionItemsRepository;

    @Resource
    private SpotInspectionStandardRepository spotInspectionStandardRepository;


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
        spotInspectionRecord.setPlanName(spotInspectionRecordAddReq.getPlanName());
        spotInspectionRecord.setPlanTime(spotInspectionRecordAddReq.getPlanTime());
        spotInspectionRecord.setRecyclePeriod(spotInspectionRecordAddReq.getRecyclePeriod());
        spotInspectionRecord.setRecyclePeriodType(spotInspectionRecordAddReq.getRecyclePeriodType());
        spotInspectionRecord.setStandard(spotInspectionRecordAddReq.getStandard());
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

    /**
     * 手机端提交巡检记录 提交某个设备的巡检项
     * @param spotInspectionRecordMobileAddReq
     * @param corporateIdentify
     * @param userId
     * @return
     */
    public SpotInspectionRecordAddResp spotInspectionItemsRecordMobileAdd(SpotInspectionRecordMobileAddReq spotInspectionRecordMobileAddReq,Long corporateIdentify,Long userId) {


        SpotInspectionRecord spotInspectionRecord = new SpotInspectionRecord();

        Date currentDate = new Date();

        spotInspectionRecord.setExecuteTime(currentDate);
        spotInspectionRecord.setExecutor(userId); //执行者
        spotInspectionRecord.setPlanId(spotInspectionRecordMobileAddReq.getPlanId());
        spotInspectionRecord.setStandard(spotInspectionRecord.getStandard());
        spotInspectionRecord.setCorporateIdentify(corporateIdentify);
        spotInspectionRecord.setDeviceId(spotInspectionRecordMobileAddReq.getDeviceId()); //设置设备ID

        //设置计划执行时间，需要取计划表里面的下次执行时间 同时在巡检记录保存成功之后更新巡检计划的下次执行时间和最后一次的执行时间
        SpotInspectionPlan plan = spotInspectionPlanRepository.findOne(spotInspectionRecordMobileAddReq.getPlanId());

        if (!CheckParam.isNull(plan)) {
            spotInspectionRecord.setPlanTime(plan.getNextExecuteTime());

            try {
                Date date = DateTimeUtil.plusDateByParam(new Date(), plan.getRecyclePeriod(), plan.getRecyclePeriodType());
                plan.setNextExecuteTime(date);
            } catch (Exception e) {
                logger.info("时间转换出现异常 :" + e.getMessage(), e);
                throw new BussinessException(ErrorCode.TIMEPARSE_ERROR.getCode(),
                        ErrorCode.TIMEPARSE_ERROR.getMessage());
            }
            plan.setLastExecuteTime(new Date());

            spotInspectionPlanRepository.save(plan);

            spotInspectionRecord.setPlanName(plan.getName());
            spotInspectionRecord.setPlanTime(plan.getNextExecuteTime());
            spotInspectionRecord.setRecyclePeriod(plan.getRecyclePeriod());
            spotInspectionRecord.setRecyclePeriodType(plan.getRecyclePeriodType());
            spotInspectionRecord.setStandard(spotInspectionRecordMobileAddReq.getSpotInspectionStandard());

            spotInspectionRecordRepository.save(spotInspectionRecord);

            if (!CheckParam.isNull(spotInspectionRecordMobileAddReq.getDetailList()) && !spotInspectionRecordMobileAddReq.getDetailList().isEmpty()) {

                List<SpotInspectionRecordDetail> detailList = new ArrayList<>();

                spotInspectionRecordMobileAddReq.getDetailList().stream().forEach(s1 -> {
                    SpotInspectionRecordDetail spotInspectionRecordDetail = new SpotInspectionRecordDetail();

                    spotInspectionRecordDetail.setAbnormalDesc(s1.getAbnormalDesc());
                    spotInspectionRecordDetail.setRecordId(spotInspectionRecord.getId());
                    spotInspectionRecordDetail.setRecordResult(s1.getRecordResult());
                    spotInspectionRecordDetail.setRemark(s1.getRemark());
                    spotInspectionRecordDetail.setStandardItemId(s1.getItemId());
                    spotInspectionRecordDetail.setCorporateIdentify(corporateIdentify);

                    detailList.add(spotInspectionRecordDetail);
                });

                spotInspectionRecordDetailRepository.save(detailList);
                spotInspectionRecordDetailRepository.save(detailList);

                SpotInspectionRecordAddResp spotInspectionRecordAddResp = new SpotInspectionRecordAddResp();

                spotInspectionRecordAddResp.setId(spotInspectionRecord.getId());

                return spotInspectionRecordAddResp;
            }

        }
        return null;
    }

    /**
     * 根据巡检ID查询巡检记录
     * @param planId
     * @param corporateIdentify
     * @return
     */
    public List<SpotInspectionRecordResp> querySpotInspectionRecordByPlanId(Long planId, Long corporateIdentify){

        SpotInspectionPlan plan = spotInspectionPlanRepository.findOne(planId);

        if(CheckParam.isNull(plan)){
            throw new BussinessException(ErrorCode.NO_SPOTINSPECTIONPLAN__EXIST_ERROR.getCode(),
                    ErrorCode.NO_SPOTINSPECTIONPLAN__EXIST_ERROR.getMessage());
        }

        List<SpotInspectionRecordResp> resultList = new ArrayList<>();


        List<SpotInspectionRecord> recordList = spotInspectionRecordRepository.findByCorporateIdentifyAndPlanId(corporateIdentify, planId);



        if(!CheckParam.isNull(recordList) && !recordList.isEmpty()){

            List<Long> userIds = new ArrayList<>();

            List<Long> recordIds = new ArrayList<>();

            recordList.stream().forEach(r1 -> {
                userIds.add(r1.getExecutor());
                recordIds.add(r1.getId());
            });

            List<User> userList = userRepository.findByCorporateIdentifyAndUserIdIn(corporateIdentify, userIds);

            //形成键为userId，值为User对象的Map结构，方便接下来定位数据
            Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getUserId, Function.identity()));

            List<SpotInspectionRecordDetail> detailList = spotInspectionRecordDetailRepository.findByCorporateIdentifyAndRecordIdIn(corporateIdentify, recordIds);

            List<SpotInspectionPlanDevice> planDeviceList = spotInspectionPlanDeviceRepository.findByCorporateIdentifyAndSpotInspectionPlan(corporateIdentify, plan.getId());

            recordList.stream().forEach(r1 -> {
                SpotInspectionRecordResp spotInspectionRecordResp = new SpotInspectionRecordResp();

                spotInspectionRecordResp.setPlanId(planId);
                spotInspectionRecordResp.setExecuteTime(r1.getCreateTime());

                if(!CheckParam.isNull(userMap) && !CheckParam.isNull(userMap.get(r1.getExecutor()))){
                    spotInspectionRecordResp.setExecutor(userMap.get(r1.getExecutor()).getUserName());
                }

                spotInspectionRecordResp.setAbnormalHandelDesc(""); //异常处理情况 暂时不知道从哪儿产生

                spotInspectionRecordResp.setRecordId(r1.getId());
                spotInspectionRecordResp.setPlanName(plan.getName());
                spotInspectionRecordResp.setPlanTime(plan.getNextExecuteTime()); //计划时间设置为下次执行时间
                spotInspectionRecordResp.setRecyclePeriod(plan.getRecyclePeriod());
                spotInspectionRecordResp.setRecyclePeriodType(plan.getRecyclePeriodType());
                spotInspectionRecordResp.setStandard(r1.getStandard()); //设置巡检标准ID


                //计算设备异常数量
                if(!CheckParam.isNull(detailList) && !detailList.isEmpty()){
                    Long count = detailList.stream().filter(d1 -> d1.getRecordId() == r1.getId() && !CheckParam.isNull(d1.getAbnormalDesc()) && d1.getAbnormalDesc().equals(SpotInspectionDeviceAbnormalEnums.SPOT_INSPECTION_ITEMS_ABNORMAL)).count();
                    spotInspectionRecordResp.setAbnormalDeviceCount(Integer.valueOf(String.valueOf(count))); //设置设备异常数量
                }

                //巡检计划下面需要检查的设备数量-该计划下实际产生了巡检记录的数量
                if(!CheckParam.isNull(planDeviceList) && !planDeviceList.isEmpty()){
                    Integer count = Integer.valueOf(String.valueOf(detailList.stream().filter(d1 -> d1.getRecordId() == r1.getId()).count()));
                    Integer normalCount = planDeviceList.size();

                    if(normalCount > count){
                        spotInspectionRecordResp.setMissCount(normalCount - count); //设置设备漏检数量
                    }else{
                        spotInspectionRecordResp.setMissCount(0);
                    }


                }

                resultList.add(spotInspectionRecordResp);
            });

            return resultList;
        }


        return null;
    }

    /**
     * 根据点检记录ID查询点检详情
     * @param recordId
     * @param corporateIdentify
     * @return
     */
    public SpotInspectionRecordDetailWarpResp querySpotInspectionRecordDetailByRecordId(Long recordId,Long corporateIdentify){

        SpotInspectionRecord record = spotInspectionRecordRepository.findOne(recordId);

        SpotInspectionPlan plan = spotInspectionPlanRepository.findOne(record.getPlanId());

        User user = userRepository.findOne(record.getExecutor());

        //spotInspectionStandardRepository.findBy

        SpotInspectionRecordDetailWarpResp restResp = new SpotInspectionRecordDetailWarpResp();

        List<SpotInspectionRecordDetail> itemDetailList = spotInspectionRecordDetailRepository.findByCorporateIdentifyAndRecordId(corporateIdentify, recordId);

        restResp.setPlanName(plan.getName());
        restResp.setExceptionHandleDesc("");
        restResp.setExecutor(user.getUserName());
        restResp.setExecuteTime(DateTimeUtil.dateToStr(record.getExecuteTime()));

        if(!CheckParam.isNull(itemDetailList) && !itemDetailList.isEmpty()){
            //
            List<SpotInspectionRecordDeatailResp> detailList = new ArrayList<>();


            itemDetailList.stream().forEach(d1 -> {

                SpotInspectionRecordDeatailResp detail = new SpotInspectionRecordDeatailResp();
                //detail.setInspectionItemId();
            });

        }


        return null;
    }

}
