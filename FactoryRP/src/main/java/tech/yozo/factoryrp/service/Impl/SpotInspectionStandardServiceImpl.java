package tech.yozo.factoryrp.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.yozo.factoryrp.entity.*;
import tech.yozo.factoryrp.enums.inspection.SpotInspectionItemsRecordTypeEnum;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.repository.*;
import tech.yozo.factoryrp.service.SpotInspectionStandardService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.DateTimeUtil;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.innertransfer.InspectionItemTransferVo;
import tech.yozo.factoryrp.vo.req.SpotInspectionStandardAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionStandardQueryReq;
import tech.yozo.factoryrp.vo.resp.inspection.*;
import tech.yozo.factoryrp.vo.resp.inspection.mobile.SpotInspectionItemsQueryWarpResp;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.alibaba.fastjson.serializer.SerializerFeature.*;

/**
 * 巡检标准服务具体实现
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/22
 * @description
 */
@Service
@Transactional
public class SpotInspectionStandardServiceImpl implements SpotInspectionStandardService {

    @Resource
    private SpotInspectionItemsRepository spotInspectionItemsRepository;

    @Resource
    private SpotInspectionStandardRepository spotInspectionStandardRepository;

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
    private SpotInspectionImageInfoRepository spotInspectionImageInfoRepository;

    /**
     * 手机端根据巡检计划ID和设备code查询巡检项目
     * @param planId
     * @param deviceCode
     * @param corporateIdentify
     * @return
     */
    public SpotInspectionItemsQueryWarpResp queryMobileInspectionItemByPlanIdAndDeviceId(Long planId,String deviceCode,Long corporateIdentify){


        //查询巡检计划-点检标准-设备关联表

        DeviceInfo deviceInfo = deviceInfoRepository.findByCodeAndCorporateIdentify(deviceCode, corporateIdentify);

        if(!CheckParam.isNull(deviceInfo)) {

            Long deviceInfoId = deviceInfo.getId();

            SpotInspectionPlanDevice spotInspectionPlanDevice = spotInspectionPlanDeviceRepository.findByCorporateIdentifyAndDeviceIdAndSpotInspectionPlan(corporateIdentify, deviceInfoId, planId);

            if (!CheckParam.isNull(spotInspectionPlanDevice)) {

                List<SpotInspectionItems> spotInspectionItemsList = spotInspectionItemsRepository.findByStandardAndCorporateIdentify(spotInspectionPlanDevice.getSpotInspectionStandard(), corporateIdentify);

                //List<SpotInspectionRecord> recordList = spotInspectionRecordRepository.findByCorporateIdentifyAndPlanId(corporateIdentify, planId);

                if (!CheckParam.isNull(spotInspectionItemsList) && !spotInspectionItemsList.isEmpty()) {

                    List<SpotInspectionStandardItemsQueryResp> itemList = new ArrayList<>();

                    List<Long> itemIdList = new ArrayList<>();

                    spotInspectionItemsList.forEach(s1 -> {
                        itemIdList.add(s1.getId());
                    });

                    SpotInspectionPlan plan = spotInspectionPlanRepository.findOne(spotInspectionPlanDevice.getSpotInspectionPlan());


                    //计算当前时间减去周期之后的时间 注意，此处和计划的下次执行时间无关
                    //Date date = DateTimeUtil.subtractDateByParam(new Date(), plan.getRecyclePeriod(), plan.getRecyclePeriodType());
                    Date date = DateTimeUtil.subtractDateByParam(new Date(), plan.getRecyclePeriod(), plan.getRecyclePeriodType());

                    Map<Long, Integer> executeResultMap = inspectionExecuteResult(corporateIdentify, date, itemIdList);


                    spotInspectionItemsList.stream().forEach(s1 -> {
                        SpotInspectionStandardItemsQueryResp spotInspectionStandardItemsQueryResp = new SpotInspectionStandardItemsQueryResp();

                        spotInspectionStandardItemsQueryResp.setUpperLimit(s1.getUpperLimit());
                        spotInspectionStandardItemsQueryResp.setLowerLimit(s1.getLowerLimit());
                        spotInspectionStandardItemsQueryResp.setInputLimitValue(JSON.parseArray(s1.getVaildateRegular(), String.class));
                        spotInspectionStandardItemsQueryResp.setName(s1.getName());
                        spotInspectionStandardItemsQueryResp.setRecordTypeName(s1.getRecordType());
                        spotInspectionStandardItemsQueryResp.setItemId(s1.getId());

                        //该计划下面没有过巡检记录表示没有进行过巡检
                       /* if(CheckParam.isNull(recordList) || recordList.isEmpty()){
                            spotInspectionStandardItemsQueryResp.setInspectionStatus(2);
                        }*/



                        //设置在规定巡检周期内，是否执行过，1执行2未执行
                        if(!CheckParam.isNull(executeResultMap.get(s1.getId()))){
                            spotInspectionStandardItemsQueryResp.setInspectionStatus(executeResultMap.get(s1.getId()));
                        }else{
                            spotInspectionStandardItemsQueryResp.setInspectionStatus(2);
                        }

                        itemList.add(spotInspectionStandardItemsQueryResp);

                    });
                    SpotInspectionItemsQueryWarpResp spotInspectionItemsQueryWarpResp = new SpotInspectionItemsQueryWarpResp();

                    spotInspectionItemsQueryWarpResp.setSpotInspectionStandard(spotInspectionPlanDevice.getSpotInspectionStandard());
                    spotInspectionItemsQueryWarpResp.setDeviceId(spotInspectionPlanDevice.getDeviceId());
                    spotInspectionItemsQueryWarpResp.setPlanId(spotInspectionPlanDevice.getSpotInspectionPlan());
                    spotInspectionItemsQueryWarpResp.setDeviceName(deviceInfo.getName());
                    spotInspectionItemsQueryWarpResp.setItemList(itemList);

                    return spotInspectionItemsQueryWarpResp;

                }

            }

        }
                throw new BussinessException(ErrorCode.NO_DEVICEINFO_ERROR.getCode(),ErrorCode.NO_DEVICEINFO_ERROR.getMessage());
    }


