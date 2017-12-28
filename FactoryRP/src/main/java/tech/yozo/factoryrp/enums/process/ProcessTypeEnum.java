package tech.yozo.factoryrp.enums.process;

/**
 * 流程类型枚举
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/28
 * @description
 */
public enum ProcessTypeEnum {

    PROCESS_RUNTIME_PROCESSING(1,"正在运行"),
    PROCESS_RUNTIME_SUCCESS(2,"成功"),
    PROCESS_RUNTIME_FAILED(3,"失败");


    private Integer code;

    private String name;

    ProcessTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
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
