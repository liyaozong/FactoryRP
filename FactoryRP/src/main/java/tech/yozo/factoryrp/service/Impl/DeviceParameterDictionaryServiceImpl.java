package tech.yozo.factoryrp.service.Impl;

import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.DeviceParameterDictionary;
import tech.yozo.factoryrp.enums.device.DeviceParamDicEnum;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.repository.DeviceParameterDictionaryRepository;
import tech.yozo.factoryrp.service.DeviceParameterDictionaryService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.DeviceParameterDicBatchAddReq;
import tech.yozo.factoryrp.vo.req.DeviceParameterDicReq;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/11
 * @description
 */
@Service
public class DeviceParameterDictionaryServiceImpl implements DeviceParameterDictionaryService {


    @Resource
    private DeviceParameterDictionaryRepository deviceParameterDictionaryRepository;


    /**
     * 设备基础参数批量添加
     * @param deviceParameterDicBatchAddReq
     * @param corporateIdentify
     */
    public void batchAddDeviceParameterDic(DeviceParameterDicBatchAddReq deviceParameterDicBatchAddReq, Long corporateIdentify){

        String name = deviceParameterDicBatchAddReq.getName();

        if (!CheckParam.isNull(name)){

            String[] nameArray = name.split(",");

            if (nameArray.length>0){

                Integer max = deviceParameterDictionaryRepository.findMaxTypeByCodeAndCorporateIdentify(deviceParameterDicBatchAddReq.getCode(),
                        corporateIdentify);

                /*Map<String,Integer> calTypeMap = new HashMap<>();

                Stream.iterate(max, x -> x +1).limit(nameArray.length).forEach(f1 ->{
                    calTypeMap.put("s",f1);
                });*/

                List<DeviceParameterDictionary> deviceParameterDictionaryList = new ArrayList<>();

                for (String s : nameArray) {
                    DeviceParameterDictionary deviceParameterDictionary = new DeviceParameterDictionary();
                    deviceParameterDictionary.setName(s);
                    deviceParameterDictionary.setCode(deviceParameterDicBatchAddReq.getCode());
                    deviceParameterDictionary.setType(max+1);
                    deviceParameterDictionaryList.add(deviceParameterDictionary);

                    max = max + 1;
                }

                deviceParameterDictionaryRepository.save(deviceParameterDictionaryList);

            }
        }



    }

    /**
     * 更新设备字典
     * @param deviceParameterDicReq
     * @param corporateIdentify
     */
    public void update(DeviceParameterDicReq deviceParameterDicReq,Long corporateIdentify){

        DeviceParameterDictionary deviceParameterDictionary = deviceParameterDictionaryRepository.findByCodeAndNameAndCorporateIdentify(deviceParameterDicReq.getCode(), deviceParameterDicReq.getName()
                , corporateIdentify);

        deviceParameterDictionary.setCorporateIdentify(corporateIdentify);
        deviceParameterDictionary.setName(deviceParameterDicReq.getName());
        deviceParameterDictionary.setCode(deviceParameterDicReq.getCode());

        deviceParameterDictionaryRepository.save(deviceParameterDictionary);
    }


    /**
     * 添加设备字典
     * @param deviceParameterDicReq
     * @param corporateIdentify
     */
    @Override
    public void add(DeviceParameterDicReq deviceParameterDicReq, Long corporateIdentify) {

        DeviceParameterDictionary deviceParameterDictionary = deviceParameterDictionaryRepository.findByCodeAndNameAndCorporateIdentify(deviceParameterDicReq.getCode(), deviceParameterDicReq.getName()
                , corporateIdentify);

        if(!CheckParam.isNull(deviceParameterDictionary)){
            throw new BussinessException(ErrorCode.CORPORATE_DEVICEPARAM__REPET_ERROR.getCode(),
                    ErrorCode.CORPORATE_DEVICEPARAM__REPET_ERROR.getMessage());
        }

        /**
         * 枚举里面如果不存在，抛出异常
         */
        if(!DeviceParamDicEnum.verifyIsExist(deviceParameterDicReq.getCode())){
            throw new BussinessException(ErrorCode.DEVICEPARAMDIC_CODE_NOTEXIST_ERROR.getCode(),
                    ErrorCode.DEVICEPARAMDIC_CODE_NOTEXIST_ERROR.getMessage());
        }

        deviceParameterDictionary = new DeviceParameterDictionary();

        /**
         * 如果type为空就找到最大的，生成一个
         */
        if(CheckParam.isNull(deviceParameterDicReq.getType())){
            Integer max = deviceParameterDictionaryRepository.findMaxTypeByCodeAndCorporateIdentify(deviceParameterDicReq.getCode(), corporateIdentify);
            deviceParameterDictionary.setType(max + 1);
        }else{
            deviceParameterDictionary.setType(Integer.valueOf(deviceParameterDicReq.getType()));
        }

        deviceParameterDictionary.setCode(deviceParameterDicReq.getCode());
        deviceParameterDictionary.setName(deviceParameterDicReq.getName());
        deviceParameterDictionary.setCorporateIdentify(corporateIdentify);

        deviceParameterDictionaryRepository.save(deviceParameterDictionary);

    }


    /**
     * 根据code查询设备参数列表
     * @param code
     * @return
     */
    public List<DeviceParameterDictionary> findByCode(String code,Long corporateIdentify){
        List<DeviceParameterDictionary> deviceParameterDictionaryList = deviceParameterDictionaryRepository.findByCodeAndCorporateIdentify(code, corporateIdentify);
        return deviceParameterDictionaryList;

    }

}
