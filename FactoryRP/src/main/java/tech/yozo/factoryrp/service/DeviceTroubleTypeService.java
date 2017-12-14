package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.entity.DeviceTroubleType;
import tech.yozo.factoryrp.vo.req.DeviceTroubleTypeReq;
import tech.yozo.factoryrp.vo.resp.device.trouble.DeviceTroubleTypeVo;

import java.util.List;

/**
 * 设备故障类型相关服务
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/14
 * @description
 */
public interface DeviceTroubleTypeService {

    /**
     * 调整设备故障类型显示顺序
     * @param operateType 操作类型 1是上移动，2是下移
     * @param id
     * @return
     */
    DeviceTroubleTypeVo updateShowOrder(Integer operateType, Long id);

    /**
     * 调整设备故障类型类型
     * @param id 需要被调整的设备故障类型id
     * @param parentId 需要调整成为的上级设备故障类型类型
     * @return
     */
    DeviceTroubleTypeVo updateUpLevel(Long id, Long parentId);

    /**
     * 删除设备故障类型类型
     * 如果这个id是父id，必须删除所有子项以及本身
     * @param id
     * @param corporateIdentify
     */
    void deleteDeviceTroubleLevel(Long id, Long corporateIdentify);

    /**
     * 查询所有设备故障类型类型
     * @return
     */
    List<DeviceTroubleTypeVo> queryAllDeviceTroubleLevel(Long corporateIdentify);


    /**
     * 新增设备故障类型类型 根据operateType来区分操作
     * 功能有 添加同级(operateType为1) 添加下级(operateType为2) 修改设备故障类型名称(operateType为3)
     * @param deviceTroubleTypeReq
     * @param operateType
     * @param corporateIdentify
     * @return
     */
    DeviceTroubleType saveDeviceTroubleLevel(DeviceTroubleTypeReq deviceTroubleTypeReq, Integer operateType, Long corporateIdentify);
}
