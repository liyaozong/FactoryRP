package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 巡检计划新增返回
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/25
 * @description
 */
@ApiModel
@Data
public class SpotInspectionPlanAddResp implements Serializable{


    private static final long serialVersionUID = 3012863004522946037L;

    /**
     * 巡检计划ID
     */
    @ApiModelProperty(value = "巡检计划ID",required = true,notes = "点检标准ID",example = "1")
    private Long id;


    /**
     * 巡检计划名称
     */
    @ApiModelProperty(value = "巡检计划名称",required = true,notes = "巡检计划名称",example = "1")
    private String name;

}
