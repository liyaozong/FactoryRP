package cn.tech.yozo.factoryrp.vo.req;

import cn.tech.yozo.factoryrp.vo.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2017-12-02 上午11:56
 **/
@Data
public class QueryDeviceSpareRelReq extends BaseRequest implements Serializable{
    @ApiModelProperty(value = "设备ID，查询设备关联备件信息必传")
    private Long deviceId;

    @ApiModelProperty(value = "备件ID，查询备件关联设备信息必传")
    private Long sparePartId;

    @ApiModelProperty(value = "当前页码")
    private Integer currentPage = 1;

    @ApiModelProperty(value = "每页显示记录数")
    private Integer itemsPerPage=10;
}
