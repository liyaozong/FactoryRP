package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.ApiCorporateIdentifyRequest;
import tech.yozo.factoryrp.vo.validation.NotEmpty;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/5
 * @description
 */
@Data
@ApiModel
public class DeviceSparesSaveReq extends ApiCorporateIdentifyRequest implements Serializable {

    @ApiModelProperty(value = "设备备件id(可以为空)",notes ="设备备件id(可以为空)")
    private Long id;

    @ApiModelProperty(value = "备件名称",notes ="备件名称")
    @NotEmpty(message = "备件名称不能为空")
    private String name;

    @ApiModelProperty(value = "上级ID",notes ="上级ID" )
    private Long parentId;

    @ApiModelProperty(value = "显示顺序",notes ="显示顺序" )
    private Integer showOrder;

    @ApiModelProperty(value = "是否有效",notes ="是否有效" )
    private Integer statusFlag;


}
