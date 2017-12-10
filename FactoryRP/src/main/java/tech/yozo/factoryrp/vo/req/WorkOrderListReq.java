package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2017-12-10 下午3:50
 **/
@Data
public class WorkOrderListReq implements Serializable{
    @ApiModelProperty(value = "当前页码",example = "1")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示记录数",example = "10")
    private Integer itemsPerPage;
}
