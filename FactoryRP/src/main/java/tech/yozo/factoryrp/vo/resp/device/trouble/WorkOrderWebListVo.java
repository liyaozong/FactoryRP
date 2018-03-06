package tech.yozo.factoryrp.vo.resp.device.trouble;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2018-03-06 下午9:16
 **/
@Data
@ApiModel("待审核故障信息")
public class WorkOrderWebListVo implements Serializable{

    @ApiModelProperty(value = "故障记录主键")
    private Long id;

    @ApiModelProperty(value = "维修单号")
    private String orderNo;

    @ApiModelProperty(value = "故障状态")
    private String status;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "规格型号")
    private String specification;

    @ApiModelProperty(value = "设备编号")
    private String deviceCode;

    @ApiModelProperty(value = "使用部门")
    private String useDept;

    @ApiModelProperty(value = "故障发现时间")
    private String happenTime;

    @ApiModelProperty(value = "记录时间/报修时间")
    private String createTime;

    @ApiModelProperty(value = "维修班组")
    private String repairGroup;

    @ApiModelProperty(value = "故障类别")
    private String troubleType;

    @ApiModelProperty(value = "故障等级")
    private String troubleLevel;

    @ApiModelProperty(value = "故障描述")
    private String remark;
}
