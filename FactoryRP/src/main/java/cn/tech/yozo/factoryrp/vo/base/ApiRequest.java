package cn.tech.yozo.factoryrp.vo.base;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description
 */
public class ApiRequest implements Serializable{

    private static final long serialVersionUID = -5680237280219714877L;

    /**
     * 请求序列号
     */
    @ApiModelProperty(value = "请求流水号",required = true,notes = "请求流水号",example = "931189104492675072")
    private String requestSeqNo;

    @ApiModelProperty(value = "请求时间",required = true,notes = "请求时间",example = "yyyy-MM-dd HH:mm:ss格式，如")
    private String requestTime;

    public String getRequestSeqNo() {
        return requestSeqNo;
    }

    public void setRequestSeqNo(String requestSeqNo) {
        this.requestSeqNo = requestSeqNo;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }
}
