package cn.tech.yozo.factoryrp.utils;

import cn.tech.yozo.factoryrp.vo.resp.ErrorCodeResp;

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
    LOGIN_FAILED("000001","登陆失败(用户名密码错误)"),
    NEED_LOGIN("000002","请登录"),
    NO_PERMISSION("000003","没有权限"),
    PARAM_ERROR("000004","参数格式错误"),
    CORPORATENAME__REPETED_ERROR("000005","企业名称已经存在"),
    ROLE__REPETED_ERROR("000006","当前企业的角色已经存在");


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
