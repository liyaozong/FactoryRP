package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.entity.DeviceInfo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenxiang
 * @create 2018-03-02 下午9:12
 **/
@Data
public class AddMaintainPlanReq implements Serializable{
    @ApiModelProperty(value = "设备主键",example = "2")
    private Long deviceId;
    @ApiModelProperty(value = "设备编号",example = "123114")
    private String deviceCode;
    @ApiModelProperty(value = "设备名称",example = "电焊机")
    private String deviceName;
    @ApiModelProperty(value = "设备规格",example = "LHW-9")
    private String deviceSpec;
    @ApiModelProperty(value = "设备使用部门主键",example = "10")
    private Long deviceUseDeptId;
    @ApiModelProperty(value = "设备使用部门名称",example = "测试部门")
    private String deviceUseDeptName;
    @ApiModelProperty(value = "设备类型主键",example = "34")
    private Long deviceType;
    @ApiModelProperty(value = "设备类型名称",example = "生产设备")
    private String deviceTypeName;
    @ApiModelProperty(value = "设备位置",example = "一楼")
    private String deviceAddress;
    @ApiModelProperty(value = "保养级别",example = "2")
    private Integer maintainLevel;
    @ApiModelProperty(value = "维修班组主键",example = "21")
    private Long repairGroupId;
    @ApiModelProperty(value = "循环方式（1:单次；2:循环多次））",example = "2")
    private Integer cycleType;
    @ApiModelProperty(value = "循环周期值",example = "1")
    private String cycleTimeValue;
    @ApiModelProperty(value = "循环周期单位（天；月；年）",example = "天")
    private String cycleTimeUnit;
    @ApiModelProperty(value = "保养部位",example = "轮子")
    private String maintainPart;
    @ApiModelProperty(value = "保养标准",example = "")
    private String maintainStandard;
    @ApiModelProperty(value = "上次保养时间(格式：yyyy-MM-dd)",example = "2017-12-12")
    private Date lastMaintainTime;
    @ApiModelProperty(value = "计划开始时间(格式：yyyy-MM-dd)",example = "2018-03-02")
    private Date planMaintainTimeStart;
    @ApiModelProperty(value = "计划结束时间(格式：yyyy-MM-dd)",example = "2018-06-02")
    private Date planMaintainTimeEnd;
    @ApiModelProperty(value = "保养负责人主键",example = "1")
    private Long planManagerId;
    @ApiModelProperty(value = "保养负责人姓名",example = "张三")
    private String planManagerName;
    @ApiModelProperty(value = "计划描述",example = "1.清扫配电装置的内外部。2.检查各接线端子是否良好。3.检查瓷瓶、套管、母线及各种电气接头。")
    private String planRemark;
}
