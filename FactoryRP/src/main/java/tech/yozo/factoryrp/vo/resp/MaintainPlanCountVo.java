package tech.yozo.factoryrp.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2018-03-04 上午11:37
 **/
@Data
public class MaintainPlanCountVo implements Serializable{
    @ApiModelProperty(value = "今日计划数量")
    private Long todayPlanNum;

    @ApiModelProperty(value = "明日计划数量")
    private Long tomorrowPlanNum;

    @ApiModelProperty(value = "已过期计划数量")
    private Long expiredNum;
}
