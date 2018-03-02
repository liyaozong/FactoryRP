package tech.yozo.factoryrp.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.entity.DeviceInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenxiang
 * @create 2018-03-02 下午11:01
 **/
@Data
@ApiModel(value = "保养计划列表")
public class MaintainPlanListVo implements Serializable{
    @ApiModelProperty(value = "计划主键")
    private Long id;
    @ApiModelProperty(value = "计划状态")
    private String planStatus;
    @ApiModelProperty(value = "设备编号")
    private String deviceCode;
    @ApiModelProperty(value = "设备名称")
    private String deviceName;
    @ApiModelProperty(value = "设备规格")
    private String deviceSpec;
    @ApiModelProperty(value = "设备使用部门")
    private String deviceUseDept;
    @ApiModelProperty(value = "设备类型")
    private String deviceType;
    @ApiModelProperty(value = "设备位置")
    private String deviceAddress;
    @ApiModelProperty(value = "保养级别")
    private String maintainLevel;
    @ApiModelProperty(value = "循环方式")
    private String cycleType;
    @ApiModelProperty(value = "循环周期值")
    private String cycleTimeValue;
    @ApiModelProperty(value = "循环周期单位（天；月；年）")
    private String cycleTimeUnit;
    @ApiModelProperty(value = "保养部位")
    private String maintainPart;
    @ApiModelProperty(value = "保养标准")
    private String maintainStandard;
    @ApiModelProperty(value = "上次保养时间")
    private String lastTime;
    @ApiModelProperty(value = "计划开始时间")
    private String  startTime;
    @ApiModelProperty(value = "计划结束时间")
    private String endTime;
    @ApiModelProperty(value = "保养负责人姓名")
    private String planManagerName;
    @ApiModelProperty(value = "计划描述")
    private String planRemark;
}
