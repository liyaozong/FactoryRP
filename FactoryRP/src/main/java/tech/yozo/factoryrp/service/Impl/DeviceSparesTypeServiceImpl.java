package tech.yozo.factoryrp.service.Impl;

import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.DeviceSparesType;
import tech.yozo.factoryrp.repository.DeviceSparesTypeRepository;
import tech.yozo.factoryrp.service.DeviceSparePartRelService;
import tech.yozo.factoryrp.service.DeviceSparesTypeService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.req.DeviceSparesSaveReq;
import tech.yozo.factoryrp.vo.resp.sparepars.DeviceSparesTypeResp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/5
 * @description
 */
@Service
public class DeviceSparesTypeServiceImpl implements DeviceSparesTypeService {



    @Resource
    private DeviceSparesTypeRepository deviceSparesTypeRepository;

    @Resource
    private DeviceSparePartRelService deviceSparePartRelService;


    /**
     * 调整设备备件显示顺序
     * @param operateType 操作类型 1是上移动，2是下移
     * @param id
     * @return
     */
    public DeviceSparesTypeResp updateShowOrder(Integer operateType,Long id){

        DeviceSparesType deviceSparesType = deviceSparesTypeRepository.findOne(id);

        if(!CheckParam.isNull(deviceSparesType)){

            //上移的操作
            if(1 == operateType){
                deviceSparesType.setShowOrder(deviceSparesType.getShowOrder() + 1);
            }else if(2 == operateType){
                deviceSparesType.setShowOrder(deviceSparesType.getShowOrder() - 1);
            }

            deviceSparesTypeRepository.save(deviceSparesType);
        }

        return null;
    }

    /**
     * 调整上级备件类型
     * @param id 需要被调整的备件类型id
     * @param parentId 需要调整成为的上级备件类型
     * @return
     */
    public DeviceSparesTypeResp updateUpLevel(Long id, Long parentId){
        DeviceSparesType deviceSparesType = deviceSparesTypeRepository.findOne(id);

        if(!CheckParam.isNull(deviceSparesType)){
            deviceSparesType.setParentId(parentId);

            deviceSparesTypeRepository.save(deviceSparesType);

            DeviceSparesTypeResp deviceSparesTypeResp = new DeviceSparesTypeResp();
            deviceSparesTypeResp.setId(deviceSparesType.getId());
            deviceSparesTypeResp.setName(deviceSparesType.getName());
            deviceSparesTypeResp.setParentId(deviceSparesType.getParentId());
            deviceSparesTypeResp.setStatusFlag(deviceSparesType.getStatusFlag());
            deviceSparesTypeResp.setShowOrder(deviceSparesType.getShowOrder());

            return deviceSparesTypeResp;
        }

            return null;

    }

    /**
     * 删除备件类型
     * 如果这个id是父id，必须删除所有子项以及本身
     * @param id
     * @param corporateIdentify
     */
    public void deleteDeviceSparesType(Long id,Long corporateIdentify){

        List<DeviceSparesType> deviceSparesTypeList = deviceSparesTypeRepository.findByParentIdAndCorporateIdentify(id,corporateIdentify);

        if(!CheckParam.isNull(deviceSparesTypeList) && !deviceSparesTypeList.isEmpty()){
            deviceSparesTypeRepository.deleteInBatch(deviceSparesTypeList);
        }

        deviceSparesTypeRepository.delete(id);
    }

    /**
     * 新增备件类型 根据operateType来区分操作
     * 功能有 添加同级(operateType为1) 添加下级(operateType为2) 修改备件名称(operateType为3)
     * @param deviceSparesSaveReq
     * @param operateType
     * @param corporateIdentify
     * @return
     */
    public DeviceSparesType saveDeviceSparesType(DeviceSparesSaveReq deviceSparesSaveReq,Integer operateType,Long corporateIdentify){
        DeviceSparesType deviceSparesType = new DeviceSparesType();
        deviceSparesSaveReq.setCorporateIdentify(corporateIdentify);
        deviceSparesSaveReq.setStatusFlag(1);

        if (1==operateType){
            //添加同级
            deviceSparesType.setParentId(deviceSparesSaveReq.getParentId());
        }
        if (2==operateType){
            //添加下级
            deviceSparesType.setParentId(deviceSparesSaveReq.getId());
        }
        if (3==operateType){
            //修改
            deviceSparesType = deviceSparesTypeRepository.findOne(deviceSparesSaveReq.getId());
            deviceSparesType.setName(deviceSparesSaveReq.getName());


        }

        return deviceSparesTypeRepository.save(deviceSparesType);

    }

    /**
     * 查询所有备件类型
     * @return
     */
    public List<DeviceSparesTypeResp> queryAllDeviceSparesType(Long corporateIdentify){

        List<DeviceSparesType> deviceSparesTypeList = deviceSparesTypeRepository.findByCorporateIdentify(corporateIdentify);

        if(!CheckParam.isNull(deviceSparesTypeList) && !deviceSparesTypeList.isEmpty()){
            List<DeviceSparesTypeResp> deviceSparesTypeRespList = new ArrayList<>();

            deviceSparesTypeList.stream().forEach(d1 ->{
                DeviceSparesTypeResp deviceSparesTypeResp = new DeviceSparesTypeResp();
                deviceSparesTypeResp.setId(d1.getId());
                deviceSparesTypeResp.setName(d1.getName());
                deviceSparesTypeResp.setParentId(d1.getParentId());
                deviceSparesTypeResp.setStatusFlag(d1.getStatusFlag());
                deviceSparesTypeResp.setShowOrder(d1.getShowOrder());

                deviceSparesTypeRespList.add(deviceSparesTypeResp);
            });


            return deviceSparesTypeRespList;
        }
            return null;
    }




}