    /**
     * 返回执行结果 是否执行了提交 1执行2未执行
     * @param corporateIdentify
     * @param compareTime
     * @param itemIdList
     * @return
     */
    public Map<Long,Integer> inspectionExecuteResult(Long corporateIdentify,Date compareTime, List<Long> itemIdList){

        //查询出规定周期内执行过的记录
        List<SpotInspectionRecordDetail> detailList = spotInspectionRecordDetailRepository.findByCorporateIdentifyAndStandardItemIdInAndCreateTimeGreaterThan(corporateIdentify, itemIdList, compareTime);

        Map<Long,Integer> resultMap = new HashMap<>();


        //如果查询出来为空全部设置为未执行
        if(CheckParam.isNull(detailList) && detailList.isEmpty()){
            itemIdList.stream().forEach(m1 -> {
                resultMap.put(m1,2);
            });

            return resultMap;
        }


        detailList.stream().forEach(d1 -> {

            //如果能比对到说明执行过，比对不到的就说明未执行过
            if(itemIdList.contains(d1.getStandardItemId())){
                resultMap.put(d1.getStandardItemId(),1);
            }else{
                resultMap.put(d1.getStandardItemId(),2);
            }

        });

        return resultMap;

    }

    /**
     * 返回执行结果的内部对象
     * @param corporateIdentify
     * @param itemIdList
     * @param recordId
     * @return
     */
    public Map<Long,InspectionItemTransferVo> queryInnerInspectionExecuteResult(Long corporateIdentify,List<Long> itemIdList,Long recordId){

        //查询出规定周期内执行过的记录
        //List<SpotInspectionRecordDetail> detailList = spotInspectionRecordDetailRepository.findByCorporateIdentifyAndStandardItemIdInAndCreateTimeGreaterThan(corporateIdentify, itemIdList, compareTime);
        List<SpotInspectionRecordDetail> detailList = spotInspectionRecordDetailRepository.findByCorporateIdentifyAndStandardItemIdInAndRecordId(corporateIdentify,itemIdList,recordId);

        Map<Long,InspectionItemTransferVo> resultMap = new HashMap<>();

        //如果查询出来为空全部设置为未执行
        if(CheckParam.isNull(detailList) && detailList.isEmpty()){
            itemIdList.stream().forEach(m1 -> {
                InspectionItemTransferVo transferVo = new InspectionItemTransferVo();
                transferVo.setExecuteStatus(2);
                resultMap.put(m1,transferVo);
            });

            return resultMap;
        }


        detailList.stream().forEach(d1 -> {
            InspectionItemTransferVo transferVo = new InspectionItemTransferVo();
            //如果能比对到说明执行过，比对不到的就说明未执行过
            if(itemIdList.contains(d1.getStandardItemId())){
                transferVo.setExecuteStatus(1);
                transferVo.setAbnormalDesc(d1.getAbnormalDesc());
                transferVo.setRecordResult(d1.getRecordResult());
                transferVo.setRemark(d1.getRemark());
                transferVo.setExecuteDetailId(d1.getId());
                resultMap.put(d1.getStandardItemId(),transferVo);
            }else{
                transferVo.setExecuteStatus(2);

                resultMap.put(d1.getStandardItemId(),transferVo);
            }

        });

        return resultMap;

    }

