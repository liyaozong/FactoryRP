package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.ApiRequest;
import tech.yozo.factoryrp.vo.validation.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/21
 * @description
 */
@ApiModel
@Data
public class MenuRoleReq  extends ApiRequest implements Serializable {


    private static final long serialVersionUID = -8035852876830134333L;

    /**
     * 角色标识
     */
    @ApiModelProperty(value = "角色标识",required = true,notes = "角色标识",example = "3")
    private Long roleId;

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID",required = true,notes = "菜单ID",example = "3")
    private List<Long> menuIdList;


    /**
     * 备注描述
     */
    @ApiModelProperty(value = "备注描述",required = true,notes = "备注描述",example = "描述1")
    @NotEmpty(message = "备注描述不能为空")
    private String remark;

}
