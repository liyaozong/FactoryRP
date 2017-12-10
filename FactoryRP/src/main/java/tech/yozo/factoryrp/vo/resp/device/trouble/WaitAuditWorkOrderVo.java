package tech.yozo.factoryrp.vo.resp.device.trouble;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 工单简略信息
 * @author chenxiang
 * @create 2017-12-10 下午3:39
 **/
@Data
public class WaitAuditWorkOrderVo implements Serializable{

    @ApiModelProperty(value = "故障记录主键")
    private Long id;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "故障状态")
    private String status;

    @ApiModelProperty(value = "规格型号")
    private String specification;

    @ApiModelProperty(value = "设备编号")
    private String code;

    @ApiModelProperty(value = "故障发现时间")
    private Date happenTime;

    @ApiModelProperty(value = "维修单号")
    private String orderNo;

    @ApiModelProperty(value = "故障级别")
    private String troubleLevel;

    @ApiModelProperty(value = "指派维修人员/接单人")
    private String repairUser;
}
