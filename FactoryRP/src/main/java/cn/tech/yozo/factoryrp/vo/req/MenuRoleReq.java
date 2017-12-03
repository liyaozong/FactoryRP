package cn.tech.yozo.factoryrp.vo.req;

import cn.tech.yozo.factoryrp.vo.base.ApiCorporateIdentifyRequest;
import cn.tech.yozo.factoryrp.vo.validation.NotEmpty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/21
 * @description
 */
@ApiModel
public class MenuRoleReq  extends ApiCorporateIdentifyRequest implements Serializable {


    private static final long serialVersionUID = -8035852876830134333L;



    /**
     * 角色标识
     */
    @ApiModelProperty(value = "角色标识",required = true,notes = "角色标识",example = "3")
    //@NotEmpty(message = "角色code不能为空")
    private Long roleId;

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID",required = true,notes = "菜单ID",example = "3")
    //@NotEmpty(message = "菜单ID不能为空")
    private Long menuId;


    /**
     * 备注描述
     */
    @ApiModelProperty(value = "备注描述",required = true,notes = "备注描述",example = "描述1")
    @NotEmpty(message = "备注描述不能为空")
    private String remark;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
