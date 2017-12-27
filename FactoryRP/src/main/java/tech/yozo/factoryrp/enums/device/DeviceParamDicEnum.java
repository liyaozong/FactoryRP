package tech.yozo.factoryrp.enums.device;

import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.resp.DeviceParamDicEnumResp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 设备参数枚举
 * 对应着设备参数表
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/10
 * @description
 */
public enum DeviceParamDicEnum {

    DEVICE_PARAMMEASURING_UNIT("device_measuring_unit","设备计量单位"),
    DEVICE_PARAM_USESTATUS("device_use_status","设备使用状况"),
    DEVICE_PARAM_TROUBLE_REASON("device_trouble_reason","设备故障原因"),
    DEVICE_PARAM_TROUBLE_LEVEL("device_trouble_level","设备故障等级"),
    DEVICE_PARAM_REPAIR_LEVEL("device_repair_level","设备故障等级"),
    DEVICE_PARAM_MAINTENANCE_LEVEL("device_maintenance_level","设备保养级别"),
    DEVICE_PARAM_DEVICE_FLAG("device_device_flag","设备标识"),
    DEVICE_PARAM_BAD_REVIEW_REASON("device_bad_review_reason","设备差评理由"),
    DEVICE_PARAMMATERIAL_PROPERTIES("device_material_properties","设备物料属性"),
    DEVICE_PROCESS_TYPE("device_process_type","设备流程类型");
    private String code;
    private String name;

    DeviceParamDicEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static DeviceParamDicEnum getByCode(String code) {
        for (DeviceParamDicEnum v : DeviceParamDicEnum.values()) {
            if (!CheckParam.isNull(code) && v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
    public static DeviceParamDicEnum getByName(String name) {
        if (CheckParam.isNull(name)) {
            return null;
        }
        for (DeviceParamDicEnum v : DeviceParamDicEnum.values()) {
            if (v.name().equalsIgnoreCase(name)) {
                return v;
            }
        }
        return null;
    }


    /**
     * 判断是code是否存在
     * @param code
     * @return
     */
    public static Boolean verifyIsExist(String code){
        boolean b = CheckParam.isNull(getByCode(code));
        return !CheckParam.isNull(getByCode(code));
    }

    /**
     * 拿到所有的错误码和描述
     * @return
     */
    public static List<DeviceParamDicEnumResp> getAllCodesAndName(){
        List<DeviceParamDicEnumResp> list = new ArrayList<>();
        Arrays.stream(DeviceParamDicEnum.values()).forEach(e1 -> {
            DeviceParamDicEnumResp errorCodeResp = new DeviceParamDicEnumResp();

            errorCodeResp.setCode(e1.getCode());
            errorCodeResp.setName(e1.getName());

            list.add(errorCodeResp);
        });


        return list;
    }

}
