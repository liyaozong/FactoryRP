package cn.tech.yozo.factoryrp.vo.resp.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/23
 * @description
 */
@Data
@ApiModel
public class AuthUser implements Serializable{


    private static final long serialVersionUID = 1395397653646348018L;

    /**
     * token值
     */
    @ApiModelProperty(value = "64位token值",required = true,notes = "64位token值",example = "kFywiZRPCInHqeOkvmYLZ5sQ6sG9qQJ4lHQt5peT22mT4ByEt9QOmDRiIVWv6494")
    private String token;


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

    /**
     * 登陆后用户能看到的菜单List
     */
    @ApiModelProperty(value = "登陆后用户能看到的菜单List",required = true,notes = "登陆后用户能看到的菜单List")
    private List<AuthUserMenu> authUserMenuList;

}
