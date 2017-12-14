package tech.yozo.factoryrp.enums;

import tech.yozo.factoryrp.utils.CheckParam;

/**
 * @author chenxiang
 * @create 2017-12-04 下午11:00
 **/
public enum TroubleLevelEnum {

    HIGH(1,"紧急"),
    NORMAL(2,"一般"),
    OTHER(3,"其它");

    private Integer code;
    private String name;

    TroubleLevelEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static TroubleLevelEnum getByCode(Integer code) {
        for (TroubleLevelEnum v : TroubleLevelEnum.values()) {
            if (!CheckParam.isNull(code) && v.getCode() == code) {
                return v;
            }
        }
        return null;
    }
    public static TroubleLevelEnum getByName(String name) {
        if (CheckParam.isNull(name)) {
            return null;
        }
        for (TroubleLevelEnum v : TroubleLevelEnum.values()) {
            if (v.name().equalsIgnoreCase(name)) {
                return v;
            }
        }
        return null;
    }
}
