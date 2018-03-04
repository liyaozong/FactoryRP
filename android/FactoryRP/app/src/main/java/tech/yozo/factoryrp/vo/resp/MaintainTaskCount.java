package tech.yozo.factoryrp.vo.resp;

import lombok.Data;

@Data
public class MaintainTaskCount {
//    @ApiModelProperty(value = "今日计划数量")
    private Long todayPlanNum;

//    @ApiModelProperty(value = "明日计划数量")
    private Long tomorrowPlanNum;

//    @ApiModelProperty(value = "已过期计划数量")
    private Long expiredNum;
}
