package tech.yozo.factoryrp.vo.resp;

import lombok.Data;

@Data
public class RoleResp {
//    @ApiModelProperty(value = "角色Id",required = true,notes = "角色Id",example = "角色Id")
    private String roleId;

    /**
     * 角色code
     */
//    @ApiModelProperty(value = "角色code",required = true,notes = "角色code",example = "123")
    private String roleCode;

    /**
     * 角色名称
     */
//    @ApiModelProperty(value = "角色名称",required = true,notes = "角色名称",example = "管理员")
    private String roleName;

    /**
     * 角色描述
     */
//    @ApiModelProperty(value = "角色描述",required = true,notes = "角色描述",example = "角色1")
    private String roleDescription;
}
