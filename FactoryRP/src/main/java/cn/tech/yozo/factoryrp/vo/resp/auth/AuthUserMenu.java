package cn.tech.yozo.factoryrp.vo.resp.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/23
 * @description 用户登陆成功之后 返回给前端的用户能看到哪些菜单
 */
@Data
@ApiModel
public class AuthUserMenu implements Serializable {


    private static final long serialVersionUID = 1152786986448342885L;

    /**
     * 菜单的主键Id
     */
    @ApiModelProperty(value = "菜单的主键Id",required = true,notes = "菜单的主键Id",example = "1")
    private Long id;

    /**
     * 父级菜单Id
     * 若为0则为一级菜单
     */
    @ApiModelProperty(value = "父级菜单Id，若为0则为一级菜单",required = true,notes = "父级菜单Id，若为0则为一级菜单",example = "0")
    private Long parentId;

    /**
     * 菜单访问URL
     */
    @ApiModelProperty(value = "菜单访问URL",required = true,notes = "菜单访问URL",example = "/api/user/list")
    private String url;


    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称",required = true,notes = "菜单名称",example = "备件管理")
    private String name;


    /**
     * 菜单排序号
     */
    @ApiModelProperty(value = "菜单排序号",required = true,notes = "菜单排序号",example = "1")
    private Integer orderNumber;


    /**
     * 菜单备注
     */
    @ApiModelProperty(value = "菜单备注",required = true,notes = "菜单备注",example = "备件管理")
    private String remark;




}
