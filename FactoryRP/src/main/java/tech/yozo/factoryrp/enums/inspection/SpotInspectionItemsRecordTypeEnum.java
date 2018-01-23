package tech.yozo.factoryrp.enums.inspection;

import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.resp.DicEnumResp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * 点检项目记录方式枚举
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/22
 * @description
 */
public enum SpotInspectionItemsRecordTypeEnum {

    SPOT_INSPECTION_ITEMS_RECORD_TYPE_ENUM_OPTIONS("options","勾选"),
    SPOT_INSPECTION_ITEMS_RECORD_TYPE_ENUM_TABLE("table","列表"),
    SPOT_INSPECTION_ITEMS_RECORD_TYPE_ENUM_NUMBER("number","数字"),
    SPOT_INSPECTION_ITEMS_RECORD_TYPE_ENUM_VERBAL_DESCRIPTION("verbal_description","文字描述");


    private String code;

    private String name;


    SpotInspectionItemsRecordTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SpotInspectionItemsRecordTypeEnum getByCode(String code) {
        for (SpotInspectionItemsRecordTypeEnum v : SpotInspectionItemsRecordTypeEnum.values()) {
            if (!CheckParam.isNull(code) && v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
    public static SpotInspectionItemsRecordTypeEnum getByName(String name) {
        if (CheckParam.isNull(name)) {
            return null;
        }
        for (SpotInspectionItemsRecordTypeEnum v : SpotInspectionItemsRecordTypeEnum.values()) {
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
        Arrays.stream(SpotInspectionItemsRecordTypeEnum.values()).forEach(e1 -> {
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
