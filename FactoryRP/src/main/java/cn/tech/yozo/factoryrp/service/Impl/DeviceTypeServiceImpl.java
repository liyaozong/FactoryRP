package cn.tech.yozo.factoryrp.service.Impl;

import cn.tech.yozo.factoryrp.entity.Department;
import cn.tech.yozo.factoryrp.entity.DeviceType;
import cn.tech.yozo.factoryrp.repository.DeviceTypeRepository;
import cn.tech.yozo.factoryrp.service.DeviceTypeService;
import cn.tech.yozo.factoryrp.vo.req.SaveDeviceTypeReq;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceTypeServiceImpl implements DeviceTypeService{

    @Autowired
    private DeviceTypeRepository deviceTypeRepository;

    @Override
    public List<DeviceType> list(Long corporateIdentify) {
        return deviceTypeRepository.findByCorporateIdentifyAndStatusFlag(corporateIdentify,1);
    }

    @Override
    public DeviceType save(SaveDeviceTypeReq param, Integer opType) {

        DeviceType d = new DeviceType();
        d.setCorporateIdentify(param.getCorporateIdentify());
        d.setStatusFlag(1);
        d.setShowOrder(999);
        if (1==opType){
            //添加同级
            d.setParentId(param.getParentId());
        }
        if (2==opType){
            //添加下级
            d.setParentId(param.getId());
        }
        if (3==opType){
            //修改
            d= deviceTypeRepository.getOne(param.getId());
        }
        d.setName(param.getName());
        return deviceTypeRepository.save(d);    }

    @Override
    public void delete(Long id) {
        List<DeviceType> needDelete = deviceTypeRepository.findByParentId(id);
        if (CollectionUtils.isNotEmpty(needDelete)){
            for (DeviceType d : needDelete){
                deviceTypeRepository.delete(d.getId());
            }
        }
        deviceTypeRepository.delete(id);
    }

    @Override
    public DeviceType updateUpLevel(Long id, Long parentId) {
        DeviceType d= deviceTypeRepository.getOne(id);
        d.setParentId(parentId);
        return deviceTypeRepository.save(d);
    }
}
