package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 根据用户ID删除用户-角色关系的接口
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/22
 * @description
 */
@ApiModel
@Data
public class UserRoleDeleteReq {


    @ApiModelProperty(value = "用户ID",required = true,notes = "用户ID",example = "3")
    private Long userId;


    @ApiModelProperty(value = "需要被删除的角色ID集合",required = true,notes = "需要被删除的角色ID集合",example = "[1,2]")
    private List<Long> roleList;
}
