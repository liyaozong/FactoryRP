package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.entity.SpareParts;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.vo.req.DeviceInfoReq;
import tech.yozo.factoryrp.vo.req.SparePartsAddReq;
import tech.yozo.factoryrp.vo.req.SparePartsQueryReq;
import tech.yozo.factoryrp.vo.resp.sparepars.DeviceSparesMobileResp;
import tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;

import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/28
 * @description 备件相关服务
 */
public interface SparePartsService {


    /**
     * 根据备件id删除备件
     * @param id
     * @param corporateIdentify
     */
    void deleteSparePartsById(Long id,Long corporateIdentify);

    /**
     * 根据条件分页查询 mobile
     * @param sparePartsQueryReq
     * @return
     */
    Pagination<SpareParts> queryByPage(SparePartsQueryReq sparePartsQueryReq);

    /**
     * 根据条件分页查询 WEB
     * @param sparePartsQueryReq
     * @param corporateIdentify
     * @return
     */
    Pagination<SpareParts> findByPage(SparePartsQueryReq sparePartsQueryReq,Long corporateIdentify);


    /**
     * 新增备件
     * @param sparePartsReq
     * @param corporateIdentify
     * @return
     */
    SparePartsResp addSpareParts(SparePartsAddReq sparePartsReq,Long corporateIdentify);

    /**
     * 根据主键批量查询
     * @param ids
     * @return
     */
    List<SparePartsResp> findByIds(List<Long> ids);

    /**
     * 手机端查询备件信息
     * @param sparePartsQueryReq
     * @return
     */
    Pagination<DeviceSparesMobileResp> queryMobileDeviceSpares(SparePartsQueryReq sparePartsQueryReq,Long corporateIdentify);

    /**
     * 批量删除备件信息
     * 先删除备件信息
     * 再删除备件信息和设备信息的关联信息
     * @param ids
     * @param corporateIdentify
     */
    void deleteSparePartsByIds(List<Long> ids,Long corporateIdentify);
}
