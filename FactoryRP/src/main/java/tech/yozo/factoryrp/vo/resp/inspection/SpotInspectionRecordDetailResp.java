package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 巡检记录详情Resp
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/5
 * @description
 */
@ApiModel
@Data
public class SpotInspectionRecordDetailResp implements Serializable {

    /**
     * 巡检计划-设备对应ID
     */
    @ApiModelProperty(value = "巡检计划-设备对应ID",notes ="巡检计划-设备对应ID" )
    private Long planDeviceId;

    /**
     * 执行状态 1完成2未完成
     */
    @ApiModelProperty(value = "执行状态 1完成2未完成",notes ="执行状态 1完成2未完成",example = "1")
    private Integer executeStatus;

    /**
     * 巡检标准ID
     */
    @ApiModelProperty(value = "巡检标准ID",notes ="巡检标准ID",example = "1")
    private Long standardId;

    /**
     * 巡检标准名称
     */
    @ApiModelProperty(value = "巡检标准名称",notes ="巡检标准名称",example = "周检")
    private String standardName;


    /**
     * 漏检数
     */
    @ApiModelProperty(value = "异常数",notes ="异常数" )
    private Integer abnormalDeviceCount;


    /**
     * 异常数
     */
    @ApiModelProperty(value = "漏检数",notes ="漏检数" )
    private Integer missCount;

    /**
     * 设备编号
     */
    @ApiModelProperty(value = "设备编号",notes ="设备编号" )
    private String deviceCode;


    /**
     * 设备型号
     */
    @ApiModelProperty(value = "设备型号",notes ="设备型号" )
    private String deviceSpecification;

    /**
     * 设备名称
     */
    @ApiModelProperty(value = "设备名称",notes ="设备名称" )
    private String deviceName;


    /**
     * 设备ID
     */
    @ApiModelProperty(value = "设备ID",notes ="设备ID" )
    private Long deviceId;

    /**
     * 顺序路线
     */
    @ApiModelProperty(value = "顺序路线",notes ="顺序路线" )
    private Integer lineOrder;
}
