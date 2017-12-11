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
 * @time 2017/11/20
 * @description
 */
@Data
@ApiModel
public class RoleReq extends ApiRequest implements Serializable{

    private static final long serialVersionUID = 4657774028335368764L;

    /**
     * 角色code
     */
    @ApiModelProperty(value = "角色code",required = true,notes = "角色code",example = "123")
    @NotEmpty(message = "角色code不能为空")
    private String roleCode;

    /**
     * 角色名称
     */
    @ApiModelProperty(value = "角色名称",required = true,notes = "角色名称",example = "管理员角色")
    @NotEmpty(message = "角色名称不能为空")
    private String roleName;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述",required = true,notes = "角色描述",example = "alibaba的管理员角色")
    @NotEmpty(message = "角色描述不能为空")
    private String roleDescription;

    /**
     * 是否启用 1启用2不启用
     */
    @ApiModelProperty(value = "是否启用 1启用2不启用",required = true,notes = "是否启用 1启用2不启用",example = "1")
    private Integer enableStatus;


}
