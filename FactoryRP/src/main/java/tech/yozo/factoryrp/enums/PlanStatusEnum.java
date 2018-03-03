package tech.yozo.factoryrp.enums;

import tech.yozo.factoryrp.utils.CheckParam;

/**
 * @author chenxiang
 * @create 2018-03-03 上午9:40
 **/
public enum PlanStatusEnum {

    TODAY(1,"今日可执行"),
    TODAY_NEW(2,"今日新增"),
    TODAY_EXPIRED(3,"今日到期"),
    TOMO(4,"明日可执行"),
    TOMO_NEW(5,"明日新增"),
    TOMO_EXPIRED(6,"明日到期"),
    EXPIRED(7,"已过期");

    private Integer code;
    private String name;

    PlanStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static PlanStatusEnum getByCode(Integer code) {
        for (PlanStatusEnum v : PlanStatusEnum.values()) {
            if (!CheckParam.isNull(code) && v.getCode() == code) {
                return v;
            }
        }
        return null;
    }
    public static PlanStatusEnum getByName(String name) {
        if (CheckParam.isNull(name)) {
            return null;
        }
        for (PlanStatusEnum v : PlanStatusEnum.values()) {
            if (v.name().equalsIgnoreCase(name)) {
                return v;
            }
        }
        return null;
    }
}
