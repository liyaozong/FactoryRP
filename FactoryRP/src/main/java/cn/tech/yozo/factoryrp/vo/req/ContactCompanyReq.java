package cn.tech.yozo.factoryrp.vo.req;

import cn.tech.yozo.factoryrp.vo.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContactCompanyReq extends BaseRequest {
    @ApiModelProperty(value = "单位编码",example = "001")
    private String code;
    @ApiModelProperty(value = "单位名称",example = "金融")
    private String name;
    @ApiModelProperty(value = "联系人名称",example = "周")
    private String contactName;
    @ApiModelProperty(value = "当前页码",example = "1")
    private Integer currentPage;
    @ApiModelProperty(value = "每页显示记录数",example = "10")
    private Integer itemsPerPage;
}
