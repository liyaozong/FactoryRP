package tech.yozo.factoryrp.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2018-04-06 上午10:28
 **/
@Data
public class IndexSimpleMaintainPlanVo implements Serializable{
    @ApiModelProperty(value = "故障设备名称")
    private String deviceName;
    @ApiModelProperty(value = "计划执行状态")
    private String status;
}
