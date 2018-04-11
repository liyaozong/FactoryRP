package tech.yozo.factoryrp.vo.resp.device.trouble;

//import com.alibaba.fastjson.annotation.JSONField;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author chenxiang
 * @create 2017-12-18 下午9:17
 **/
@Data
public class WorkOrderDetailVo implements Serializable{

//    @ApiModelProperty(value = "故障信息主键")
    private Long troubleRecordId;

    private Long deviceId;

//    @ApiModelProperty(value = "设备名称")
    private String deviceName;

//    @ApiModelProperty(value = "规格型号")
    private String specification;

//    @ApiModelProperty(value = "设备编号")
    private String deviceCode;

//    @ApiModelProperty(value = "安装地点")
    private String installationAddress;

//    @ApiModelProperty(value = "使用部门")
    private String useDept;

//    @ApiModelProperty(value = "故障发现时间")
//    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date happenTime;

//    @ApiModelProperty(value = "维修单号")
    private String orderNo;

//    @ApiModelProperty(value = "操作人")
    private String deviceUser;

//    @ApiModelProperty(value = "操作人电话")
    private String phone;

//    @ApiModelProperty(value = "记录人/报修人")
    private String createUser;

//    @ApiModelProperty(value = "指派维修人员/接单人")
    private String repairUserName;

//    @ApiModelProperty(value = "故障描述")
    private String remark;

//    @ApiModelProperty(value = "维修班组主键")
    private Long repairGroupId;

//    @ApiModelProperty(value = "维修状态文字描述")
    private String repairStatusString;

//    @ApiModelProperty(value = "维修状态")
    private Integer repairStatus;

//    @ApiModelProperty(value = "故障类别")
    private String troubleType;

//    @ApiModelProperty(value = "故障原因")
    private Integer troubleReason;

//    @ApiModelProperty(value = "维修级别")
    private Integer repairLevel;

//    @ApiModelProperty(value = "是否停机，0：否；1：是")
    private Integer stoped;

//    @ApiModelProperty(value = "停机时间")
    private Integer stopedHour;

//    @ApiModelProperty(value = "维修费用")
    private String repairAmount;

//    @ApiModelProperty(value = "工作描述")
    private String workRemark;

//    @ApiModelProperty(value = "维修开始时间，格式yyyy-MM-dd HH:mm:ss")
    private String startTime;

//    @ApiModelProperty(value = "维修结束时间，格式yyyy-MM-dd HH:mm:ss")
    private String endTime;

//    @ApiModelProperty(value = "维修耗时")
    private Integer costHour;

//    @ApiModelProperty(value = "工作量")
    private List<WorkTimeVo> workTimes;

//    @ApiModelProperty(value = "更换配件")
    private List<UsedSparePartsVo> replaceSpares;
}
