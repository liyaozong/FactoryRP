package cn.tech.yozo.factoryrp.vo.resp.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description 角色返回Vo
 */
@ApiModel
public class RoleResp implements Serializable{


    private static final long serialVersionUID = 3925643967712390621L;

    @ApiModelProperty(value = "角色Id",required = true,notes = "角色Id",example = "角色Id")
    private String roleId;

    /**
     * 角色code
     */
    @ApiModelProperty(value = "角色code",required = true,notes = "角色code",example = "123")
    private String roleCode;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称",required = true,notes = "角色名称",example = "管理员")
    private String roleName;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述",required = true,notes = "角色描述",example = "角色1")
    private String roleDescription;

    /**
     * 是否启用 1启用2不启用
     */
    @ApiModelProperty(value = "是否启用 1启用2不启用 ",required = true,notes = "角色code",example = "1")
    private Integer enableStatus;


    @ApiModelProperty(value = "企业唯一标识",required = true,notes = "企业唯一标识",example = "32132132132213")
    private Long corporateIdentify;


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public Long getCorporateIdentify() {
        return corporateIdentify;
    }

    public void setCorporateIdentify(Long corporateIdentify) {
        this.corporateIdentify = corporateIdentify;
    }
}
