package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.BaseRequest;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2018-03-02 下午10:45
 **/
@ApiModel(value = "保养计划查询条件")
@Data
public class MaintainPlanListReq  extends BaseRequest implements Serializable {

    @ApiModelProperty(value = "当前页码",example = "1")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示记录数",example = "10")
    private Integer itemsPerPage;
}

