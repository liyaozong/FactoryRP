package tech.yozo.factoryrp.enums.auth;

import tech.yozo.factoryrp.utils.CheckParam;

/**
 * 角色代码枚举
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/8
 * @description
 */
public enum RoleCodeEnum {


    MAINTENANCE_PERSONNEL("MAINTENANCE_PERSONNEL","维修人员");

    private String code;
    private String name;


    public static RoleCodeEnum getByCode(String code) {
        for (RoleCodeEnum v : RoleCodeEnum.values()) {
            if (!CheckParam.isNull(code) && v.getCode() == code) {
                return v;
            }
        }
        return null;
    }
    public static RoleCodeEnum getByName(String name) {
        if (CheckParam.isNull(name)) {
            return null;
        }
        for (RoleCodeEnum v : RoleCodeEnum.values()) {
            if (v.name().equalsIgnoreCase(name)) {
                return v;
            }
        }
        return null;
    }

    RoleCodeEnum(String code, String name) {
        this.code = code;
        this.name = name;
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