    /**
     * 根据设备ID查询点检标准
     * @param deviceId
     * @param corporateIdentify
     * @return
     */
    public List<SpotInspectionStandardQueryResp> queryStandardByDeviceId(Long deviceId, Long corporateIdentify){


            DeviceInfo deviceInfo = deviceInfoRepository.findOne(deviceId);


            if(!CheckParam.isNull(deviceInfo)){

                Long deviceType = deviceInfo.getDeviceType();

                List<SpotInspectionStandard> spotInspectionStandardList = spotInspectionStandardRepository.findByDeviceTypeAndCorporateIdentify(deviceType, corporateIdentify);

                if(!CheckParam.isNull(spotInspectionStandardList) && !spotInspectionStandardList.isEmpty()){

                    //排除不需要的数据
                    spotInspectionStandardList = spotInspectionStandardList.stream().filter(d1 ->
                            JSON.parseArray(d1.getRelateDevices(),Long.class).contains(deviceId)).collect(Collectors.toList());

                    List<SpotInspectionStandardQueryResp> respList = new ArrayList<>();

                    DeviceType type = deviceTypeRepository.findOne(deviceInfo.getDeviceType());

                    spotInspectionStandardList.stream().forEach(d1 -> {
                        SpotInspectionStandardQueryResp spotInspectionStandardQueryResp = new SpotInspectionStandardQueryResp();

                        spotInspectionStandardQueryResp.setId(d1.getId());
                        spotInspectionStandardQueryResp.setName(d1.getName());
                        spotInspectionStandardQueryResp.setRelateDeviceTypeName(CheckParam.isNull(type) ? null : type.getName());
                        spotInspectionStandardQueryResp.setRelateDevices(JSON.parseArray(d1.getRelateDevices(),Long.class));
                        spotInspectionStandardQueryResp.setRemark(d1.getRemark());
                        spotInspectionStandardQueryResp.setRequirement(d1.getRequirement());
                        respList.add(spotInspectionStandardQueryResp);
                    });

                    return respList;
                }

            }

            return null;
        }


