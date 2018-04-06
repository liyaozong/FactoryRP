package tech.yozo.factoryrp.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenxiang
 * @create 2018-04-06 上午10:24
 **/
@Data
public class IndexMaintainPlanCountVo implements Serializable{
    @ApiModelProperty(value = "今日计划数量")
    private Integer todayPlanNum;

    @ApiModelProperty(value = "已执行计划数量")
    private Integer executedPlanNum;

    @ApiModelProperty(value = "未执行计划数量")
    private Integer notExecutedPlanNum;

    private List<IndexSimpleMaintainPlanVo> plans;
}
