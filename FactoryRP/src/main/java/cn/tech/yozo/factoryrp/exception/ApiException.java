package cn.tech.yozo.factoryrp.exception;

import cn.tech.yozo.factoryrp.utils.ErrorCode;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description Api异常
 */
public class ApiException extends RuntimeException{


    private static final long serialVersionUID = -234801665080134237L;


    private String errorCode;

    private String errorMessage;

    public ApiException(ErrorCode errorCode) {
        super(errorCode.getCode()+":"+errorCode.getMessage());
        this.errorCode = errorCode.getCode();
        this.errorMessage = errorCode.getMessage();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
