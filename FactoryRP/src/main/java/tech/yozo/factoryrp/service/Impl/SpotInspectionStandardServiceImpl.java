package tech.yozo.factoryrp.service.Impl;

import com.alibaba.fastjson.JSON;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.yozo.factoryrp.entity.DeviceInfo;
import tech.yozo.factoryrp.entity.DeviceType;
import tech.yozo.factoryrp.entity.SpotInspectionItems;
import tech.yozo.factoryrp.entity.SpotInspectionStandard;
import tech.yozo.factoryrp.enums.inspection.SpotInspectionItemsRecordTypeEnum;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.repository.DeviceInfoRepository;
import tech.yozo.factoryrp.repository.DeviceTypeRepository;
import tech.yozo.factoryrp.repository.SpotInspectionItemsRepository;
import tech.yozo.factoryrp.repository.SpotInspectionStandardRepository;
import tech.yozo.factoryrp.service.SpotInspectionStandardService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.SpotInspectionStandardAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionStandardQueryReq;
import tech.yozo.factoryrp.vo.resp.inspection.*;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

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

                    spotInspectionItems.add(spotInspectionStandardItemsQueryResp);
                });

                spotInspectionStandardDetailQueryResp.setSpotInspectionItems(spotInspectionItems);

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

                    if(!CheckParam.isNull(deviceTypeMap.get(p1.getDeviceType()))){
                        spotInspectionStandardQueryResp.setRelateDeviceName(deviceTypeMap.get(p1.getDeviceType()).getName());
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

}
