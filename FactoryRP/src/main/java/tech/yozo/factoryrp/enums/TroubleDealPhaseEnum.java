package tech.yozo.factoryrp.enums;

import tech.yozo.factoryrp.utils.CheckParam;

/**
 * 故障待处理阶段
 * @author chenxiang
 * @create 2018-01-16 上午9:23
 **/
public enum TroubleDealPhaseEnum {

    WAIT_AUDIT(1,"待审核"),
    WAIT_VALIDATE(2,"维修完成待验证");

    private Integer code;
    private String name;

    TroubleDealPhaseEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static TroubleDealPhaseEnum getByCode(Integer code) {
        for (TroubleDealPhaseEnum v : TroubleDealPhaseEnum.values()) {
            if (!CheckParam.isNull(code) && v.getCode() == code) {
                return v;
            }
        }
        return null;
    }
    public static TroubleDealPhaseEnum getByName(String name) {
        if (CheckParam.isNull(name)) {
            return null;
        }
        for (TroubleDealPhaseEnum v : TroubleDealPhaseEnum.values()) {
            if (v.name().equalsIgnoreCase(name)) {
                return v;
            }
        }
        return null;
    }
}
