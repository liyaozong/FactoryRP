package cn.tech.yozo.factoryrp.service.Impl;

import cn.tech.yozo.factoryrp.entity.DeviceInfoExtendField;
import cn.tech.yozo.factoryrp.repository.DeviceInfoExtendFieldRepository;
import cn.tech.yozo.factoryrp.service.DeviceInfoExtendFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 设备信息扩展字段业务逻辑
 */
@Service
public class DeviceInfoExtendFieldServiceImpl implements DeviceInfoExtendFieldService{

    @Autowired
    private DeviceInfoExtendFieldRepository deviceInfoExtendFieldRepository;

    @Override
    public DeviceInfoExtendField save(DeviceInfoExtendField param) {
        DeviceInfoExtendField old = deviceInfoExtendFieldRepository.
                findByCorporateIdentifyAndStatusFlag(param.getCorporateIdentify(),1);
        if (null!=old){
            param.setId(old.getId());
            param.setCreateTime(old.getCreateTime());
        }
        return deviceInfoExtendFieldRepository.save(param);
    }

    @Override
    public DeviceInfoExtendField findByCorporateIdentify(Long corporateIdentify) {
        return deviceInfoExtendFieldRepository.findByCorporateIdentifyAndStatusFlag(corporateIdentify,1);
    }
}
