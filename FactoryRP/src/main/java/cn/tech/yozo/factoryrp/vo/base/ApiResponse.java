package cn.tech.yozo.factoryrp.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description
 */
@ApiModel
public class ApiResponse<E> implements Serializable{

    private static final long serialVersionUID = -8099598967725340002L;

    /**
     * 请求序列号
     */
    @ApiModelProperty(value = "请求流水号",required = true,notes = "请求流水号",example = "931189104492675072")
    private String requestSeqNo;

    /**
     * 错误码
     */
    @ApiModelProperty(value = "错误码",required = true,notes = "错误码",example = "000000")
    private String errorCode;

    /**
     * 错误信息
     */
    @ApiModelProperty(value = "错误原因",required = true,notes = "错误原因",example = "登陆成功")
    private String errorMessage;


    /**
     *请求时间
     */
    @ApiModelProperty(value = "请求时间",required = true,notes = "请求时间",example = "yyyy-MM-dd HH:mm:ss格式，如2017-11-17 00:15:12")
    private String requestTime;


    /**
     * 接口返回时间
     */
    @ApiModelProperty(value = "接口返回时间",required = true,notes = "接口返回时间",example = "yyyy-MM-dd HH:mm:ss格式，如2017-11-17 00:15:12")
    private String responseTime;

    /**
     * 返回的数据
     */
    private E data;

    public ApiResponse() {
    }

    public ApiResponse(String requestSeqNo, String errorCode, String errorMessage, E data) {
        this.requestSeqNo = requestSeqNo;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public String getRequestSeqNo() {
        return requestSeqNo;
    }

    public void setRequestSeqNo(String requestSeqNo) {
        this.requestSeqNo = requestSeqNo;
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

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}