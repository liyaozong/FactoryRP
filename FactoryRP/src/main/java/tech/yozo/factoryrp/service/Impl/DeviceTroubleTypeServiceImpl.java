package tech.yozo.factoryrp.service.Impl;

import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.DeviceTroubleType;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.repository.DeviceTroubleTypeRepository;
import tech.yozo.factoryrp.service.DeviceTroubleTypeService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.DeviceTroubleTypeReq;
import tech.yozo.factoryrp.vo.resp.device.trouble.DeviceTroubleTypeVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/14
 * @description
 */
@Service
public class DeviceTroubleTypeServiceImpl implements DeviceTroubleTypeService {


    @Resource
    private DeviceTroubleTypeRepository deviceTroubleTypeRepository;

    /**
     * 调整设备故障类型显示顺序
     * @param operateType 操作类型 1是上移动，2是下移
     * @param id
     * @return
     */
    @Override
    public DeviceTroubleTypeVo updateShowOrder(Integer operateType, Long id) {
        DeviceTroubleType deviceTroubleType = deviceTroubleTypeRepository.findOne(id);

        if(!CheckParam.isNull(deviceTroubleType)){

            //上移的操作
            if(1 == operateType){
                deviceTroubleType.setShowOrder(deviceTroubleType.getShowOrder() + 1);
            }else if(2 == operateType){
                deviceTroubleType.setShowOrder(deviceTroubleType.getShowOrder() - 1);
            }

            deviceTroubleTypeRepository.save(deviceTroubleType);

            DeviceTroubleTypeVo deviceTroubleTypeVo = new DeviceTroubleTypeVo();
            deviceTroubleTypeVo.setId(deviceTroubleType.getId());
            deviceTroubleTypeVo.setShowOrder(deviceTroubleType.getShowOrder());
            deviceTroubleTypeVo.setStatusFlag(deviceTroubleType.getStatusFlag());
            deviceTroubleTypeVo.setParentId(deviceTroubleType.getParentId());
            deviceTroubleTypeVo.setName(deviceTroubleType.getName());

            return deviceTroubleTypeVo;
        }

        return null;
    }


    /**
     * 调整设备故障类型类型
     * @param id 需要被调整的设备故障类型id
     * @param parentId 需要调整成为的上级设备故障类型类型
     * @return
     */
    @Override
    public DeviceTroubleTypeVo updateUpLevel(Long id, Long parentId) {
        DeviceTroubleType deviceTroubleType = deviceTroubleTypeRepository.findOne(id);

        if(!CheckParam.isNull(deviceTroubleType)){
            deviceTroubleType.setParentId(parentId);

            deviceTroubleTypeRepository.save(deviceTroubleType);

            DeviceTroubleTypeVo deviceTroubleTypeVo = new DeviceTroubleTypeVo();

            deviceTroubleTypeVo.setId(deviceTroubleType.getId());
            deviceTroubleTypeVo.setName(deviceTroubleType.getName());
            deviceTroubleTypeVo.setParentId(deviceTroubleType.getParentId());
            deviceTroubleTypeVo.setStatusFlag(deviceTroubleType.getStatusFlag());
            deviceTroubleTypeVo.setShowOrder(deviceTroubleType.getShowOrder());

            return deviceTroubleTypeVo;
        }

        return null;

    }

    /**
     * 删除设备故障类型类型
     * 如果这个id是父id，必须删除所有子项以及本身
     * @param id
     * @param corporateIdentify
     */
    @Override
    public void deleteDeviceTroubleLevel(Long id, Long corporateIdentify) {
        List<DeviceTroubleType> deviceTroubleList = deviceTroubleTypeRepository.findByParentIdAndCorporateIdentify(id, corporateIdentify);

        if(!CheckParam.isNull(deviceTroubleList) && !deviceTroubleList.isEmpty()){
            deviceTroubleTypeRepository.deleteInBatch(deviceTroubleList);
        }

        deviceTroubleTypeRepository.delete(id);
    }

