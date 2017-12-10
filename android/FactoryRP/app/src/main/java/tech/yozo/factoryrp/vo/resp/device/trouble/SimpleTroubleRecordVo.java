package tech.yozo.factoryrp.vo.resp.device.trouble;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
//import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenxiang
 * @create 2017-12-04 下午2:06
 **/
@Data
//@ApiModel("简略故障信息")
public class SimpleTroubleRecordVo implements Serializable{

//    @ApiModelProperty(value = "主键")
    private Long id;
//    @ApiModelProperty(value = "故障级别")
    private String troubleLevel;
//    @ApiModelProperty(value = "维修班组")
    private String repairGroupName;
//    @ApiModelProperty(value = "故障描述")
    private String remark;
//    @ApiModelProperty(value = "故障发现时间")
//    @DateTimeFormat(style = "FF",pattern = "yyyy/MM/dd HH:mm:ss")
    private Date happenTime;
//    @ApiModelProperty(value = "故障状态")
    private String status;
//    @ApiModelProperty(value = "记录人/报修人")
    private String createUser;
//    @ApiModelProperty(value = "记录时间/报修时间")
//    @DateTimeFormat(style = "FF",pattern = "yyyy/MM/dd HH:mm:ss")
    private Date createTime;
//    @ApiModelProperty(value = "维修单号主键")
    private Long repairRecordId;
}
