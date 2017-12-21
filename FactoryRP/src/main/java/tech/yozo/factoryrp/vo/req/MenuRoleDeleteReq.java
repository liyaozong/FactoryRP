package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.ApiRequest;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单-角色删除Vo
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/22
 * @description
 */
@ApiModel
@Data
public class MenuRoleDeleteReq extends ApiRequest implements Serializable {

    @ApiModelProperty(value = "角色ID",required = true,notes = "角色ID",example = "3")
    private Long roleId;


    @ApiModelProperty(value = "需要被删除的菜单的ID集合",required = true,notes = "需要被删除的菜单的ID集合",example = "[1,2]")
    private List<Long> menuList;

}
