package cn.tech.yozo.factoryrp.enums;

import cn.tech.yozo.factoryrp.utils.CheckParam;

/**
 * 设备状态（1:停机待修；2:带病运行；3:其它）
 * @author chenxiang
 * @create 2017-12-03 下午5:08
 **/
public enum DeviceStatusEnum {
    STOPPED(1,"停机待修"),
    RUNNING(2,"带病运行"),
    OTHER(3,"其它");

    private Integer code;
    private String name;

    DeviceStatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static DeviceStatusEnum getByCode(String code) {
        for (DeviceStatusEnum v : DeviceStatusEnum.values()) {
            if (!CheckParam.isNull(code)) {
                return v;
            }
        }
        return null;
    }
    public static DeviceStatusEnum getByName(String name) {
        if (CheckParam.isNull(name)) {
            return null;
        }
        for (DeviceStatusEnum v : DeviceStatusEnum.values()) {
            if (v.name().equalsIgnoreCase(name)) {
                return v;
            }
        }
        return null;
    }
}
