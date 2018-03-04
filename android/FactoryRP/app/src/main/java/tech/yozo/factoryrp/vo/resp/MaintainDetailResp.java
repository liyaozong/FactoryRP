package tech.yozo.factoryrp.vo.resp;

import lombok.Data;

import java.io.Serializable;

@Data
public class MaintainDetailResp implements Serializable {
    //    @ApiModelProperty(value = "保养计划主键")
    private Long maintainPlanId;
    //    @ApiModelProperty(value = "设备编号")
    private String deviceCode;
    //    @ApiModelProperty(value = "设备名称")
    private String deviceName;
    //    @ApiModelProperty(value = "设备规格")
    private String deviceSpec;
    //    @ApiModelProperty(value = "设备分类名称")
    private String deviceTypeName;
    //    @ApiModelProperty(value = "所在部门名称")
    private String deviceUseDeptName;
    //    @ApiModelProperty(value = "保养级别")
    private Integer maintainLevel;
    //    @ApiModelProperty(value = "保养级别文字描述")
    private String maintainLevelString;
    //    @ApiModelProperty(value = "维保班组/维保工段")
    private Long repairGroupId;
    //    @ApiModelProperty(value = "维保班组/维保工段名称")
    private String repairGroupName;
    //    @ApiModelProperty(value = "循环方式")
    private String cycleType;
    //    @ApiModelProperty(value = "循环周期值")
    private String cycleTime;
    //    @ApiModelProperty(value = "上次保养时间")
    private String lastTime;
    //    @ApiModelProperty(value = "下次保养时间")
    private String  nexMaintainTime;
    //    @ApiModelProperty(value = "保养部位")
    private String maintainPart;
    //    @ApiModelProperty(value = "保养标准")
    private String maintainStandard;
    //    @ApiModelProperty(value = "保养负责人姓名")
    private String planManagerName;
    //    @ApiModelProperty(value = "计划描述")
    private String planRemark;
}
