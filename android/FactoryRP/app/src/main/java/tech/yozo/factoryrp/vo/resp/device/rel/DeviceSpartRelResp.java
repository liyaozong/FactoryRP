package tech.yozo.factoryrp.vo.resp.device.rel;

//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.resp.device.info.FullDeviceInfoResp;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2017-12-02 下午1:17
 **/
@Data
public class DeviceSpartRelResp extends FullDeviceInfoResp implements Serializable{
//    @ApiModelProperty(value = "关联信息主键")
    private Long relId;
}
