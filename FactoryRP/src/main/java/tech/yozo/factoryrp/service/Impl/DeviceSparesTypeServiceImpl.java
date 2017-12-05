package tech.yozo.factoryrp.service.Impl;

import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.DeviceSparesType;
import tech.yozo.factoryrp.repository.DeviceSparesTypeRepository;
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
