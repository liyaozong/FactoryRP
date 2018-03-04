package tech.yozo.factoryrp.vo.resp;

import lombok.Data;

import java.io.Serializable;

@Data
public class MaintainTaskResp {
//    @ApiModelProperty(value = "计划主键")
    private Long id;
//    @ApiModelProperty(value = "设备编号")
    private String deviceCode;
//    @ApiModelProperty(value = "设备名称")
    private String deviceName;
//    @ApiModelProperty(value = "设备规格")
    private String deviceSpec;
//    @ApiModelProperty(value = "保养级别")
    private String maintainLevel;
//    @ApiModelProperty(value = "下次保养时间")
    private String  nexMaintainTime;
//    @ApiModelProperty(value = "保养负责人姓名")
    private String planManagerName;
}
