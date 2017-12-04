package tech.yozo.factoryrp.vo.resp.user;

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
public class UserAddResp implements Serializable {


    private static final long serialVersionUID = 305887712470325647L;

    /**
     * 企业标识符
     */
    @ApiModelProperty(value = "企业标识符",required = true,notes = "企业标识符",example = "321321321321")
    private Long corporateIdentify;


    /**
     * 用户Id
     */
    @ApiModelProperty(value = "用户Id",required = true,notes = "用户Id",example = "3123213321")
    private Long userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名",required = true,notes = "用户名",example = "用户名")
    private String userName;


}
