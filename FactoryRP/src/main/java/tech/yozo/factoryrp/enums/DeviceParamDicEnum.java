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

    MEASUREUNIT(1,"计量单位");

    private Integer code;
    private String name;

    DeviceParamDicEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static DeviceParamDicEnum getByCode(Integer code) {
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
