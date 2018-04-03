package tech.yozo.factoryrp.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2018-04-03 下午11:23
 **/
@Data
@ApiModel("设备保养记录简略信息")
public class SimpleMaintainRecordVo implements Serializable{
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "保养单号")
    private String maintainNo;
    @ApiModelProperty(value = "保养完成时间")
    private String endTime;
    @ApiModelProperty(value = "保养级别")
    private String maintainLevel;
    @ApiModelProperty(value = "保养班组")
    private String repairGroupName;
    @ApiModelProperty(value = "保养负责人")
    private String planManagerName;
    @ApiModelProperty(value = "保养要求")
    private String planRemark;
    @ApiModelProperty(value = "工作秒杀")
    private String maintainContent;
    @ApiModelProperty(value = "保养费用")
    private String maintainAmount;
}
