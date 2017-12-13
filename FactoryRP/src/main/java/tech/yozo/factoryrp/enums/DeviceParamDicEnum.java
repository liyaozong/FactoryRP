package tech.yozo.factoryrp.enums;

import tech.yozo.factoryrp.utils.CheckParam;

/**
 *
 * 设备参数枚举
 * 对应着设备参数表
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/10
 * @description
 */
public enum DeviceParamDicEnum {

    SPARE_PARTS_MEASURING_UNIT("measuring_unit","计量单位"),
    SPARE_PARTS_MATERIAL_PROPERTIES("material_properties","物料属性");

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
            if (!CheckParam.isNull(code) && v.getCode() == code) {
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

}
