package tech.yozo.factoryrp.utils;

import tech.yozo.factoryrp.vo.resp.ErrorCodeResp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description
 */
public enum ErrorCode {


    SUCCESS("000000","成功"),
    ERROR("999999","业务处理异常"),
    NO_PERMISSION("000003","没有权限"),
    PARAM_ERROR("000004","参数格式错误"),
    CORPORATENAME__REPETED_ERROR("000005","企业名称已经存在"),
    ROLE__REPETED_ERROR("000006","当前企业的角色已经存在"),
    MENU__REPETED_ERROR("000007","当前企业的菜单已经存在"),
    MENUROLE__REPETED_ERROR("000008","当前企业的菜单-角色已经存在"),
    USERROLE__REPETED_ERROR("000009","当前企业的用户-角色已经存在"),
    CORPORATE__NOTEXIST_ERROR("0000010","企业信息不存在"),
    CORPORATE_USER__REPET_ERROR("0000011","当前企业已经存在此用户"),
    CORPORATE_SPAREPARTS__REPET_ERROR("0000012","备件已经存在"),
    CORPORATE_DEVICEPARAM__REPET_ERROR("0000013","设备参数已经存在"),
    DEVICEPARAMDIC_CODE_NOTEXIST_ERROR("0000014","设备参数枚举值错误"),
    SYSTEM_DIC_PARAM_REPET_ERROR("0000015","系统字典重复"),
    LOGIN_FAILED("000666","登陆失败(用户名密码错误)"),
    NEED_LOGIN("000667","请登录"),
    USER_NOTEXIST_ERROR("0000016","用户不存在"),
    ROLE_NOTEXIST_ERROR("0000017","角色不存在"),
    MENU_NOTEXIST_ERROR("0000018","菜单不存在"),
    CURRENTUSER_OPERATESELF_ERROR("0000019","菜单不存在"),
    PROCESS_NAME_REPET_ERROR("0000020","流程已经存在"),
    PROCESS_NOT_EXIST_ERROR("0000021","流程不存在"),
    PROCESS_INSTANCE_NOT_EXIST_ERROR("0000022","流程实例不存在，开启流程失败"),
    CORPORATE_USER__NOTEXIST_ERROR("0000023","用户不存在"),
    SPOTINSPECTIONSTANDARD_REPET_ERROR("0000024","点检标准重复"),
    SPOTINSPECTIONPLAN_REPET_ERROR("0000025","点检计划重复"),
    TIMEPARSE_ERROR("0000026","出现时间转换异常"),
    NO_DEVICEINFO_ERROR("0000027","设备信息不存在"),
    NO_SPOTINSPECTIONPLAN__EXIST_ERROR("0000028","巡检计划不存在"),
    PROCESS_DIC_NOTEXIST_ERROR("0000029","流程枚举参数不存在"),
    SPOTINSPECTION_RECORD_NOTEXIST_ERROR("0000030","巡检记录不存在"),
    REQUEST_FILE_NOT_EXIST("0000031","上传文件请求错误"),
    UNKONW_IMAGE_TYPE_REEOR("0000032","错误的文件类型"),
    FILE_PARSE_EXCEPTION("0000033","文件解析异常"),
    OLD_PASSWORD_INCORRECT("0000034","旧密码输入错误");


    private String code;

    private String message;

    private ErrorCode(String code,String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    /**
     * 拿到所有的错误码和描述
     * @return
     */
    public static List<ErrorCodeResp>  getAllCodesAndMessage(){
        List<ErrorCodeResp> list = new ArrayList<>();
        Arrays.stream(ErrorCode.values()).forEach(e1 -> {
            ErrorCodeResp errorCodeResp = new ErrorCodeResp();

            errorCodeResp.setCode(e1.getCode());
            errorCodeResp.setErrorMessage(e1.getMessage());

            list.add(errorCodeResp);
        });


        return list;
    }


}
