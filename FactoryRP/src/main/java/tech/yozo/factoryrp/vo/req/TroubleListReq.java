package tech.yozo.factoryrp.vo.req;

import tech.yozo.factoryrp.vo.base.BaseRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2017-12-04 下午4:03
 **/
@Data
@ApiModel(value = "故障列表请求参数")
public class TroubleListReq extends BaseRequest implements Serializable{

    @ApiModelProperty(value = "设备ID",example = "6")
    private Long deviceId;

    @ApiModelProperty(value = "当前页码",example = "1")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示记录数",example = "10")
    private Integer itemsPerPage;
}
