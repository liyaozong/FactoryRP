package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.entity.DeviceSparesType;
import tech.yozo.factoryrp.vo.req.DeviceSparesSaveReq;
import tech.yozo.factoryrp.vo.resp.sparepars.DeviceSparesTypeResp;

import java.util.List;

/**
 * 备件类型服务
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/5
 * @description
 */
public interface DeviceSparesTypeService {


    /**
     * 查询所有备件类型
     * @return
     */
    List<DeviceSparesTypeResp> queryAllDeviceSparesType(Long corporateIdentify);


    /**
     * 新增备件类型 根据operateType来区分操作
     * 功能有 添加同级(operateType为1) 添加下级(operateType为2) 修改备件名称(operateType为3)
     * @param deviceSparesSaveReq
     * @param operateType
     * @param corporateIdentify
     * @return
     */
    DeviceSparesType saveDeviceSparesType(DeviceSparesSaveReq deviceSparesSaveReq, Integer operateType, Long corporateIdentify);


}
