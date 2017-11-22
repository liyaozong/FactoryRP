package cn.tech.yozo.factoryrp.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/21
 * @description
 */
@Data
@ApiModel
public class RoleMenuQueryResp implements Serializable {


    private static final long serialVersionUID = -2805922863753105896L;


    /**
     * 当前角色id
     */
    @ApiModelProperty(value = "当前角色id",required = true,notes = "当前角色id",example = "3")
    private Long roleId;

    @ApiModelProperty(value = "企业唯一标识",required = true,notes = "企业唯一标识",example = "32132132132213")
    private String corporateIdentify;

    /**
     * 菜单List
     */
    @ApiModelProperty(value = "菜单List",required = true,notes = "菜单List")
    private List<MenuQueryResp> menuList;




}
