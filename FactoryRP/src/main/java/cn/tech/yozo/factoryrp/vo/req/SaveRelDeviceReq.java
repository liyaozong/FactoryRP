package cn.tech.yozo.factoryrp.vo.req;

import cn.tech.yozo.factoryrp.vo.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenxiang
 * @create 2017-12-02 上午11:54
 **/
@Data
public class SaveRelDeviceReq extends BaseRequest implements Serializable{
    @ApiModelProperty(value = "备件ID",required = true)
    private Long sparePartId;

    @ApiModelProperty(value = "设备ID集合，多个设备使用List",required = true)
    private List<Long> deviceIds;
}
