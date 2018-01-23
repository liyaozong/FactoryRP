package tech.yozo.factoryrp.service.Impl;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.DeviceInfo;
import tech.yozo.factoryrp.entity.DeviceProcessDetail;
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
import tech.yozo.factoryrp.vo.req.SpotInspectionItemsAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionStandardAddReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionStandardAddResp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 巡检标准服务具体实现
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/22
 * @description
 */
@Service
public class SpotInspectionStandardServiceImpl implements SpotInspectionStandardService {

    @Resource
    private SpotInspectionItemsRepository spotInspectionItemsRepository;

    @Resource
    private SpotInspectionStandardRepository spotInspectionStandardRepository;

    @Resource
    private DeviceInfoRepository deviceInfoRepository;

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

        List<DeviceInfo> deviceInfoList = deviceInfoRepository.findByCorporateIdentifyAndDeviceType(spotInspectionStandardAddReq.getDeviceType(), corporateIdentify);

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
        spotInspectionStandard.setRemark(spotInspectionStandard.getRemark());

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


}