    /**
     * 批量删除点检标准
     * @param ids
     * @param corporateIdentify
     */
    public void deleteSpotInspectionStandardByIds(List<Long> ids,Long corporateIdentify){

        List<SpotInspectionStandard> spotInspectionStandardList = spotInspectionStandardRepository.findByCorporateIdentifyAndIdIn(corporateIdentify, ids);

        if(!CheckParam.isNull(spotInspectionStandardList) && !spotInspectionStandardList.isEmpty()){

            List<Long> standardIds = new ArrayList<>();

            spotInspectionStandardList.stream().forEach(s1 -> {
                standardIds.add(s1.getId());
            });

            List<SpotInspectionItems> itemsList = spotInspectionItemsRepository.findByStandardIdsInAndCorporateIdentify(standardIds, corporateIdentify);

            if(!CheckParam.isNull(itemsList) && !itemsList.isEmpty()){
                spotInspectionItemsRepository.deleteInBatch(itemsList);
            }

            spotInspectionStandardRepository.deleteInBatch(spotInspectionStandardList);

        }

    }



    /**
     * 点检标准新增方法
     * @param spotInspectionStandardAddReq
     * @param corporateIdentify
     * @return
     */
    @Override
    public SpotInspectionStandardAddResp addSpotInspectionStandard(SpotInspectionStandardAddReq spotInspectionStandardAddReq, Long corporateIdentify) {

        SpotInspectionStandard spotInspectionStandard = spotInspectionStandardRepository.findByNameAndCorporateIdentify(spotInspectionStandardAddReq.getName(), corporateIdentify);

        if(!CheckParam.isNull(spotInspectionStandard)){
            throw new BussinessException(ErrorCode.SPOTINSPECTIONSTANDARD_REPET_ERROR.getCode(),
                    ErrorCode.SPOTINSPECTIONSTANDARD_REPET_ERROR.getMessage());
        }

        spotInspectionStandard = new SpotInspectionStandard();

        spotInspectionStandard.setName(spotInspectionStandardAddReq.getName());
        spotInspectionStandard.setDeviceType(spotInspectionStandardAddReq.getDeviceType());

        List<DeviceInfo> deviceInfoList = deviceInfoRepository.findByDeviceTypeAndCorporateIdentify(spotInspectionStandardAddReq.getDeviceType(), corporateIdentify);

        //设置关联设备ID列表
        if(!CheckParam.isNull(deviceInfoList) && !deviceInfoList.isEmpty()){
            List<Long> deviceIdList = new ArrayList<>();
            deviceInfoList.stream().forEach(d1 -> {
                deviceIdList.add(d1.getId());
            });

            spotInspectionStandard.setRelateDevices(JSON.toJSONString(deviceIdList));
        }

        spotInspectionStandard.setRequirement(spotInspectionStandardAddReq.getRequirement());
        spotInspectionStandard.setCorporateIdentify(corporateIdentify);
        spotInspectionStandard.setRemark(spotInspectionStandardAddReq.getRemark());

        spotInspectionStandardRepository.save(spotInspectionStandard);

        //巡检项目ID
        Long standardId = spotInspectionStandard.getId();

        //需要设置点检项目详情
        if(!CheckParam.isNull(spotInspectionStandardAddReq.getSpotInspectionItems()) && !spotInspectionStandardAddReq.getSpotInspectionItems().isEmpty()){
            List<SpotInspectionItems> spotInspectionItemsList = new ArrayList<>();

            spotInspectionStandardAddReq.getSpotInspectionItems().stream().forEach(s1 -> {
                SpotInspectionItems spotInspectionItems = new SpotInspectionItems();
                spotInspectionItems.setName(s1.getName());
                spotInspectionItems.setRecordType(s1.getRecordType());
                spotInspectionItems.setStandard(standardId);
                spotInspectionItems.setCorporateIdentify(corporateIdentify);
                spotInspectionItems.setLowerLimit(s1.getLowerLimit());
                spotInspectionItems.setUpperLimit(s1.getUpperLimit());
                spotInspectionItems.setSpotInspectionWay(s1.getSpotInspectionWay());
                spotInspectionItems.setDevicePlace(s1.getDevicePlace());

                //文字的处理逻辑 没有规定填写哪些文字，所以允许空
                if(!SpotInspectionItemsRecordTypeEnum.SPOT_INSPECTION_ITEMS_RECORD_TYPE_ENUM_VERBAL_DESCRIPTION.getCode().equals(s1.getRecordType())){
                    String limitStr = JSON.toJSONString(s1.getInputLimitValue());
                    spotInspectionItems.setVaildateRegular(limitStr);
                }else{
                    spotInspectionItems.setVaildateRegular(null);
                }

                spotInspectionItemsList.add(spotInspectionItems);
            });

            spotInspectionItemsRepository.save(spotInspectionItemsList);

        }

        SpotInspectionStandardAddResp spotInspectionStandardAddResp = new SpotInspectionStandardAddResp();
        spotInspectionStandardAddResp.setId(standardId);
        spotInspectionStandardAddResp.setName(spotInspectionStandardAddReq.getName());

        return spotInspectionStandardAddResp;
    }


