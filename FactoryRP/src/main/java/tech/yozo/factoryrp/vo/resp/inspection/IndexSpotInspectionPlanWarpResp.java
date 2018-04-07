package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 首页巡检计划统计数据Resp
 * @author created by Singer email:313402703@qq.com
 * @time 2018/4/7
 * @description
 */
@ApiModel
@Data
public class IndexSpotInspectionPlanWarpResp implements Serializable {

    private static final long serialVersionUID = 3302121459900463286L;

    /**
     * 巡检计划ID
     */
    @ApiModelProperty(value = "今天已经执行的数量",required = true,notes = "今天已经执行的数量",example = "1")
    private Integer executedCount = 0;


    /**
     * 今天未执行的数量
     */
    @ApiModelProperty(value = "今天未执行的数量",required = true,notes = "今天未执行的数量",example = "1")
    private Integer unExecutedCount = 0;

    /**
     * 今天总的数量
     */
    @ApiModelProperty(value = "今天总的数量",required = true,notes = "今天总的数量",example = "1")
    private Integer totalCount = 0;


    /**
     * 巡检计划首页显示详细数据
     */
    @ApiModelProperty(value = "巡检计划首页显示详细数据",required = true,notes = "巡检计划首页显示详细数据",example = "List",dataType = "List")
    private List<IndexSpotInspectionPlanDetailResp> executeDetailList;
}
