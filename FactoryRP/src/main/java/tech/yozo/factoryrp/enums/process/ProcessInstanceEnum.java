package tech.yozo.factoryrp.enums.process;

import tech.yozo.factoryrp.utils.CheckParam;

/**
 * 流程实例状态
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/28
 * @description
 */
public enum ProcessInstanceEnum {


    PROCESS_BEGAIN(1,"流程开启"),
    PROCESSINT(2,"正在进行"),
    PROCESS_NORMAL_TERMINATION(3,"正常结束"),
    PROCESS_ERMINATION(4,"终止流程"),
    PROCESS_CANCELED(5,"流程取消");



    private Integer code;

    private String name;

    ProcessInstanceEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ProcessInstanceEnum getByCode(String code) {
        for (ProcessInstanceEnum v : ProcessInstanceEnum.values()) {
            if (!CheckParam.isNull(code) && v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
    public static ProcessInstanceEnum getByName(String name) {
        if (CheckParam.isNull(name)) {
            return null;
        }
        for (ProcessInstanceEnum v : ProcessInstanceEnum.values()) {
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
