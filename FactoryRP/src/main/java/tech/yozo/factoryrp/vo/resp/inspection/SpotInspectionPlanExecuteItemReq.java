package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 巡检计划执行项目ID
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/6
 * @description
 */
@ApiModel
@Data
public class SpotInspectionPlanExecuteItemReq implements Serializable {


    /**
     * 巡检项ID
     */
    @ApiModelProperty(value = "巡检项ID",notes ="巡检项ID",example = "1")
    private Long itemId;


    /**
     * 记录结果
     */
    @ApiModelProperty(value = "记录结果 是否有异常 数值等",notes ="记录结果 是否有异常 数值等",example = "1")
    private String recordResult;


    /**
     * 异常情况描述 1无异常2有异常
     */
    @ApiModelProperty(value = "异常情况描述 1无异常2有异常",notes ="异常情况描述 1无异常2有异常",example = "1")
    private String abnormalDesc;


    /**
     *  备注
     */
    @ApiModelProperty(value = "备注",notes ="备注",example = "1")
    private String remark;

}
