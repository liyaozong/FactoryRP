package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenxiang
 * @create 2018-04-24 下午4:43
 **/
@Data
@ApiModel("改善计划")
public class ImprovePlan {
    @ApiModelProperty(value = "主键")
    private Long imporvePlanId;
    @ApiModelProperty(value ="问题点" )
    private String problemPonit;
    @ApiModelProperty(value ="改善措施" )
    private String improveWay;
    @ApiModelProperty(value ="责任人" )
    private String dutyPeople;
    @ApiModelProperty(value ="需要完成时间" )
    private String needEndTime;
    @ApiModelProperty(value ="实际完成时间" )
    private String realEndTime;
}
