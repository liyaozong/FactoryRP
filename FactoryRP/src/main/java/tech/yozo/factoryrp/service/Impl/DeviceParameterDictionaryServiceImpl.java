package tech.yozo.factoryrp.service.Impl;

import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.DeviceParameterDictionary;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.repository.DeviceParameterDictionaryRepository;
import tech.yozo.factoryrp.service.DeviceParameterDictionaryService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.DeviceParameterDicReq;

import javax.annotation.Resource;
import java.util.List;

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
            throw new BussinessException(ErrorCode.CORPORATE_DEVICEPARAM__REPET_ERROR.getCode(),ErrorCode.CORPORATE_DEVICEPARAM__REPET_ERROR.getMessage());
        }

        deviceParameterDictionary = new DeviceParameterDictionary();

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
