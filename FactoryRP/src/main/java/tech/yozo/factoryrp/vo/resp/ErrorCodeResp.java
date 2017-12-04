package tech.yozo.factoryrp.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 错误信息返回
 *
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description
 */
@ApiModel
public class ErrorCodeResp implements Serializable{

    private static final long serialVersionUID = 6449136500210310724L;


    @ApiModelProperty(value = "错误码",required = true,notes = "错误码",example = "000000")
    private String code;

    @ApiModelProperty(value = "错误原因",required = true,notes = "错误原因",example = "登陆成功")
    private String errorMessage;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}




