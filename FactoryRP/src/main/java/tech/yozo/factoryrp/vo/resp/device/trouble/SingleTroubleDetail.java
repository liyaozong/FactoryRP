package tech.yozo.factoryrp.vo.resp.device.trouble;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenxiang
 * @create 2018-03-07 下午3:34
 **/
@Data
@ApiModel("故障详情")
public class SingleTroubleDetail implements Serializable{
    @ApiModelProperty(value = "故障信息主键")
    private Long troubleRecordId;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "规格型号")
    private String specification;

    @ApiModelProperty(value = "设备编号")
    private String deviceCode;

    @ApiModelProperty(value = "所在位置")
    private String installationAddress;

    @ApiModelProperty(value = "使用部门")
    private String useDept;

    @ApiModelProperty(value = "设备类别",notes ="设备类别" )
    private String deviceType;

    @ApiModelProperty(value = "故障发现时间")
    private String happenTime;

    @ApiModelProperty(value = "维修单号")
    private String orderNo;

    @ApiModelProperty(value = "操作人")
    private String deviceUser;

    @ApiModelProperty(value = "操作人电话")
    private String phone;

    @ApiModelProperty(value = "记录人/报修人")
    private String createUser;

    @ApiModelProperty(value = "记录时间/报修时间")
    private String createTime;

    @ApiModelProperty(value = "故障类别")
    private String troubleType;

    @ApiModelProperty(value = "故障级别")
    private String troubleLevel;

    @ApiModelProperty(value = "故障描述")
    private String remark;

    @ApiModelProperty(value = "设备状态")
    private String deveiceStatus;

}
