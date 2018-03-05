package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2018-03-05 上午8:56
 **/
@Data
public class MaintainPlanListForDeviceReq extends MaintainPlanListReq implements Serializable{
    @ApiModelProperty(value = "设备主键",example = "2")
    private Long deviceId;
}
