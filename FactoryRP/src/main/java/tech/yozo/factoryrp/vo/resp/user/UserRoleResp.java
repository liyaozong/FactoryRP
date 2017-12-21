package tech.yozo.factoryrp.vo.resp.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/22
 * @description
 */
@ApiModel
@Data
public class UserRoleResp implements Serializable {

    private static final long serialVersionUID = -5216525402202889079L;


    /**
     * 角色用户关联id
     */
    @ApiModelProperty(value = "角色用户关联id",required = true,notes = "角色用户关联id",example = "3")
    private List<Long> id;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID",required = true,notes = "用户ID",example = "3")
    private Long userId;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID",required = true,notes = "角色ID",example = "3")
    private List<Long> roleId;



}
