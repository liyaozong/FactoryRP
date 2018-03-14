package tech.yozo.factoryrp.enums;

import tech.yozo.factoryrp.utils.CheckParam;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/13
 * @description
 */
public enum HuJinResultEnum {

    HUJIN_NETCHECK_00("00","互金联网核查--公民身份号码与姓名一致，且存在照片联网核查成功","070300"),
    HUJIN_NETCHECK_01("01","互金联网核查--公民身份号码与姓名一致，但不存在照片","070301"),
    HUJIN_NETCHECK_02("02","互金联网核查--公民身份号码存在，但与姓名不匹配","070302"),
    HUJIN_NETCHECK_03("03","互金联网核查--公民身份号码不存在","070302"),
    HUJIN_NETCHECK_04("04","互金联网核查--其他错误","070304"),
    HUJIN_NETCHECK_05("05","互金联网核查--输入的参数错误","070305");

    /**
     * 互金返回的原值Code
     */
    private String hujinCode;


    /**
     * 结果映射描述
     */
    private String desc;


    /**
     * openAPI映射互金结果code
     */
    private String code;


    HuJinResultEnum(String hujinCode, String desc, String code) {
        this.hujinCode = hujinCode;
        this.desc = desc;
        this.code = code;
    }

    public String getHujinCode() {
        return hujinCode;
    }

    public void setHujinCode(String hujinCode) {
        this.hujinCode = hujinCode;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public static void main(String[] args) {

       /* for (HuJinResultEnum v : HuJinResultEnum.values()) {
            System.out.println(v.getCode());
            System.out.println(v.getDesc());
            System.out.println(v.getHujinCode());
        }*/

       String hujinCode = "01";

        System.out.println(getByHuJinCode(hujinCode).getCode());

    }

    public static HuJinResultEnum getByHuJinCode(String  hujinCode) {
        for (HuJinResultEnum v : HuJinResultEnum.values()) {
            if (!CheckParam.isNull(hujinCode) && v.getHujinCode().equals(hujinCode)) {
                return v;
            }
        }
        return null;
    }
    public static HuJinResultEnum getByName(String name) {
        if (CheckParam.isNull(name)) {
            return null;
        }
        for (HuJinResultEnum v : HuJinResultEnum.values()) {
            if (v.name().equalsIgnoreCase(name)) {
                return v;
            }
        }
        return null;
    }

}
