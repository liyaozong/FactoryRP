package tech.yozo.factoryrp.enums.process;

import tech.yozo.factoryrp.utils.CheckParam;

/**
 * 流程类型枚举
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/28
 * @description
 */
public enum DeviceProcessTypeEnum {

    DEVICE_PROCESS_RECEIVE("device_process_type_receive","设备领用"),
    DEVICE_PROCESS_MALFUNCTION_REPAIR("device_process_type_malfunction_repair","故障报修"),
    DEVICE_PROCESS_ENTRUST_REPAIR("device_process_entrust_repair","外委维修"),
    DEVICE_PROCESS_PURCHASE("device_process_purchase","设备购置"),
    DEVICE_PROCESS_ALLOT("device_process_allot","设备调拨"),
    DEVICE_PROCESS_GIVE_BACK("device_process_give_back","设备还回"),
    DEVICE_PROCESS_SPARE_PART_PURCHASE("device_process_spare_part_purchase","备件购置"),
    DEVICE_PROCESS_UNUSED("device_process_unused","设备报废/封存/状态改变"),
    DEVICE_PROCESS_OTHER("device_process_transfer","设备移交");


    private String code;

    private String name;

    DeviceProcessTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static DeviceProcessTypeEnum getByCode(String code) {
        for (DeviceProcessTypeEnum v : DeviceProcessTypeEnum.values()) {
            if (!CheckParam.isNull(code) && v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
    public static DeviceProcessTypeEnum getByName(String name) {
        if (CheckParam.isNull(name)) {
            return null;
        }
        for (DeviceProcessTypeEnum v : DeviceProcessTypeEnum.values()) {
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
        return !CheckParam.isNull(getByCode(code));
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
