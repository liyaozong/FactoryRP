package tech.yozo.factoryrp.vo.req;

import lombok.Data;

import java.util.List;

@Data
public class InspectRecordSubmitReq {
    /**
     * 设备ID
     */
//    @ApiModelProperty(value = "设备ID",notes ="设备ID" )
    private Long deviceId;


    /**
     * 巡检计划ID
     */
//    @ApiModelProperty(value = "巡检计划ID",notes ="巡检计划ID" )
    private Long planId;


    /**
     * 点检标准ID
     */
//    @ApiModelProperty(value = "点检标准ID",notes ="点检标准ID" )
    private Long spotInspectionStandard;


    /**
     * 设备名称
     */
//    @ApiModelProperty(value = "设备名称",notes ="设备名称" )
//    @NotEmpty(message = "设备名称不可为空")
    private String deviceName;


    /**
     * 设备编码
     */
//    @ApiModelProperty(value = "设备编码",notes ="设备编码" )
//    @NotEmpty(message = "设备编码不可为空")
    private String deviceCode;


    /**
     * 巡检项目详情集合
     */
//    @ApiModelProperty(value = "巡检项目详情集合",notes ="巡检项目详情集合" )
    private List<InspectionItemAddReq> detailList;
}
