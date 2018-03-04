package tech.yozo.factoryrp.vo.resp.inspection.mobile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionStandardItemsQueryResp;

import java.io.Serializable;
import java.util.List;

/**
 * 手机端巡检项目查询包装VO
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/4
 * @description
 */
@ApiModel
@Data
public class SpotInspectionItemsQueryWarpResp implements Serializable {

    /**
     * 设备ID
     */
    @ApiModelProperty(value = "设备ID",notes ="设备ID" )
    private Long deviceId;


    /**
     * 巡检计划ID
     */
    @ApiModelProperty(value = "巡检计划ID",notes ="巡检计划ID" )
    private Long planId;


    /**
     * 点检标准ID
     */
    @ApiModelProperty(value = "点检标准ID",notes ="点检标准ID" )
    private Long spotInspectionStandard;


    /**
     * 设备名称
     */
    @ApiModelProperty(value = "设备名称",notes ="设备名称" )
    private String deviceName;


    /**
     * 设备编码
     */
    @ApiModelProperty(value = "设备编码",notes ="设备编码" )
    private String deviceCode;

    /**
     * 巡检项目列表
     */
    @ApiModelProperty(value = "巡检项目列表",notes ="巡检项目列表" )
    private List<SpotInspectionStandardItemsQueryResp> itemList;

}
