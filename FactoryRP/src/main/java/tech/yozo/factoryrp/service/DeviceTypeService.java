package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.entity.DeviceType;
import tech.yozo.factoryrp.vo.req.SaveDeviceTypeReq;

import java.util.List;

public interface DeviceTypeService {

    /**
     * 根据企业标识查询列表
     * @param corporateIdentify
     * @return
     */
    public List<DeviceType> list(Long corporateIdentify);

    /**
     * 保存信息
     * @param param
     * @param opType 保存类型，1:添加同级：2:添加下级；3：修改
     * @return
     */
    public DeviceType save(SaveDeviceTypeReq param, Integer opType,Long corporateIdentify);

    /**
     * 删除当前设备及子设备
     * @param id
     * @return
     */
    public void delete(Long id);

    /**
     * 调整上级
     * @param id
     * @param parentId
     * @return
     */
    public DeviceType updateUpLevel(Long id,Long parentId);
}
