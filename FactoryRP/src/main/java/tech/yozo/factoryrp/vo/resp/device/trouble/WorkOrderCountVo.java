package tech.yozo.factoryrp.vo.resp.device.trouble;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2017-12-10 下午5:49
 **/
@Data
public class WorkOrderCountVo implements Serializable{

    @ApiModelProperty(value = "我的工单数量")
    private Long allMyOrderNum;

    @ApiModelProperty(value = "待审核工单数量")
    private Long waitAuditNum;

    @ApiModelProperty(value = "待执行工单数量")
    private Long waitRepairNum;

    @ApiModelProperty(value = "执行中工单数量")
    private Long repairingNum;
}
