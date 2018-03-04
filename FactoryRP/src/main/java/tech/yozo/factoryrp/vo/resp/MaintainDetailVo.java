package tech.yozo.factoryrp.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.resp.device.trouble.UsedSparePartsVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkTimeVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author chenxiang
 * @create 2018-03-04 下午12:22
 **/
@Data
public class MaintainDetailVo implements Serializable {

    @ApiModelProperty(value = "保养计划主键")
    private Long maintainPlanId;
    @ApiModelProperty(value = "设备编号")
    private String deviceCode;
    @ApiModelProperty(value = "设备名称")
    private String deviceName;
    @ApiModelProperty(value = "设备规格")
    private String deviceSpec;
    @ApiModelProperty(value = "设备分类名称")
    private String deviceTypeName;
    @ApiModelProperty(value = "所在部门名称")
    private String deviceUseDeptName;
    @ApiModelProperty(value = "保养级别")
    private Integer maintainLevel;
    @ApiModelProperty(value = "保养级别文字描述")
    private String maintainLevelString;
    @ApiModelProperty(value = "维保班组/维保工段")
    private Long repairGroupId;
    @ApiModelProperty(value = "循环方式")
    private String cycleType;
    @ApiModelProperty(value = "循环周期值")
    private String cycleTime;
    @ApiModelProperty(value = "上次保养时间")
    private String lastTime;
    @ApiModelProperty(value = "下次保养时间")
    private String  nexMaintainTime;
    @ApiModelProperty(value = "保养部位")
    private String maintainPart;
    @ApiModelProperty(value = "保养标准")
    private String maintainStandard;
    @ApiModelProperty(value = "保养负责人姓名")
    private String planManagerName;
    @ApiModelProperty(value = "计划描述")
    private String planRemark;
    @ApiModelProperty(value = "保养状态")
    private Integer maintainStatus;

    @ApiModelProperty(value = "是否停机，0：否；1：是")
    private Integer stoped;

    @ApiModelProperty(value = "停机时间")
    private Integer stopedHour;

    @ApiModelProperty(value = "保养费用")
    private String maintainAmount;

    @ApiModelProperty(value = "保养过程")
    private String maintainRemark;

    @ApiModelProperty(value = "保养开始时间，格式yyyy-MM-dd HH:mm:ss")
    private String startTime;

    @ApiModelProperty(value = "保养结束时间，格式yyyy-MM-dd HH:mm:ss")
    private String endTime;

    @ApiModelProperty(value = "保养耗时")
    private Integer costHour;

    @ApiModelProperty(value = "工作量")
    private List<WorkTimeVo> workTimes;

    @ApiModelProperty(value = "更换配件")
    private List<UsedSparePartsVo> replaceSpares;
}
