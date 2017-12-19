package tech.yozo.factoryrp.vo.req;

import tech.yozo.factoryrp.vo.base.ApiCorporateIdentifyRequest;
import tech.yozo.factoryrp.vo.base.ApiRequest;
import tech.yozo.factoryrp.vo.validation.NotEmpty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/24
 * @description
 */
@ApiModel
@Data
public class UserAddReq extends ApiRequest implements Serializable {

    private static final long serialVersionUID = 6292757720174616806L;


    /**
     * 企业标识符
     *//*
    @ApiModelProperty(value = "企业标识符",required = true,notes = "企业标识符",example = "321321321321")
    private Long corporateIdentify;*/

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",required = true,notes = "用户名",example = "用户名")
    @NotEmpty(message = "用户名不能为空")
    private String userName;


    /**
     * 密码
     */
    @ApiModelProperty(value = "密码",required = true,notes = "密码",example = "123")
    @NotEmpty(message = "密码不能为空")
    private String password;

}
