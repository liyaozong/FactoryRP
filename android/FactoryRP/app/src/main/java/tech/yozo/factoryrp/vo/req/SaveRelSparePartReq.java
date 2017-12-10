package tech.yozo.factoryrp.vo.req;

//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
//import tech.yozo.factoryrp.vo.base.BaseRequest;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenxiang
 * @create 2017-12-02 下午12:05
 **/
@Data
public class SaveRelSparePartReq implements Serializable{
//    @ApiModelProperty(value = "设备ID",required = true)
    private Long deviceId;

//    @ApiModelProperty(value = "备件ID集合，多个备件使用List",required = true)
    private List<Long> sparePartIds;
}
