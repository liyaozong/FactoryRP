package cn.tech.yozo.factoryrp.service;

import cn.tech.yozo.factoryrp.entity.DeviceInfo;
import cn.tech.yozo.factoryrp.page.Pagination;
import cn.tech.yozo.factoryrp.vo.req.DeviceInfoReq;

public interface DeviceInfoService {

    /**
     * 分页查询设备信息
     * @param param
     * @return
     */
    public Pagination<DeviceInfo> findByPage(DeviceInfoReq param);

    /**
     * 保存设备信息
     * @param param
     * @return
     */
    public DeviceInfo save(DeviceInfo param);
}
