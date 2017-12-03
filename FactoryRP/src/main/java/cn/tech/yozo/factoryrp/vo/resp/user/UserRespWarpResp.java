package cn.tech.yozo.factoryrp.vo.resp.user;

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
public class UserRespWarpResp implements Serializable {

    private static final long serialVersionUID = -1828697258674969059L;


    @ApiModelProperty(value = "用户集合",required = true,notes = "用户集合")
    private List<UserResp> userRespList;


}
