package tech.yozo.factoryrp.utils;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description
 */
public enum ErrorCode {


    SUCCESS("000000","成功"),
    ERROR("999999","业务处理异常"),
    LOGIN_FAILED("000001","登陆失败(用户名密码错误)"),
    NEED_LOGIN("000002","请登录"),
    NO_PERMISSION("000003","没有权限"),
    PARAM_ERROR("000004","参数格式错误"),
    CORPORATENAME__REPETED_ERROR("000005","企业名称已经存在"),
    ROLE__REPETED_ERROR("000006","当前企业的角色已经存在"),
    MENU__REPETED_ERROR("000007","当前企业的菜单已经存在"),
    MENUROLE__REPETED_ERROR("000008","当前企业的菜单-角色已经存在"),
    USERROLE__REPETED_ERROR("000009","当前企业的用户-角色已经存在"),
    CORPORATE__NOTEXIST_ERROR("0000010","企业信息不存在"),
    CORPORATE_USER__REPET_ERROR("0000011","当前企业已经存在此用户"),
    CORPORATE_SPAREPARTS__REPET_ERROR("0000012","备件已经存在");


    private String code;

    private String message;

    private ErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