    /**
     * 查询手机端巡检项目详情
     * @param standardId
     * @param corporateIdentify
     * @return
     */

    /**
     * 查询点巡检标准详情
     * @param standardId
     * @param corporateIdentify
     * @return
     */
    public SpotInspectionStandardDetailQueryResp queryInspectionStandardDetail(Long standardId, Long corporateIdentify){


        SpotInspectionStandard spotInspectionStandard = spotInspectionStandardRepository.findOne(standardId);

        if(!CheckParam.isNull(spotInspectionStandard)){
            SpotInspectionStandardDetailQueryResp spotInspectionStandardDetailQueryResp = new SpotInspectionStandardDetailQueryResp();

            spotInspectionStandardDetailQueryResp.setDeviceType(spotInspectionStandard.getDeviceType());
            spotInspectionStandardDetailQueryResp.setRemark(spotInspectionStandard.getRemark());
            spotInspectionStandardDetailQueryResp.setName(spotInspectionStandard.getName());
            spotInspectionStandardDetailQueryResp.setRequirement(spotInspectionStandard.getRequirement());

            DeviceType deviceType = deviceTypeRepository.findOne(spotInspectionStandard.getDeviceType());
            if(!CheckParam.isNull(deviceType)){
                spotInspectionStandardDetailQueryResp.setDeviceTypeName(deviceType.getName());
            }

            List<SpotInspectionItems> spotInspectionItemsList = spotInspectionItemsRepository.findByStandardAndCorporateIdentify(standardId, corporateIdentify);

            if(!CheckParam.isNull(spotInspectionItemsList) && !spotInspectionItemsList.isEmpty()){

                List<SpotInspectionStandardItemsQueryResp> spotInspectionItems = new ArrayList<>();

                spotInspectionItemsList.stream().forEach(s1 -> {
                    SpotInspectionStandardItemsQueryResp spotInspectionStandardItemsQueryResp = new SpotInspectionStandardItemsQueryResp();
                    spotInspectionStandardItemsQueryResp.setName(s1.getName());
                    if(!CheckParam.isNull(SpotInspectionItemsRecordTypeEnum.getByCode(s1.getRecordType()))){
                        spotInspectionStandardItemsQueryResp.setRecordTypeName(SpotInspectionItemsRecordTypeEnum.getByCode(s1.getRecordType()).getName());
                    }

                    //如果是文字类型的就特殊处理
                    if(SpotInspectionItemsRecordTypeEnum.SPOT_INSPECTION_ITEMS_RECORD_TYPE_ENUM_VERBAL_DESCRIPTION.getCode().equals(s1.getRecordType())){
                        spotInspectionStandardItemsQueryResp.setInputLimitValue(null);
                    }else{
                        spotInspectionStandardItemsQueryResp.setInputLimitValue(JSON.parseArray(s1.getVaildateRegular(),String.class));
                    }

                    spotInspectionStandardItemsQueryResp.setLowerLimit(s1.getLowerLimit());
                    spotInspectionStandardItemsQueryResp.setUpperLimit(s1.getUpperLimit());
                    spotInspectionStandardItemsQueryResp.setSpotInspectionWay(s1.getSpotInspectionWay());
                    spotInspectionStandardItemsQueryResp.setDevicePlace(s1.getDevicePlace());
                    spotInspectionItems.add(spotInspectionStandardItemsQueryResp);
                });

                spotInspectionStandardDetailQueryResp.setSpotInspectionItems(spotInspectionItems);

            }

            List<Long> deviceInfoIds = JSON.parseArray(spotInspectionStandard.getRelateDevices(),Long.class);


            if(!CheckParam.isNull(deviceInfoIds) && !deviceInfoIds.isEmpty()){
                List<DeviceInfo> deviceInfoList = deviceInfoRepository.findByIdsIn(deviceInfoIds);

                List<SpotInspectionStandardDeviceInfo> standardDeviceInfoList = new ArrayList<>();


                deviceInfoList.stream().forEach(d1 -> {
                    SpotInspectionStandardDeviceInfo spotInspectionStandardDeviceInfo =  new SpotInspectionStandardDeviceInfo();

                    spotInspectionStandardDeviceInfo.setId(d1.getId());
                    spotInspectionStandardDeviceInfo.setName(d1.getName());
                    spotInspectionStandardDeviceInfo.setCode(d1.getCode());
                    spotInspectionStandardDeviceInfo.setSpecification(d1.getSpecification());

                    standardDeviceInfoList.add(spotInspectionStandardDeviceInfo);
                });

                spotInspectionStandardDetailQueryResp.setDeviceInfoList(standardDeviceInfoList);

            }

            return spotInspectionStandardDetailQueryResp;
        }


        return null;
    }

