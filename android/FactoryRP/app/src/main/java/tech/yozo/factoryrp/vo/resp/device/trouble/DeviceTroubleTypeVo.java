package tech.yozo.factoryrp.vo.resp.device.trouble;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备故障类型返回Vo
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/14
 * @description
 */
//@ApiModel
@Data
public class DeviceTroubleTypeVo {

//    private static final long serialVersionUID = 4792668575678728057L;
//
//    @ApiModelProperty(value = "设备故障类型id",notes ="设备故障类型id")
    private Long id;

//    @ApiModelProperty(value = "设备故障类型名称",notes ="设备故障类型名称")
    private String name;

//    @ApiModelProperty(value = "上级ID",notes ="上级ID" )
    private Long parentId;

//    @ApiModelProperty(value = "显示顺序",notes ="显示顺序" )
//    private Integer showOrder;
//
//    @ApiModelProperty(value = "是否有效",notes ="是否有效" )
//    private Integer statusFlag;


}
