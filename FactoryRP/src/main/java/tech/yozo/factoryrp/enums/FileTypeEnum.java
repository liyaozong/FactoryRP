package tech.yozo.factoryrp.enums;

import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.resp.DicEnumResp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件类型枚举 对应OSS文件存储的文件夹位置
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/8
 * @description
 */
public enum FileTypeEnum {


    INSPECTION_IMAGE("inspect","点巡检"),
    MAINTAIN_IMAGE("inspect","维修"),
    TROUBLE_IMAGE("trouble","故障"),
    DOCUMENT("document","文档");

    private String code;

    private String name;


    FileTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static FileTypeEnum getByCode(String code) {
        for (FileTypeEnum v : FileTypeEnum.values()) {
            if (!CheckParam.isNull(code) && v.getCode().equals(code)) {
                return v;
            }
        }
        return null;
    }
    public static FileTypeEnum getByName(String name) {
        if (CheckParam.isNull(name)) {
            return null;
        }
        for (FileTypeEnum v : FileTypeEnum.values()) {
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
        Arrays.stream(FileTypeEnum.values()).forEach(e1 -> {
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