    /**
     * 删除巡检标准
     * @param standardId
     * @param corporateIdentify
     */
    @Override
    public void deleteInspectionStandard(Long standardId,Long corporateIdentify) {
        SpotInspectionStandard inspectionStandard = spotInspectionStandardRepository.findOne(standardId);

        if(!CheckParam.isNull(inspectionStandard)){
            spotInspectionStandardRepository.delete(inspectionStandard);
            List<SpotInspectionItems> spotInspectionItemsList = spotInspectionItemsRepository.findByStandardAndCorporateIdentify(inspectionStandard.getId(), corporateIdentify);

            if(!CheckParam.isNull(spotInspectionItemsList) && !spotInspectionItemsList.isEmpty()){
                spotInspectionItemsRepository.deleteInBatch(spotInspectionItemsList);
            }
        }

    }


    /**
     * 点检标准分页查询
     * @param spotInspectionStandardQueryReq
     * @param corporateIdentify
     * @return
     */
    public SpotInspectionStandardQueryWarpResp findByPage(SpotInspectionStandardQueryReq spotInspectionStandardQueryReq, Long corporateIdentify){

        if (spotInspectionStandardQueryReq.getCurrentPage() > 0) {
            spotInspectionStandardQueryReq.setCurrentPage(spotInspectionStandardQueryReq.getCurrentPage()-1);
        }

        Pageable p = new PageRequest(spotInspectionStandardQueryReq.getCurrentPage(), spotInspectionStandardQueryReq.getItemsPerPage());

        Page<SpotInspectionStandard> page = spotInspectionStandardRepository.findAll((Root<SpotInspectionStandard> root,
                                                                    CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {

            List<Predicate> list = new ArrayList<>();

            if (!CheckParam.isNull(spotInspectionStandardQueryReq.getId())) { //主键
                list.add(criteriaBuilder.equal(root.get("id").as(Long.class), spotInspectionStandardQueryReq.getId()));
            }
            if (!CheckParam.isNull(spotInspectionStandardQueryReq.getName())) { //巡检标准名称
                list.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+spotInspectionStandardQueryReq.getName()+"%"));
            }
            if (!CheckParam.isNull(spotInspectionStandardQueryReq.getRemark())) { //备注
                list.add(criteriaBuilder.like(root.get("remark").as(String.class), "%"+spotInspectionStandardQueryReq.getRemark()+"%"));
            }
            if (!CheckParam.isNull(spotInspectionStandardQueryReq.getRelateDeviceType())) { //适用设备类型
                list.add(criteriaBuilder.equal(root.get("deviceType").as(Long.class), spotInspectionStandardQueryReq.getRelateDeviceType()));
            }
            if (!CheckParam.isNull(spotInspectionStandardQueryReq.getRequirement())) { //巡检要求
                list.add(criteriaBuilder.like(root.get("requirement").as(String.class), "%"+spotInspectionStandardQueryReq.getRequirement()+"%"));
            }

            list.add(criteriaBuilder.equal(root.get("corporateIdentify").as(Long.class), corporateIdentify));

            Predicate[] predicates = new Predicate[list.size()];
            predicates = list.toArray(predicates);
            return criteriaBuilder.and(predicates);
        }, p);

