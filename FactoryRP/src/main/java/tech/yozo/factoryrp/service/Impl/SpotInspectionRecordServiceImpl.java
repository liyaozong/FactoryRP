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
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordDetailResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordDetailWarpResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordResp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

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
                spotInspectionRecordDetail.setStandard(s1.getStandard());
                spotInspectionRecordDetail.setDeviceId(s1.getDeviceId());

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

        //设置计划执行时间，需要取计划表里面的下次执行时间 同时在巡检记录保存成功之后更新巡检计划的下次执行时间和最后一次的执行时间
        SpotInspectionPlan plan = spotInspectionPlanRepository.findOne(spotInspectionRecordMobileAddReq.getPlanId());

            if (CheckParam.isNull(plan)) {
                throw new BussinessException(ErrorCode.NO_SPOTINSPECTIONPLAN__EXIST_ERROR.getCode(),
                        ErrorCode.NO_SPOTINSPECTIONPLAN__EXIST_ERROR.getMessage());
        }

        Date currentTime = new Date();

        //保证在一个巡检计划周期内，巡检记录只能有一条
        Date date = DateTimeUtil.subtractDateByParam(currentTime, plan.getRecyclePeriod(), plan.getRecyclePeriodType());

        //如果能查询出来表示执行过
        SpotInspectionRecord record = spotInspectionRecordRepository.findByCorporateIdentifyAndPlanIdAndCreateTimeGreaterThan(corporateIdentify, plan.getId(), date);

        if(CheckParam.isNull(record)){
            record = new SpotInspectionRecord();
        }

        record.setPlanTime(plan.getNextExecuteTime());
            try {
                plan.setNextExecuteTime(DateTimeUtil.plusDateByParam(new Date(), plan.getRecyclePeriod(), plan.getRecyclePeriodType()));
            } catch (Exception e) {
                logger.info("时间转换出现异常 :" + e.getMessage(), e);
                throw new BussinessException(ErrorCode.TIMEPARSE_ERROR.getCode(),
                        ErrorCode.TIMEPARSE_ERROR.getMessage());
            }
            plan.setLastExecuteTime(new Date());

            spotInspectionPlanRepository.save(plan);


        record.setExecuteTime(currentTime);
        record.setExecutor(userId); //执行者
        record.setPlanId(spotInspectionRecordMobileAddReq.getPlanId());
        record.setCorporateIdentify(corporateIdentify);

        record.setPlanName(plan.getName());
        record.setPlanTime(plan.getNextExecuteTime());
        record.setRecyclePeriod(plan.getRecyclePeriod());
        record.setRecyclePeriodType(plan.getRecyclePeriodType());

        spotInspectionRecordRepository.save(record);

            if (!CheckParam.isNull(spotInspectionRecordMobileAddReq.getDetailList()) && !spotInspectionRecordMobileAddReq.getDetailList().isEmpty()) {

                List<SpotInspectionRecordDetail> detailList = new ArrayList<>();

                //巡检记录ID
                Long recordId = record.getId();

                spotInspectionRecordMobileAddReq.getDetailList().stream().forEach(s1 -> {
                    SpotInspectionRecordDetail spotInspectionRecordDetail = new SpotInspectionRecordDetail();

                    spotInspectionRecordDetail.setAbnormalDesc(s1.getAbnormalDesc());
                    spotInspectionRecordDetail.setRecordId(recordId);
                    spotInspectionRecordDetail.setRecordResult(s1.getRecordResult());
                    spotInspectionRecordDetail.setRemark(s1.getRemark());
                    spotInspectionRecordDetail.setStandardItemId(s1.getItemId());
                    spotInspectionRecordDetail.setCorporateIdentify(corporateIdentify);
                    spotInspectionRecordDetail.setDeviceId(spotInspectionRecordMobileAddReq.getDeviceId());
                    spotInspectionRecordDetail.setStandard(spotInspectionRecordMobileAddReq.getSpotInspectionStandard());

                    detailList.add(spotInspectionRecordDetail);
                });

                spotInspectionRecordDetailRepository.save(detailList);

                SpotInspectionRecordAddResp spotInspectionRecordAddResp = new SpotInspectionRecordAddResp();

                spotInspectionRecordAddResp.setId(record.getId());

                return spotInspectionRecordAddResp;
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
     * @param planId
     * @param corporateIdentify
     * @return
     */
    public SpotInspectionRecordDetailWarpResp querySpotInspectionRecordDetailByRecordId(Long recordId,Long planId,Long corporateIdentify){

        SpotInspectionRecord record = spotInspectionRecordRepository.findOne(recordId);

        if(CheckParam.isNull(record)){
            throw new BussinessException(ErrorCode.SPOTINSPECTION_RECORD_NOTEXIST_ERROR.getCode(),ErrorCode.SPOTINSPECTION_RECORD_NOTEXIST_ERROR.getMessage());

        }

        SpotInspectionPlan plan = spotInspectionPlanRepository.findOne(planId);

        //查询出这个巡检计划下面有多少巡检设备关联 一个设备，只有一个巡检计划和一个巡检标准
        List<SpotInspectionPlanDevice> planDeviceList = spotInspectionPlanDeviceRepository.findByCorporateIdentifyAndSpotInspectionPlan(corporateIdentify, planId);

        if(!CheckParam.isNull(planDeviceList) && !planDeviceList.isEmpty()){
            List<Long> deviceIds = new ArrayList<>();
            List<Long> standardIds = new ArrayList<>();
            planDeviceList.forEach(d1 -> {
                deviceIds.add(d1.getDeviceId());
                standardIds.add(d1.getSpotInspectionStandard());
            });

            List<SpotInspectionStandard> standardList = spotInspectionStandardRepository.findByCorporateIdentifyAndIdIn(corporateIdentify, standardIds.stream().distinct().collect(Collectors.toList()));
            List<DeviceInfo> deviceInfoList = deviceInfoRepository.findByIdsIn(deviceIds.stream().distinct().collect(Collectors.toList()));

            //去重
            Map<Long, SpotInspectionStandard> standardMap = standardList.stream().collect(Collectors.toMap(SpotInspectionStandard::getId, Function.identity()));
            Map<Long, DeviceInfo> deviceInfoMap = deviceInfoList.stream().collect(Collectors.toMap(DeviceInfo::getId, Function.identity()));


            //根据记录ID查询出巡检细节 包含设备ID和点检标准ID
            List<SpotInspectionRecordDetail> itemDetailList = spotInspectionRecordDetailRepository.findByCorporateIdentifyAndRecordId(corporateIdentify, recordId);

            //查询出每个巡检标准需要执行的巡检项
            List<SpotInspectionItems> standardItemList = spotInspectionItemsRepository.findByCorporateIdentifyAndStandardIn(corporateIdentify, standardIds.stream().distinct().collect(Collectors.toList()));

            //按照巡检标准ID进行分组
            Map<Long, List<SpotInspectionItems>> itemMap = standardItemList.stream().collect(groupingBy(SpotInspectionItems::getStandard));
            Map<Long, List<SpotInspectionRecordDetail>> itemExecuteDetailMap = itemDetailList.stream().collect(groupingBy(SpotInspectionRecordDetail::getStandard));

            User user = userRepository.findOne(record.getExecutor());

            //最终的返回结果
            SpotInspectionRecordDetailWarpResp resultResp = new SpotInspectionRecordDetailWarpResp();

            resultResp.setExecuteTime(DateTimeUtil.dateToStr(record.getExecuteTime()));
            resultResp.setExecutor(user.getUserName());
            resultResp.setExceptionHandleDesc(""); //异常处理情况 不知道是啥
            resultResp.setPlanName(plan.getName());


            //巡检记录细节
            List<SpotInspectionRecordDetailResp> detailList = new ArrayList<>();

            planDeviceList.forEach(d1 -> {

                SpotInspectionRecordDetailResp detail = new SpotInspectionRecordDetailResp();

                //格式化巡检标准名称和设备信息
                if(!CheckParam.isNull(standardMap) && !CheckParam.isNull(standardMap.get(d1.getSpotInspectionStandard()))){
                    detail.setStandardId(standardMap.get(d1.getSpotInspectionStandard()).getId());
                    detail.setStandardName(standardMap.get(d1.getSpotInspectionStandard()).getName());
                }
                if(!CheckParam.isNull(deviceInfoMap) && !CheckParam.isNull(deviceInfoMap.get(d1.getDeviceId()))){
                    detail.setDeviceCode(deviceInfoMap.get(d1.getDeviceId()).getCode());
                    detail.setDeviceName(deviceInfoMap.get(d1.getDeviceId()).getName());
                    detail.setDeviceSpecification(deviceInfoMap.get(d1.getDeviceId()).getSpecification());
                }

                detail.setDeviceId(d1.getDeviceId());
                detail.setLineOrder(d1.getLineOrder());
                detail.setPlanDeviceId(d1.getId());

                //计算漏检数量
                if(!CheckParam.isNull(itemMap.get(d1.getSpotInspectionStandard()))){

                    //拿到某个点检标准需要被执行的点检项数量
                    Long needToExecute = itemMap.get(d1.getSpotInspectionStandard()).stream().count();

                    Long executeCount = 0L;

                    if(!CheckParam.isNull(itemExecuteDetailMap.get(d1.getSpotInspectionStandard()))){
                        executeCount = itemExecuteDetailMap.get(d1.getSpotInspectionStandard()).stream().count();
                    }

                    //如果需要被检查的数量大于已经检查的数量说明具备漏检项目
                    if(needToExecute >= executeCount){
                        detail.setMissCount(Integer.valueOf(String.valueOf(needToExecute - executeCount)));
                    }else{
                        detail.setMissCount(0); //如果需要被检查的数量小于已经检查的数量说明出现数据不一致
                    }

                    //设置异常数量
                    if(!CheckParam.isNull(itemExecuteDetailMap) && !CheckParam.isNull(itemExecuteDetailMap.get(d1.getSpotInspectionStandard()))){
                        Long count = itemExecuteDetailMap.get(d1.getSpotInspectionStandard()).stream().filter(d2 -> d2.getAbnormalDesc().equals(SpotInspectionDeviceAbnormalEnums.SPOT_INSPECTION_ITEMS_ABNORMAL)).count();
                        detail.setAbnormalDeviceCount(Integer.valueOf(String.valueOf(count))); //设置设备异常数量
                    }else{
                        detail.setAbnormalDeviceCount(0);
                    }

                    //设置执行状态 1完成2未完成
                    detail.setExecuteStatus(needToExecute == executeCount ? 1 : 2);

                    detailList.add(detail);
                }else{
                    detail.setAbnormalDeviceCount(0);
                    detail.setMissCount(0);
                    detail.setMissCount(0);
                    detail.setExecuteStatus(1); //执行状态 1完成2未完成
                }

            });
            resultResp.setDetailList(detailList);
            return resultResp;

        }
        return null;
    }

    public static void main(String[] args) {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();

        user1.setId(1L);
        user2.setId(2L);
        user3.setId(3L);

        user1.setUserName("张三");
        user2.setUserName("张三");
        user3.setUserName("李四");

        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);


        Map<String, List<User>> userMap =
                userList.stream().collect(groupingBy(User::getUserName));

        System.out.println(userMap);

    }

}
