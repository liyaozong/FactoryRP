package cn.tech.yozo.factoryrp.vo.req;

import cn.tech.yozo.factoryrp.vo.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenxiang
 * @create 2017-12-02 下午12:05
 **/
@Data
public class SaveRelSparePartReq extends BaseRequest implements Serializable{
    @ApiModelProperty(value = "设备ID",required = true)
    private Long deviceId;

    @ApiModelProperty(value = "备件ID集合，多个备件使用List",required = true)
    private List<Long> sparePartIds;
}
