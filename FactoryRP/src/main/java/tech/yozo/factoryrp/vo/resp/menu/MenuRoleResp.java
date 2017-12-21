package tech.yozo.factoryrp.vo.resp.menu;

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
public class MenuRoleResp implements Serializable {

    private static final long serialVersionUID = -818436320165973738L;



    @ApiModelProperty(value = "角色-菜单关系ID集合",required = true,notes = "角色-菜单关系ID集合",example = "[3,4,5]")
    private List<Long> ids;

    /**
     * 角色标识
     */

    @ApiModelProperty(value = "角色ID", required = true, notes = "角色标识", example = "3")
    protected Long roleId;

    /**
     * 菜单ID
     */
    @ApiModelProperty(value = "菜单ID集合",required = true,notes = "菜单ID集合",example = "3")
    private List<Long> menuIdList;


}
