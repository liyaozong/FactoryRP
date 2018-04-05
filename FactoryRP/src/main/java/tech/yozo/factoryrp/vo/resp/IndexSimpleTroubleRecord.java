package tech.yozo.factoryrp.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2018-04-05 下午11:01
 **/
@Data
public class IndexSimpleTroubleRecord implements Serializable{
    @ApiModelProperty(value = "故障设备名称")
    private String deviceName;
    @ApiModelProperty(value = "故障状态")
    private String status;
}