        if (page.hasContent()){
            List<SpotInspectionStandardQueryResp> respList = new ArrayList<>();

            //格式化设备类型名称
            List<Long> deviceTypeIds = new ArrayList<>();

            page.getContent().stream().forEach(p1 -> {
                deviceTypeIds.add(p1.getDeviceType());
            });

            if(!CheckParam.isNull(deviceTypeIds) && !deviceTypeIds.isEmpty()){
                List<DeviceType> deviceTypeList = deviceTypeRepository.findAll(deviceTypeIds);

                //形成以设备类型ID为键的Map
                Map<Long, DeviceType> deviceTypeMap = deviceTypeList.stream().collect(Collectors.toMap(DeviceType::getId, Function.identity()));

                page.getContent().stream().forEach(p1 -> {
                    SpotInspectionStandardQueryResp spotInspectionStandardQueryResp = new SpotInspectionStandardQueryResp();

                    spotInspectionStandardQueryResp.setId(p1.getId());
                    spotInspectionStandardQueryResp.setName(p1.getName());
                    spotInspectionStandardQueryResp.setRemark(p1.getRemark());


                    //适用设备类型名称
                    if(!CheckParam.isNull(deviceTypeMap.get(p1.getDeviceType()))){
                        spotInspectionStandardQueryResp.setRelateDeviceTypeName(deviceTypeMap.get(p1.getDeviceType()).getName());
                    }
                    spotInspectionStandardQueryResp.setRelateDevices(JSON.parseArray(p1.getRelateDevices(),Long.class));
                    spotInspectionStandardQueryResp.setRequirement(p1.getRequirement());

                    respList.add(spotInspectionStandardQueryResp);
                });

                SpotInspectionStandardQueryWarpResp spotInspectionStandardQueryWarpResp = new SpotInspectionStandardQueryWarpResp();

                spotInspectionStandardQueryWarpResp.setCurrentPage(spotInspectionStandardQueryReq.getCurrentPage());
                spotInspectionStandardQueryWarpResp.setItemsPerPage(spotInspectionStandardQueryReq.getItemsPerPage());
                spotInspectionStandardQueryWarpResp.setTotalCount(page.getTotalElements());
                spotInspectionStandardQueryWarpResp.setList(respList);

                return spotInspectionStandardQueryWarpResp;

            }

        }

