package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.entity.DeviceSparesType;
import tech.yozo.factoryrp.vo.req.DeviceInfoReq;
import tech.yozo.factoryrp.vo.req.DeviceSparesSaveReq;
import tech.yozo.factoryrp.vo.resp.sparepars.DeviceSparesMobileResp;
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
     * 调整设备备件显示顺序
     * @param operateType 操作类型 1是上移动，2是下移
     * @param id
     * @return
     */
    DeviceSparesTypeResp updateShowOrder(Integer operateType,Long id);

    /**
     * 调整上级备件类型
     * @param id 需要被调整的备件类型id
     * @param parentId 需要调整成为的上级备件类型
     * @return
     */
    DeviceSparesTypeResp updateUpLevel(Long id, Long parentId);

    /**
     * 删除备件类型
     * 如果这个id是父id，必须删除所有子项以及本身
     * @param id
     * @param corporateIdentify
     */
    void deleteDeviceSparesType(Long id,Long corporateIdentify);

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
