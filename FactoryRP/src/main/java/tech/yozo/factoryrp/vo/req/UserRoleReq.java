package tech.yozo.factoryrp.vo.req;

import tech.yozo.factoryrp.vo.base.ApiCorporateIdentifyRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.ApiRequest;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/22
 * @description
 */
@ApiModel
@Data
public class UserRoleReq extends ApiRequest implements Serializable {


    private static final long serialVersionUID = -5903086884269758663L;

    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID",required = true,notes = "用户ID",example = "3")
    private Long userId;

    /**
     * 角色ID
     */
    @ApiModelProperty(value = "角色ID",required = true,notes = "角色ID",example = "3")
    private Long roleId;


}
