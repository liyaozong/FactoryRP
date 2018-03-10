package tech.yozo.factoryrp.enums.inspection;

import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.resp.DicEnumResp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 点检计划->设备异常枚举值  异常情况描述 1无异常2有异常
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/5
 * @description
 */
public enum SpotInspectionDeviceAbnormalEnums {

    SPOT_INSPECTION_ITEMS_NORMAL("1","无异常"),
    SPOT_INSPECTION_ITEMS_ABNORMAL("2","有异常");


    private String code;

    private String name;


    SpotInspectionDeviceAbnormalEnums(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SpotInspectionDeviceAbnormalEnums getByCode(String code) {
        for (SpotInspectionDeviceAbnormalEnums v : SpotInspectionDeviceAbnormalEnums.values()) {
            if (!CheckParam.isNull(code) && v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
    public static SpotInspectionDeviceAbnormalEnums getByName(String name) {
        if (CheckParam.isNull(name)) {
            return null;
        }
        for (SpotInspectionDeviceAbnormalEnums v : SpotInspectionDeviceAbnormalEnums.values()) {
            if (v.name().equalsIgnoreCase(name)) {
                return v;
            }
        }
        return null;
    }


    /**
     * 拿到所有点检项目记录方式枚举
     * @return
     */
    public static List<DicEnumResp> getAllCodesAndName(){
        List<DicEnumResp> list = new ArrayList<>();
        Arrays.stream(SpotInspectionDeviceAbnormalEnums.values()).forEach(e1 -> {
            DicEnumResp errorCodeResp = new DicEnumResp();

            errorCodeResp.setCode(e1.getCode());
            errorCodeResp.setName(e1.getName());

            list.add(errorCodeResp);
        });
        return list;
    }

    /**
     * 判断是code是否存在
     * @param code
     * @return
     */
    public static Boolean verifyIsExist(String code){
        return !CheckParam.isNull(getByCode(code));
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
