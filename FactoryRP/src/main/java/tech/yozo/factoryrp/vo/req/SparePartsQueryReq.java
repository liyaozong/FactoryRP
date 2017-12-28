package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.ApiRequest;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/28
 * @description 备件查询请求VO
 */
@ApiModel
@Data
public class SparePartsQueryReq extends ApiRequest implements Serializable {


    private static final long serialVersionUID = 9117599321104866076L;

    @ApiModelProperty(value = "备件类型",notes ="备件类型" )
    private Long sparePartType;

    @ApiModelProperty(value = "备件名称",notes ="备件名称" )
    private String name;

    @ApiModelProperty(value = "备件编号",notes ="备件编号" )
    private String code;

    @ApiModelProperty(value = "计量单位",notes ="计量单位" )
    private Long measuringUnit;

    @ApiModelProperty(value = "生产厂商",notes ="生产厂商" )
    private String manufacturer;

    @ApiModelProperty(value = "库存下限",notes ="库存下限" )
    private Integer inventoryFloor;

    @ApiModelProperty(value = "参考价",notes ="参考价" )
    private Double referencePrice;

    @ApiModelProperty(value = "换算单位",notes ="换算单位" )
    private Long unitConversion;

    @ApiModelProperty(value = "物料属性",notes ="物料属性" )
    private String materialProperties;

    @ApiModelProperty(value = "换算比例",notes ="换算比例" )
    private Double conversionRatio;

    @ApiModelProperty(value = "规格型号",notes ="规格型号" )
    private String specificationsAndodels;

    @ApiModelProperty(value = "条形码",notes ="条形码" )
    private String barCode;

    @ApiModelProperty(value = "更换周期",notes ="更换周期" )
    private Integer replacementCycle;

    @ApiModelProperty(value = "供应商",notes ="供应商" )
    private Long suppliers;

    @ApiModelProperty(value = "库存上限",notes ="库存上限" )
    private Integer inventoryUpperLimit;

    @ApiModelProperty(value = "当前页码",example = "1")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示记录数",example = "10")
    private Integer itemsPerPage;

}
