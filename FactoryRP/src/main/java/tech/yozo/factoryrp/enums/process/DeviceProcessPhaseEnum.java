package tech.yozo.factoryrp.enums.process;

import tech.yozo.factoryrp.utils.CheckParam;

/**
 * 设备流程阶段枚举
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/28
 * @description
 */
public enum DeviceProcessPhaseEnum {

    DEVICE_PROCESS_PHASE_APPLICATION_APPROVAL("device_process_phase_application_approval","申请审核"),
    DEVICE_PROCESS_REPAIRED_ENDED_VERIFYING("device_process_repaired_ended_verifying","维修结束待验证");


    private String code;

    private String name;


    DeviceProcessPhaseEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static DeviceProcessPhaseEnum getByCode(String code) {
        for (DeviceProcessPhaseEnum v : DeviceProcessPhaseEnum.values()) {
            if (!CheckParam.isNull(code) && v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
    public static DeviceProcessPhaseEnum getByName(String name) {
        if (CheckParam.isNull(name)) {
            return null;
        }
        for (DeviceProcessPhaseEnum v : DeviceProcessPhaseEnum.values()) {
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