        return null;

    }


    /**
     * 通过巡检标准ID进行查询
     * @param standardId
     * @param planId
     * @param corporateIdentify
     * @return
     */
    public List<SpotInspectionStandardItemsQueryResp> findSpotInspectionStandardItemByStandardIdAndPlanId(Long standardId, Long planId, Long corporateIdentify) {

        SpotInspectionPlan plan = spotInspectionPlanRepository.findOne(planId);

        if (CheckParam.isNull(plan)) {
            throw new BussinessException(ErrorCode.NO_SPOTINSPECTIONPLAN__EXIST_ERROR.getCode(),
                    ErrorCode.NO_SPOTINSPECTIONPLAN__EXIST_ERROR.getMessage());
        }

        List<SpotInspectionItems> spotInspectionItemsList = spotInspectionItemsRepository.findByStandardAndCorporateIdentify(standardId, corporateIdentify);

        if (!CheckParam.isNull(spotInspectionItemsList) && !spotInspectionItemsList.isEmpty()) {

            List<SpotInspectionStandardItemsQueryResp> itemList = new ArrayList<>();

            List<Long> itemIdList = new ArrayList<>();

            spotInspectionItemsList.forEach(s1 -> {
                itemIdList.add(s1.getId());
            });

            Date currentDate = new Date();


            //计算当前时间减去周期之后的时间 注意，此处和计划的下次执行时间无关
            Date date = DateTimeUtil.subtractDateByParam(currentDate, plan.getRecyclePeriod(), plan.getRecyclePeriodType());

            //巡检记录创建时间 + 周期 > 当前时间  说明在周期内产生了巡检记录 查找周期内执行巡检用户填写的值
            //如果能查询出来表示在周期内执行过
            SpotInspectionRecord record = spotInspectionRecordRepository.findByCorporateIdentifyAndPlanIdAndCreateTimeGreaterThan(corporateIdentify, plan.getId(), date);

            Map<Long, InspectionItemTransferVo> executeResultMap = null;

            //执行过才查找巡检细节
            if(!CheckParam.isNull(record)){
                executeResultMap = queryInnerInspectionExecuteResult(corporateIdentify,itemIdList,record.getId());
            }

            for (SpotInspectionItems item: spotInspectionItemsList) {
                SpotInspectionStandardItemsQueryResp spotInspectionStandardItemsQueryResp = new SpotInspectionStandardItemsQueryResp();

                spotInspectionStandardItemsQueryResp.setUpperLimit(item.getUpperLimit());
                spotInspectionStandardItemsQueryResp.setLowerLimit(item.getLowerLimit());
                spotInspectionStandardItemsQueryResp.setInputLimitValue(JSON.parseArray(item.getVaildateRegular(), String.class));
                spotInspectionStandardItemsQueryResp.setName(item.getName());
                spotInspectionStandardItemsQueryResp.setRecordTypeName(item.getRecordType());
                spotInspectionStandardItemsQueryResp.setItemId(item.getId());


                //设置在规定巡检周期内，是否执行过，1执行2未执行
                if (!CheckParam.isNull(executeResultMap) && !CheckParam.isNull(executeResultMap.get(item.getId()))) {
                    //inspectionStatus值 是否执行了提交 1执行2未执行
                    spotInspectionStandardItemsQueryResp.setInspectionStatus(executeResultMap.get(item.getId()).getExecuteStatus());
                    spotInspectionStandardItemsQueryResp.setAbnormalDesc(executeResultMap.get(item.getId()).getAbnormalDesc());
                    spotInspectionStandardItemsQueryResp.setRecordResult(executeResultMap.get(item.getId()).getRecordResult());
                    spotInspectionStandardItemsQueryResp.setRemark(executeResultMap.get(item.getId()).getRemark());
                    spotInspectionStandardItemsQueryResp.setExecuteDetailId(executeResultMap.get(item.getId()).getExecuteDetailId());
                } else {
                    spotInspectionStandardItemsQueryResp.setInspectionStatus(2);
                }

                itemList.add(spotInspectionStandardItemsQueryResp);
            }

            return itemList;
        }
            return null;
    }
}
