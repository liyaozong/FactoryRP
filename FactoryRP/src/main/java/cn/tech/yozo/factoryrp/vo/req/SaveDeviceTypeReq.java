package cn.tech.yozo.factoryrp.vo.req;

import cn.tech.yozo.factoryrp.vo.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 设备类型保存请求参数
 */
@Data
public class SaveDeviceTypeReq extends BaseRequest {
    @ApiModelProperty(value = "主键",example = "1")
    private Long id;
    @ApiModelProperty(value = "设备类型名称",example = "打印类")
    private String name;
    @ApiModelProperty(value = "企业唯一标识",example = "111")
    private Long corporateIdentify;
    @ApiModelProperty(value = "上级ID",notes ="上级ID" )
    private Long parentId;
}
