package tech.yozo.factoryrp.vo.req;

import com.alibaba.fastjson.JSON;
import tech.yozo.factoryrp.vo.base.ApiCorporateIdentifyRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.ApiRequest;
import tech.yozo.factoryrp.vo.validation.NotEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
     * 角色ID 可以多个逗号进行分割
     */
    @ApiModelProperty(value = "角色ID集合",required = true,notes = "角色ID集合",example = "[1,2]")
    private List<Long> roleIdList;

    public static void main(String[] args) {
        List<Long> list = new ArrayList<>();

        list.add(1L);
        list.add(2L);

        UserRoleReq userRoleReq = new UserRoleReq();

        userRoleReq.setRoleIdList(list);
        userRoleReq.setUserId(1L);

        System.out.println(JSON.toJSONString(userRoleReq));
    }

}
