package cn.tech.yozo.factoryrp.service;

import cn.tech.yozo.factoryrp.entity.DeviceInfoExtendField;

public interface DeviceInfoExtendFieldService {
    /**
     * 保存设备信息扩展字段
     * @param param
     * @return
     */
    public DeviceInfoExtendField save(DeviceInfoExtendField param);

    /**
     * 根据企业唯一标识查询设备信息扩展字段
     * @param corporateIdentify
     * @return
     */
    public DeviceInfoExtendField findByCorporateIdentify(Long corporateIdentify);
}
