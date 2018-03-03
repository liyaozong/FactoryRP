package tech.yozo.factoryrp.vo.resp.inspection.mobile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 手机端查询点检计划接口返回
 */
@ApiModel
@Data
public class SpotInspectionPlanResp implements Serializable {


    @ApiModelProperty(value = "巡检计划ID",required = true,notes = "点检标准ID",example = "1")
    private Long panId;


    @ApiModelProperty(value = "计划开始时间",required = true,notes = "计划开始时间",example = "1")
    private String nextExecuteTime;

}
