package cn.tech.yozo.factoryrp.service.Impl;

import cn.tech.yozo.factoryrp.entity.DeviceInfoExtendField;
import cn.tech.yozo.factoryrp.repository.DeviceInfoExtendFieldRepository;
import cn.tech.yozo.factoryrp.service.DeviceInfoExtendFieldService;
import cn.tech.yozo.factoryrp.vo.req.DeviceInfoExtendFieldReq;
import org.springframework.beans.BeanUtils;
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
    public DeviceInfoExtendField save(DeviceInfoExtendFieldReq param,Long corporateIdentify) {
        DeviceInfoExtendField old = deviceInfoExtendFieldRepository.
                findByCorporateIdentifyAndStatusFlag(corporateIdentify,1);
        DeviceInfoExtendField s = new DeviceInfoExtendField();
        BeanUtils.copyProperties(param,s);
        if (null!=old){
            s.setId(old.getId());
            s.setCreateTime(old.getCreateTime());
        }
        return deviceInfoExtendFieldRepository.save(s);
    }

    @Override
    public DeviceInfoExtendField findByCorporateIdentify(Long corporateIdentify) {
        return deviceInfoExtendFieldRepository.findByCorporateIdentifyAndStatusFlag(corporateIdentify,1);
    }
}
