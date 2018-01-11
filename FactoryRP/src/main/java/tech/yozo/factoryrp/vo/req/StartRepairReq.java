package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2017-12-17 下午9:52
 **/
@Data
public class StartRepairReq implements Serializable{

    @ApiModelProperty(value = "故障信息主键")
    private Long troubleRecordId;

    @ApiModelProperty(value = "维修班组主键")
    private Long repairGroupId;

    @ApiModelProperty(value = "维修状态")
    private Integer repairStatus;

    @ApiModelProperty(value = "故障类别主键")
    private Long troubleTypeId;

    @ApiModelProperty(value = "故障原因")
    private Integer troubleReason;

    @ApiModelProperty(value = "维修级别")
    private Integer repairLevel;

    @ApiModelProperty(value = "是否停机，0：否；1：是")
    private Integer stoped;

    @ApiModelProperty(value = "停机时间")
    private Integer stopedHour;

    @ApiModelProperty(value = "维修费用")
    private String repairAmount;

    @ApiModelProperty(value = "工作描述")
    private String workRemark;

    @ApiModelProperty(value = "开始时间，格式yyyy-MM-dd HH:mm:ss")
    private String startTime;

    @ApiModelProperty(value = "维修耗时")
    private Integer costHour;
}
