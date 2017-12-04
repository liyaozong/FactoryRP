package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.vo.req.SaveRelDeviceReq;
import tech.yozo.factoryrp.vo.req.QueryDeviceSpareRelReq;
import tech.yozo.factoryrp.vo.req.SaveRelSparePartReq;
import tech.yozo.factoryrp.vo.resp.device.rel.DeviceSpartRelResp;
import tech.yozo.factoryrp.vo.resp.device.rel.SpartDeviceRelResp;

import java.util.List;

/**
 * @author chenxiang
 * @create 2017-12-02 上午11:47
 **/
public interface DeviceSparePartRelService {

    /**
     * 保存设备关联的备件信息
     * @param param
     */
    public void saveRelSparePart(SaveRelSparePartReq param,Long corporateIdentify);

    /**
     * 保存备件关联的设备信息
     * @param param
     */
    public void saveRelDevice(SaveRelDeviceReq param,Long corporateIdentify);

    /**
     *根据备件查询关联的设备信息
     * @param req
     * @return
     */
    public Pagination<DeviceSpartRelResp> findRelDeviceInfoByPage(QueryDeviceSpareRelReq req, Long corporateIdentify);

    /**
     *根据设备查询关联的备件信息
     * @param req
     * @return
     */
    public Pagination<SpartDeviceRelResp> findRelSpareInfoByPage(QueryDeviceSpareRelReq req, Long corporateIdentify);

    /**
     * 批量删除关联信息
     * @param ids
     */
    public void deleteRelInfoByIds(List<Long> ids);
}
