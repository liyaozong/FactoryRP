package cn.tech.yozo.factoryrp.service;

import cn.tech.yozo.factoryrp.entity.DeviceInfo;
import cn.tech.yozo.factoryrp.page.Pagination;
import cn.tech.yozo.factoryrp.vo.req.DeviceInfoReq;
import cn.tech.yozo.factoryrp.vo.resp.device.info.FullDeviceInfoResp;
import cn.tech.yozo.factoryrp.vo.resp.device.info.SimpleDeviceInfoResp;

import java.util.List;

public interface DeviceInfoService {

    /**
     * 分页查询设备信息
     * @param param
     * @return
     */
    public Pagination<FullDeviceInfoResp> findByPage(DeviceInfoReq param,Long corporateIdentify);

    /**
     * 保存设备信息
     * @param param
     * @return
     */
    public DeviceInfo save(DeviceInfo param);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    public DeviceInfo getById(Long id);

    /**
     * 分页查询设备简略信息
     * @param param
     * @return
     */
    public Pagination<SimpleDeviceInfoResp> findSimpleInfoByPage(DeviceInfoReq param,Long corporateIdentify);

    /**
     * 根据设备主键批量查询设备信息
     * @param ids
     * @return
     */
    public List<FullDeviceInfoResp> findByIds(List<Long> ids,Long corporateIdentify);
}
