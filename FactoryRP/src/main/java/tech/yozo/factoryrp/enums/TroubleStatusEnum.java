package tech.yozo.factoryrp.enums;

import tech.yozo.factoryrp.utils.CheckParam;

/**
 * 故障状态（0:待审核;1:需要立刻维修；2:维修中；3:维修完成，待验证；4:验证完成）
 * @author chenxiang
 * @create 2017-12-03 下午5:02
 **/
public enum TroubleStatusEnum {
    WAIT_AUDIT(0,"待审核"),
    NEED_REPAIR(1,"等待维修"),
    REPAIRING(2,"维修中"),
    REPAIRED(3,"结束待验证"),
    VALIDATED(4,"验证完成");

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

    public static TroubleStatusEnum getByCode(Integer code) {
        for (TroubleStatusEnum v : TroubleStatusEnum.values()) {
            if (!CheckParam.isNull(code) && v.getCode()==code) {
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
