package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenxiang
 * @create 2018-04-24 下午4:51
 **/
@ApiModel("解决方案及行动计划")
@Data
public class SolutionPlan {
    @ApiModelProperty(value = "解决方案主键")
    private Long solutionPlanId;
    @ApiModelProperty(value = "序号")
    private Integer no;
    @ApiModelProperty(value = "方案及计划")
    private String plan;
    @ApiModelProperty(value = "时间")
    private String dealTime;
    @ApiModelProperty(value = "责任人")
    private String dutyPeople;
}
