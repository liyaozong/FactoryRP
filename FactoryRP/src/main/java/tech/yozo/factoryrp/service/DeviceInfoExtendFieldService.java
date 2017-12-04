package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.entity.DeviceInfoExtendField;
import tech.yozo.factoryrp.vo.req.DeviceInfoExtendFieldReq;

public interface DeviceInfoExtendFieldService {
    /**
     * 保存设备信息扩展字段
     * @param param
     * @return
     */
    public DeviceInfoExtendField save(DeviceInfoExtendFieldReq param, Long corporateIdentify);

    /**
     * 根据企业唯一标识查询设备信息扩展字段
     * @param corporateIdentify
     * @return
     */
    public DeviceInfoExtendField findByCorporateIdentify(Long corporateIdentify);
}
