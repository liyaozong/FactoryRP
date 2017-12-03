package cn.tech.yozo.factoryrp.enums;

import cn.tech.yozo.factoryrp.utils.CheckParam;

/**
 * 故障状态（0:需要立刻维修；1:维修中；2:维修完成，待验证；3:验证完成）
 * @author chenxiang
 * @create 2017-12-03 下午5:02
 **/
public enum TroubleStatusEnum {
    NEED_REPAIR(0,"需要立刻维修"),
    REPAIRING(1,"维修中"),
    REPAIRED(2,"结束待验证"),
    VALIDATED(3,"验证完成");

    private Integer code;
    private String name;

    TroubleStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static TroubleStatusEnum getByCode(String code) {
        for (TroubleStatusEnum v : TroubleStatusEnum.values()) {
            if (!CheckParam.isNull(code)) {
                return v;
            }
        }
        return null;
    }
    public static TroubleStatusEnum getByName(String name) {
        if (CheckParam.isNull(name)) {
            return null;
        }
        for (TroubleStatusEnum v : TroubleStatusEnum.values()) {
            if (v.name().equalsIgnoreCase(name)) {
                return v;
            }
        }
        return null;
    }
}
