package tech.yozo.factoryrp.enums.device;

import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.resp.DicEnumResp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public static DeviceStatusEnum getByCode(Integer code) {
        for (DeviceStatusEnum v : DeviceStatusEnum.values()) {
            if (!CheckParam.isNull(code) && v.getCode() == code) {
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


    /**
     * 拿到所有设备状态
     * @return
     */
    public static List<DicEnumResp> getAllCodesAndName(){
        List<DicEnumResp> list = new ArrayList<>();
        Arrays.stream(DeviceStatusEnum.values()).forEach(e1 -> {
            DicEnumResp errorCodeResp = new DicEnumResp();

            errorCodeResp.setCode(String.valueOf(e1.getCode()));
            errorCodeResp.setName(e1.getName());

            list.add(errorCodeResp);
        });
        return list;
    }

}
