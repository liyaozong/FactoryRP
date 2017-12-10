package tech.yozo.factoryrp.vo.req;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
//import tech.yozo.factoryrp.vo.base.BaseRequest;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenxiang
 * @create 2017-12-03 下午11:02
 **/
@Data
//@ApiModel(value = "故障报修请求实体")
public class AddTroubleRecordReq implements Serializable{
//    @ApiModelProperty(value = "设备主键",required = true,notes = "设备主键")
    private Long deviceId;

//    @ApiModelProperty(value = "故障等级",notes = "故障等级")
    private Integer troubleLevel;

//    @ApiModelProperty(value = "故障类别",notes = "故障类别")
    private Long troubleType;

//    @ApiModelProperty(value = "维修班组",notes = "维修班组")
    private Long repairGroupId;

//    @ApiModelProperty(value = "设备状态",required = true,notes = "设备状态")
    private Integer deviceStatus;

//    @ApiModelProperty(value = "操作者",notes = "操作者")
    private String deviceUser;

//    @ApiModelProperty(value = "操作者电话",notes = "操作者电话")
    private String phone;

//    @ApiModelProperty(value = "设备位置",notes = "设备位置")
    private String deviceAddress;

//    @ApiModelProperty(value = "故障描述",required = true,notes = "故障描述")
    private String remark;

//    @ApiModelProperty(value = "故障发生时间",required = true,notes = "故障发生时间")
    private Date happenTime;
}
