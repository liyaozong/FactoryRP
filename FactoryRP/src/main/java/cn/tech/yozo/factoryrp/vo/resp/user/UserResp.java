package cn.tech.yozo.factoryrp.vo.resp.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/22
 * @description
 */
@ApiModel
@Data
public class UserResp implements Serializable {

    private static final long serialVersionUID = 1533262341606217841L;

    /**
     * 用户ID 工具类生成
     */
    @ApiModelProperty(value = "用户ID",required = true,notes = "用户ID",example = "1")
    private Long userId;

    /**
     * 登陆用户名
     */
    @ApiModelProperty(value = "登陆用户名",required = true,notes = "登陆用户名",example = "张三")
    private String userName;

    /**
     * 用户角色标识
     *//*
    @ApiModelProperty(value = "用户角色标识",required = true,notes = "用户角色标识",example = "1")
    private Long roleId;*/


}
