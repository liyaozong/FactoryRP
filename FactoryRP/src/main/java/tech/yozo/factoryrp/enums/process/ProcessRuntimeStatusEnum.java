package tech.yozo.factoryrp.enums.process;

import tech.yozo.factoryrp.utils.CheckParam;

/**
 * 流程运行状态枚举
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/28
 * @description
 */
public enum ProcessRuntimeStatusEnum {

    PROCESS_RUNTIME_PROCESSING(1,"正在运行"),
    PROCESS_RUNTIME_SUCCESS(2,"成功"),
    PROCESS_RUNTIME_FAILED(3,"失败");


    private Integer code;

    private String name;


    ProcessRuntimeStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ProcessRuntimeStatusEnum getByCode(Integer code) {
        for (ProcessRuntimeStatusEnum v : ProcessRuntimeStatusEnum.values()) {
            if (!CheckParam.isNull(code) && v.getCode() == (code)) {
                return v;
            }
        }
        return null;
    }
    public static ProcessRuntimeStatusEnum getByName(String name) {
        if (CheckParam.isNull(name)) {
            return null;
        }
        for (ProcessRuntimeStatusEnum v : ProcessRuntimeStatusEnum.values()) {
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
    public static Boolean verifyIsExist(Integer code){
        return !CheckParam.isNull(getByCode(code));
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
