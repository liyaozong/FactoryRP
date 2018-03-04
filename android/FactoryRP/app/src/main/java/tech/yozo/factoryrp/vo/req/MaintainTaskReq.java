package tech.yozo.factoryrp.vo.req;

import lombok.Data;

@Data
public class MaintainTaskReq {
//    @ApiModelProperty(value = "计划状态(1:今日可以执行计划；2：今日新增计划；3：今日到期计划；4：明日可执行计划；5：明日新增计划；6：明日到期计划；7：已过期计划)",example = "1")
    private Integer planType;

//    @ApiModelProperty(value = "设备名称")
    private String deviceName;

//    @ApiModelProperty(value = "设备编号")
    private String deviceCode;

//    @ApiModelProperty(value = "设备规格型号")
    private String deviceSpec;

//    @ApiModelProperty(value = "维修班组")
    private Long repairGroupId;

//    @ApiModelProperty(value = "当前页码",example = "1")
    private Integer currentPage;

//    @ApiModelProperty(value = "每页显示记录数",example = "10")
    private Integer itemsPerPage;
}
