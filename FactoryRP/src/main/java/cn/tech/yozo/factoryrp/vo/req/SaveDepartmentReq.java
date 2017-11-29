package cn.tech.yozo.factoryrp.vo.req;

import cn.tech.yozo.factoryrp.vo.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 部门请求参数
 */
@Data
public class SaveDepartmentReq extends BaseRequest {
    @ApiModelProperty(value = "主键",example = "1")
    private Long id;
    @ApiModelProperty(value = "部门编码",example = "1212")
    private String code;
    @ApiModelProperty(value = "部门名称",example = "金融部")
    private String name;
    @ApiModelProperty(value = "企业唯一标识",example = "111")
    private Long corporateIdentify;
    @ApiModelProperty(value = "上级部门ID",notes ="上级部门ID" )
    private Long parentId;
}
