package cn.tech.yozo.factoryrp.vo.req;

import cn.tech.yozo.factoryrp.vo.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 部门请求参数
 */
@Data
public class SaveDepartmentReq extends BaseRequest {
    @ApiModelProperty(value = "主键，添加下级和修改的时候必填")
    private Long id;
    @ApiModelProperty(value = "部门编码")
    private String code;
    @ApiModelProperty(value = "部门名称")
    private String name;
    @ApiModelProperty(value = "上级部门ID，添加同级和修改的时候必填")
    private Long parentId;
}
