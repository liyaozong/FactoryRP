package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 巡检计划设备详情信息
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/4
 * @description
 */
@ApiModel
@Data
public class SpotInspectionPlanDeviceInfoResp implements Serializable{


    /**
     * 巡检计划-设备对应ID
     */
    @ApiModelProperty(value = "巡检计划-设备对应ID",notes ="巡检计划-设备对应ID" )
    private Long planDeviceId;

    /**
     * 设备ID
     */
    @ApiModelProperty(value = "设备ID",notes ="设备ID" )
    private Long deviceId;


    /**
     * 设备名称
     */
    @ApiModelProperty(value = "设备名称",notes ="设备名称" )
    private String deviceName;


    /**
     * 路线顺序
     */
    @ApiModelProperty(value = "路线顺序",notes ="路线顺序" )
    private Integer lineOrder;

    /**
     * 适用设备类型名称
     */
    @ApiModelProperty(value = "适用设备类型名称",notes ="适用设备类型" )
    private String deviceTypeName;

    /**
     * 适用设备类型ID
     */
    @ApiModelProperty(value = "适用设备类型ID",notes ="适用设备类型ID" )
    private Long deviceTypeId;

    /**
     * 规格型号
     */
    @ApiModelProperty(value = "规格型号",notes ="规格型号" )
    private String deviceSpecification;


    /**
     * 设备编号
     */
    @ApiModelProperty(value = "设备编号",notes ="设备编号" )
    private String deviceCode;

    /**
     * 巡检标准名称
     */
    @ApiModelProperty(value = "巡检标准名称",notes ="巡检标准名称" )
    private String standardName;

    /**
     * 巡检标准ID
     */
    @ApiModelProperty(value = "巡检标准ID",notes ="巡检标准ID" )
    private Long standardId;


    /**
     * 设备异常数
     */
    @ApiModelProperty(value = "设备异常数",notes ="设备异常数" )
    private Integer abnormalDeviceCount;


    /**
     * 设备漏检数
     */
    @ApiModelProperty(value = "设备漏检数",notes ="设备漏检数" )
    private Integer missCount;

}

