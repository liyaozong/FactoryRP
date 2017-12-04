package tech.yozo.factoryrp.vo.resp.menu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/21
 * @description
 */
@Data
@ApiModel
public class MenuRoleResp implements Serializable {

    private static final long serialVersionUID = -818436320165973738L;



    @ApiModelProperty(value = "id",required = true,notes = "id",example = "3")
    private Long id;

    /**
     * 角色标识
     */

    @ApiModelProperty(value = "角色标识",required = true,notes = "角色标识",example = "3")
    private Long roleId;

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID",required = true,notes = "菜单ID",example = "3")
    private Long menuId;


    /**
     * 备注描述
     */
    @ApiModelProperty(value = "备注描述",required = true,notes = "备注描述",example = "描述1")
    private String remark;


    @ApiModelProperty(value = "企业唯一标识",required = true,notes = "企业唯一标识",example = "32132132132213")
    private String corporateIdentify;


}
