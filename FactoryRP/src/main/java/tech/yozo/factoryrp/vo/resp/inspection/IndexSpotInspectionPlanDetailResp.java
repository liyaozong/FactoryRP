package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 巡检计划首页显示详细数据
 * @author created by Singer email:313402703@qq.com
 * @time 2018/4/7
 * @description
 */
@ApiModel
@Data
public class IndexSpotInspectionPlanDetailResp implements Serializable {

    private static final long serialVersionUID = 8617254035947878526L;


    /**
     * 巡检计划名称
     */
    @ApiModelProperty(value = "巡检计划名称",required = true,notes = "巡检计划名称",example = "1")
    private String planName;


    /**
     * 执行状态 1已执行 2未执行
     */
    @ApiModelProperty(value = "执行状态 1已执行 2未执行",required = true,notes = "执行状态 1已执行 2未执行",example = "1")
    private Integer executeStatus;

    /**
     * 巡检计划ID
     */
    @ApiModelProperty(value = "巡检计划ID",required = true,notes = "巡检计划ID",example = "1")
    private Long planId;
}