    /**
     * 查询所有设备故障类型类型
     * @return
     */
    @Override
    public List<DeviceTroubleTypeVo> queryAllDeviceTroubleLevel(Long corporateIdentify) {
        List<DeviceTroubleType> deviceTroubleTypeList = deviceTroubleTypeRepository.findByCorporateIdentify(corporateIdentify);

        if(!CheckParam.isNull(deviceTroubleTypeList) && !deviceTroubleTypeList.isEmpty()){
            List<DeviceTroubleTypeVo> deviceTroubleTypeRespVoList = new ArrayList<>();

            deviceTroubleTypeList.stream().forEach(d1 ->{

                DeviceTroubleTypeVo deviceTroubleTypeVo = new DeviceTroubleTypeVo();

                deviceTroubleTypeVo.setId(d1.getId());
                deviceTroubleTypeVo.setName(d1.getName());
                deviceTroubleTypeVo.setParentId(d1.getParentId());
                deviceTroubleTypeVo.setStatusFlag(d1.getStatusFlag());
                deviceTroubleTypeVo.setShowOrder(d1.getShowOrder());

                deviceTroubleTypeRespVoList.add(deviceTroubleTypeVo);
            });


            return deviceTroubleTypeRespVoList;
        }
        return null;
    }

    /**
     * 新增设备故障类型类型 根据operateType来区分操作
     * 功能有 添加同级(operateType为1) 添加下级(operateType为2) 修改设备故障类型名称(operateType为3)
     * @param deviceTroubleTypeReq
     * @param operateType
     * @param corporateIdentify
     * @return
     */
    @Override
    public DeviceTroubleType saveDeviceTroubleLevel(DeviceTroubleTypeReq deviceTroubleTypeReq, Integer operateType, Long corporateIdentify) {
        DeviceTroubleType deviceTroubleType = new DeviceTroubleType();
        deviceTroubleType.setCorporateIdentify(corporateIdentify);
        deviceTroubleType.setStatusFlag(1);
        deviceTroubleType.setName(deviceTroubleTypeReq.getName());
        if(!CheckParam.isNull(deviceTroubleTypeReq.getShowOrder())){
            deviceTroubleType.setShowOrder(deviceTroubleTypeReq.getShowOrder());
        }else{
            deviceTroubleType.setShowOrder(999);
        }

        if (1==operateType){

            //注意判断重复
            DeviceTroubleType sameLevelDeviceSparesType = deviceTroubleTypeRepository.findByParentIdAndCorporateIdentifyAndName(deviceTroubleTypeReq.getParentId()
                    , corporateIdentify,deviceTroubleTypeReq.getName());

            if(!CheckParam.isNull(sameLevelDeviceSparesType)){
                throw new BussinessException(ErrorCode.SYSTEM_DIC_PARAM_REPET_ERROR.getCode(),ErrorCode.SYSTEM_DIC_PARAM_REPET_ERROR.getMessage());
            }

            //添加同级
            deviceTroubleType.setParentId(deviceTroubleTypeReq.getParentId());
        }
        if (2==operateType){

            //注意判断重复
            DeviceTroubleType sameLevelDeviceSparesType = deviceTroubleTypeRepository.findByParentIdAndCorporateIdentifyAndName(deviceTroubleTypeReq.getId(),
                    corporateIdentify, deviceTroubleTypeReq.getName());

            if(!CheckParam.isNull(sameLevelDeviceSparesType)){
                throw new BussinessException(ErrorCode.SYSTEM_DIC_PARAM_REPET_ERROR.getCode(),ErrorCode.SYSTEM_DIC_PARAM_REPET_ERROR.getMessage());
            }

            //添加下级
            deviceTroubleType.setParentId(deviceTroubleTypeReq.getId());
        }
        if (3==operateType){
            //修改
            deviceTroubleType = deviceTroubleTypeRepository.findOne(deviceTroubleTypeReq.getId());
        }

        return deviceTroubleTypeRepository.save(deviceTroubleType);
    }
}
