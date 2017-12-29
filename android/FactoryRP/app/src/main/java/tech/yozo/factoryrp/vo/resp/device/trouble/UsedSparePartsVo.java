package tech.yozo.factoryrp.vo.resp.device.trouble;

//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 设备维修更换的备件信息
 * @author chenxiang
 * @create 2017-12-18 下午9:43
 **/
@Data
public class UsedSparePartsVo implements Serializable{

//    @ApiModelProperty(value = "备件名称",notes ="备件名称" )
    private String name;

//    @ApiModelProperty(value = "备件编号",notes ="备件编号" )
    private String code;

//    @ApiModelProperty(value = "规格型号",notes ="规格型号" )
    private String specificationsAndodels;

//    @ApiModelProperty(value = "当前库存",notes ="当前库存" )
    private Integer inventoryUpperLimit;

//    @ApiModelProperty(value = "原配件序号",notes ="原配件序号" )
    private String oldOrderNum;

//    @ApiModelProperty(value = "新换上序号",notes ="新换上序号" )
    private String newOrderNum;

//    @ApiModelProperty(value = "更换数量",notes ="更换数量" )
    private Integer amount;
}
