package tech.yozo.factoryrp.service.Impl;


import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.yozo.factoryrp.entity.*;
import tech.yozo.factoryrp.enums.inspection.SpotInspectionDeviceAbnormalEnums;
import tech.yozo.factoryrp.enums.inspection.SpotInspectionPlanRecycleTypeEnum;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.repository.*;
import tech.yozo.factoryrp.service.SpotInspectionRecordService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.DateTimeUtil;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.innertransfer.InspectionRecordTimeQueryTransferVo;
import tech.yozo.factoryrp.vo.req.SpotInspectionRecordAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionRecordMobileAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionRecordPageQueryReq;
import tech.yozo.factoryrp.vo.resp.auth.AuthUserMenu;
import tech.yozo.factoryrp.vo.resp.inspection.*;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;
import java.util.*;
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
        record.setDepartment(plan.getDepartment());

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
                spotInspectionRecordResp.setPlanTime(DateTimeUtil.dateToStr(plan.getNextExecuteTime())); //计划时间设置为下次执行时间
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

    /**
     * 巡检记录分页查询
     * @param spotInspectionRecordPageQueryReq
     * @return
     */
    public Pagination<SpotInspectionRecordPageQueryResp> findByPage(SpotInspectionRecordPageQueryReq spotInspectionRecordPageQueryReq,Long corporateIdentify){


        if (spotInspectionRecordPageQueryReq.getCurrentPage() > 0) {
            spotInspectionRecordPageQueryReq.setCurrentPage(spotInspectionRecordPageQueryReq.getCurrentPage()-1);
        }

        Pageable p = new PageRequest(spotInspectionRecordPageQueryReq.getCurrentPage(), spotInspectionRecordPageQueryReq.getItemsPerPage());

        Page<SpotInspectionRecord> page = spotInspectionRecordRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {

            InspectionRecordTimeQueryTransferVo transferVo = null;

            if(!CheckParam.isNull(spotInspectionRecordPageQueryReq.getExecuteTimeCondition())){
                transferVo = tranaferRecordQueryTime(spotInspectionRecordPageQueryReq.getExecuteTimeCondition());
            }

            List<Predicate> listCon = new ArrayList<>();

            if (!CheckParam.isNull(spotInspectionRecordPageQueryReq.getDepartmentId())){ //部门查询条件
                listCon.add(criteriaBuilder.equal(root.get("department").as(Long.class),spotInspectionRecordPageQueryReq.getDepartmentId()));
            }
            if (!CheckParam.isNull(transferVo) && !CheckParam.isNull(transferVo.getBeginTime())){ //开始时间查询条件
                listCon.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createTime").as(Date.class),transferVo.getBeginTime()));
            }
            if (!CheckParam.isNull(transferVo) && !CheckParam.isNull(transferVo.getEndTime())){ //结束时间查询条件
                listCon.add(criteriaBuilder.lessThanOrEqualTo(root.get("createTime").as(Date.class),transferVo.getEndTime()));
            }
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
            Predicate[] predicates = new Predicate[listCon.size()];
            predicates = listCon.toArray(predicates);
            return criteriaBuilder.and(predicates);
        },p);


        if(page.hasContent()){

            List<SpotInspectionRecordPageQueryResp> respList = new ArrayList<>();

            List<SpotInspectionRecord> recordContent = page.getContent();


            List<Long> recordIds = new ArrayList<>();
            List<Long> planIds = new ArrayList<>();
            recordContent.stream().forEach(c1 -> {
                recordIds.add(c1.getId());
                planIds.add(c1.getPlanId());
            });

            Map<Long, List<SpotInspectionRecordDetail>> recordsDetailsMap = null;
            Map<Long, List<SpotInspectionItems>> itemMap = null;

            List<SpotInspectionItems> standardItemList = null;

            //巡检细节集合 根据巡检记录ID进行IN查询
            List<SpotInspectionRecordDetail> detailList = spotInspectionRecordDetailRepository.findByCorporateIdentifyAndRecordIdIn(corporateIdentify,recordIds);

            //根据巡检记录ID分组
            if(!CheckParam.isNull(detailList) && !detailList.isEmpty()){

                //用于巡检标准查询的集合，方便对比出漏检数量
                List<Long> planStandardList = new ArrayList<>();
                detailList.stream().forEach(d1 -> {
                    planStandardList.add(d1.getStandard());
                });
                recordsDetailsMap = detailList.stream().collect(Collectors.groupingBy(SpotInspectionRecordDetail::getRecordId));

                standardItemList = spotInspectionItemsRepository.findByCorporateIdentifyAndStandardIn(corporateIdentify, planStandardList.stream().distinct().collect(Collectors.toList()));

                //按照巡检标准ID进行分组 形成以巡检标准为key,巡检项目为value的map
                if(!CheckParam.isNull(standardItemList) && !standardItemList.isEmpty()){
                    itemMap = standardItemList.stream().collect(groupingBy(SpotInspectionItems::getStandard));
                }

            }

            for (SpotInspectionRecord record: recordContent) {

                SpotInspectionRecordPageQueryResp resp = new SpotInspectionRecordPageQueryResp();


                resp.setLastExecuteTime(DateTimeUtil.dateToStr(record.getExecuteTime()));
                resp.setDepartmentId(record.getDepartment());
                resp.setPlanTime(DateTimeUtil.dateToStr(record.getPlanTime()));
                resp.setPlanName(record.getPlanName());
                resp.setRecordId(record.getId());
                resp.setRecyclePeriod(SpotInspectionPlanRecycleTypeEnum.handlerRecycleTimer(record.getRecyclePeriod(),record.getRecyclePeriodType()));
                resp.setExecutor(record.getExecutor());

                resp.setAbnormalHandelDesc(""); //异常处理情况 返回空

                //计算异常数量
                if(!CheckParam.isNull(recordsDetailsMap) && !CheckParam.isNull(recordsDetailsMap.get(record.getId()))){
                    //计算异常数
                    Long count = recordsDetailsMap.get(record.getId()).stream().filter(d1 -> d1.getRecordId() == record.getId() && !CheckParam.isNull(d1.getAbnormalDesc()) && d1.getAbnormalDesc().equals(SpotInspectionDeviceAbnormalEnums.SPOT_INSPECTION_ITEMS_ABNORMAL)).count();
                    resp.setAbnormalDeviceCount(Integer.parseInt(String.valueOf(count)));
                }


                //计算漏检数量
                if(!CheckParam.isNull(recordsDetailsMap) && !CheckParam.isNull(recordsDetailsMap.get(record.getId()))){

                    List<SpotInspectionRecordDetail> spotInspectionRecordDetails = recordsDetailsMap.get(record.getId());


                    //统计某个巡检记录下实际执行了多少巡检项
                    Integer executeCount = Integer.valueOf(String.valueOf(spotInspectionRecordDetails.stream().filter(d1 -> d1.getRecordId() == record.getId()).count()));

                    //拿到某个点检标准需要被执行的点检项数量
                    Integer needToExecute = 0;

                    //这里面的巡检记录详情全都是属于这个巡检记录的详情
                    List<SpotInspectionRecordDetail> details = recordsDetailsMap.get(record.getId());

                    //需要根据巡检标准ID去重
                    details = details.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(SpotInspectionRecordDetail::getId))), ArrayList::new));

                    List<Integer> countItems = new ArrayList<>();


                    //拿到需要被执行的巡检项目集合
                    for (SpotInspectionRecordDetail detail : details) {
                        if(!CheckParam.isNull(itemMap) && !CheckParam.isNull(itemMap.get(detail.getStandard()))){
                            countItems.add(itemMap.get(detail.getStandard()).size());
                        }
                    }

                    //算出需要被执行的巡检项数量
                    if(!countItems.isEmpty()){
                        needToExecute = countItems.stream().reduce(0,
                                Integer::sum);
                    }


                    resp.setMissCount(needToExecute - executeCount);
                    resp.setDelayDesc(""); //延迟信息暂时不返回
                }else {
                    resp.setMissCount(0);
                    resp.setDelayDesc(""); //延迟信息暂时不返回
                    resp.setAbnormalDeviceCount(0);
                }
                respList.add(resp);
            }
            Pagination<SpotInspectionRecordPageQueryResp> res = new Pagination<>();
            res.setList(respList);

            res.setCurrentPage(spotInspectionRecordPageQueryReq.getCurrentPage());
            res.setItemsPerPage(spotInspectionRecordPageQueryReq.getItemsPerPage());
            res.setTotalCount(Integer.valueOf(String.valueOf(page.getTotalElements())));
            return res;
        }

        return null;
    }


    /**
     * 执行时间查询条件 1 本周记录 2上周记录 3本月记录 4上月记录 5本年记录 6上年记录 7更早记录
     * @param executeTimeCondition
     * @return
     */
    private InspectionRecordTimeQueryTransferVo tranaferRecordQueryTime(Integer executeTimeCondition){

        InspectionRecordTimeQueryTransferVo transferVo = new InspectionRecordTimeQueryTransferVo();


        if(1 == executeTimeCondition){
            transferVo.setBeginTime(DateTimeUtil.getTimesCurrentWeekBegin());
            transferVo.setEndTime(DateTimeUtil.getTimesCurrentWeekEnd());
        }else if(2 == executeTimeCondition){
            transferVo.setBeginTime(DateTimeUtil.getTimePreviousWeekBegin());
            transferVo.setEndTime(DateTimeUtil.getTimePreviousWeekEnd());
        }else if(3 == executeTimeCondition){
            transferVo.setBeginTime(DateTimeUtil.getTimesCurrentMonthBegin());
            transferVo.setEndTime(DateTimeUtil.getTimesCurrentMonthEnd());
        }else if(4 == executeTimeCondition){
            transferVo.setBeginTime(DateTimeUtil.getTimesPreviousMonthBegin());
            transferVo.setEndTime(DateTimeUtil.getTimesPreviousMonthEnd());
        }else if(5 == executeTimeCondition){
            transferVo.setBeginTime(DateTimeUtil.getTimesCurrentYearBegin());
            transferVo.setEndTime(DateTimeUtil.getTimesCurrentYearEnd());
        }else if(6 == executeTimeCondition){
            transferVo.setBeginTime(DateTimeUtil.getTimesPreviousYearBegin());
            transferVo.setEndTime(DateTimeUtil.getTimesPreviousYearEnd());
        }else if(7 == executeTimeCondition){
            //对于更早记录 即为大于现在时间的记录
            transferVo.setBeginTime(new Date());
            transferVo.setEndTime(null);
        }

        return transferVo;
    }


}
