package tech.yozo.factoryrp.vo.resp.device.trouble;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenxiang
 * @create 2018-04-02 下午10:16
 **/
@Data
@ApiModel("维修简略记录信息")
public class SimpleRepairRecordVo implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;
    @ApiModelProperty(value = "维修单号")
    private String orderNo;
    @ApiModelProperty(value = "申请人")
    private String createUser;
    @ApiModelProperty(value = "送修时间")
    private String  createTime;
    @ApiModelProperty(value = "维修完成时间")
    private String endTime;
    @ApiModelProperty(value = "维修级别")
    private String repairLevel;
    @ApiModelProperty(value = "维修班组")
    private String repairGroupName;
    @ApiModelProperty(value = "维修人员名称")
    private String repairUserName;
    @ApiModelProperty(value = "故障描述")
    private String remark;
    @ApiModelProperty(value = "工作描述")
    private String workRemark;
    @ApiModelProperty(value = "维修费用")
    private String amount;

}
