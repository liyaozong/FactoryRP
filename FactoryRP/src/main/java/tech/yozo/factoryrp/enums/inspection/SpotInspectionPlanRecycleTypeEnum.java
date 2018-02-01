package tech.yozo.factoryrp.enums.inspection;

import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.resp.DicEnumResp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 巡检计划执行时间类型循环类型枚举
 * @author created by Singer email:313402703@qq.com
 * @time 2018/2/1
 * @description
 */
public enum SpotInspectionPlanRecycleTypeEnum {


    SPOT_INSPECTION_PLAN_RECYCLE_TYPE_DAY("days","天"),
    SPOT_INSPECTION_PLAN_RECYCLE_TYPE_YEAR("year","年"),
    SPOT_INSPECTION_PLAN_RECYCLE_TYPE_MONTH("month","月"),
    SPOT_INSPECTION_PLAN_RECYCLE_TYPE_HOUR("hour","小时");


    private String code;

    private String name;


    SpotInspectionPlanRecycleTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static SpotInspectionPlanRecycleTypeEnum getByCode(String code) {
        for (SpotInspectionPlanRecycleTypeEnum v : SpotInspectionPlanRecycleTypeEnum.values()) {
            if (!CheckParam.isNull(code) && v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
    public static SpotInspectionPlanRecycleTypeEnum getByName(String name) {
        if (CheckParam.isNull(name)) {
            return null;
        }
        for (SpotInspectionPlanRecycleTypeEnum v : SpotInspectionPlanRecycleTypeEnum.values()) {
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
        Arrays.stream(SpotInspectionPlanRecycleTypeEnum.values()).forEach(e1 -> {
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
