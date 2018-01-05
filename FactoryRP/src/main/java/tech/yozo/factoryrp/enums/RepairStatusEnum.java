package tech.yozo.factoryrp.enums;

import tech.yozo.factoryrp.utils.CheckParam;

/**
 * @author chenxiang
 * @create 2017-12-15 下午11:22
 **/
public enum RepairStatusEnum {


    REPAIRING(0,"正在维修"),
    DONE(1,"已完成"),
    WAIT_BUY_SPAIR(2,"待采购件"),
    WAIT_MAKE_SPAIN(3,"待加工件"),
    WAIT_STOP(4,"待停机维修"),
    STOPED(5,"停机待修"),
    CAN_NOT_REPAIR(6,"无法修复"),
    WAIT_TRANS_TO_OUT(7,"待转外委维修");

    private Integer code;
    private String name;

    RepairStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static RepairStatusEnum getByCode(Integer code) {
        for (RepairStatusEnum v : RepairStatusEnum.values()) {
            if (!CheckParam.isNull(code) && v.getCode() == code) {
                return v;
            }
        }
        return null;
    }
    public static RepairStatusEnum getByName(String name) {
        if (CheckParam.isNull(name)) {
            return null;
        }
        for (RepairStatusEnum v : RepairStatusEnum.values()) {
            if (v.name().equalsIgnoreCase(name)) {
                return v;
            }
        }
        return null;
    }
}
