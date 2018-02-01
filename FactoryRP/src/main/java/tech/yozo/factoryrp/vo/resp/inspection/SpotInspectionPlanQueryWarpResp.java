package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 巡检计划列表查询包装数据
 * @author created by Singer email:313402703@qq.com
 * @time 2018/2/1
 * @description
 */
@ApiModel
@Data
public class SpotInspectionPlanQueryWarpResp implements Serializable {


    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码",example = "1")
    private Integer currentPage;

    /**
     * 每页显示记录数
     */
    @ApiModelProperty(value = "每页显示记录数",example = "10")
    private Integer itemsPerPage;

    /**
     * 总数量
     */
    @ApiModelProperty(value = "总数量",example = "10")
    private Long totalCount;


    /**
     * 巡检计划
     */
    @ApiModelProperty(value = "巡检计划",example = "list",dataType = "ArrayList")
    List<SpotInspectionPlanQueryResp> spotInspectionPlanQueryRespList;

}
