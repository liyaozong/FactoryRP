package cn.tech.yozo.factoryrp.exception;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description 系统业务异常类
 */
public class BussinessException extends RuntimeException{


    private static final long serialVersionUID = 1656492381432429706L;

    private String errorCode;

    private String errorMessage;

    public BussinessException(String errorCode, String errorMessage) {
        super(errorCode +":"+errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
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
