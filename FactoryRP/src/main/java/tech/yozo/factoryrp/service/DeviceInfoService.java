package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.entity.DeviceInfo;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.vo.req.AddDeviceReq;
import tech.yozo.factoryrp.vo.req.DeviceInfoReq;
import tech.yozo.factoryrp.vo.resp.device.info.FullDeviceInfoResp;
import tech.yozo.factoryrp.vo.resp.device.info.SimpleDeviceInfoResp;

import java.util.List;

public interface DeviceInfoService {

    /**
     * 分页查询设备信息
     * @param param
     * @return
     */
    Pagination<FullDeviceInfoResp> findByPage(DeviceInfoReq param,Long corporateIdentify);

    /**
     * 保存设备信息
     * @param param
     * @return
     */
    DeviceInfo save(AddDeviceReq param,Long corporateIdentify);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    FullDeviceInfoResp getById(Long id,Long corporateIdentify);

    /**
     * 根据设备编码查询
     * @param code
     * @return
     */
    FullDeviceInfoResp getByCode(String code,Long corporateIdentify);

    /**
     * 分页查询设备简略信息
     * @param param
     * @return
     */
    Pagination<SimpleDeviceInfoResp> findSimpleInfoByPage(DeviceInfoReq param,Long corporateIdentify);

    /**
     * 根据设备主键批量查询设备信息
     * @param ids
     * @return
     */
    List<FullDeviceInfoResp> findByIds(List<Long> ids,Long corporateIdentify);

    /**
     * 批量删除
     * @param ids
     */
    void deleteRelInfoByIds(List<Long> ids,Long corporateIdentify);
}
