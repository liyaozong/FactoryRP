package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.ApiRequest;
import tech.yozo.factoryrp.vo.validation.NotEmpty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/28
 * @description 备件管理请求参数
 */
@ApiModel
@Data
public class SparePartsAddReq extends ApiRequest implements Serializable {

    private static final long serialVersionUID = 3974816824139446195L;

    @ApiModelProperty(value = "关联设备ID",notes ="关联设备ID" )
    private Long deviceInfoId;


    @ApiModelProperty(value = "备件类型",notes ="备件类型" )
    private Long sparePartType;

    @ApiModelProperty(value = "备件名称",notes ="备件名称" )
    @NotEmpty(message = "备件名称-不能为空")
    private String name;

    @ApiModelProperty(value = "备件编号",notes ="备件编号" )
    @NotEmpty(message = "备件编号-不能为空")
    private String code;

    @ApiModelProperty(value = "计量单位",notes ="计量单位" )
    private Long measuringUnit;

    @ApiModelProperty(value = "生产厂商",notes ="生产厂商" )
    @NotEmpty(message = "生产厂商-不能为空")
    private String manufacturer;

    @ApiModelProperty(value = "库存下限",notes ="库存下限" )
    private Integer inventoryFloor;

    @ApiModelProperty(value = "参考价",notes ="参考价" )
    private Double referencePrice;

    @ApiModelProperty(value = "换算比例",notes ="换算比例" )
    private Double conversionRatio;

    @ApiModelProperty(value = "换算单位",notes ="换算单位" )
    private Long unitConversion;

    @ApiModelProperty(value = "物料属性",notes ="生产厂商" )
    @NotEmpty(message = "物料属性-不能为空")
    private String materialProperties;

    @ApiModelProperty(value = "规格型号",notes ="规格型号" )
    @NotEmpty(message = "规格型号-不能为空")
    private String specificationsAndodels;

    @ApiModelProperty(value = "条形码",notes ="条形码" )
    @NotEmpty(message = "条形码-不能为空")
    private String barCode;

    @ApiModelProperty(value = "更换周期",notes ="更换周期" )
    private Integer replacementCycle;

    @ApiModelProperty(value = "供应商",notes ="供应商" )
    private Long suppliers;

    @ApiModelProperty(value = "库存上限",notes ="库存上限" )
    private Integer inventoryUpperLimit;

    @ApiModelProperty(value = "备件备注",notes ="备件备注" )
    private String remark;

    @ApiModelProperty(value = "自定义字段1",notes ="自定义字段1" )
    @NotEmpty(message = "自定义字段1-不能为空")
    private String extendFieldOne;

    @ApiModelProperty(value = "自定义字段2",notes ="自定义字段2" )
    @NotEmpty(message = "自定义字段2-不能为空")
    private String extendFieldTwo;

    @ApiModelProperty(value = "自定义字段3",notes ="自定义字段3" )
    @NotEmpty(message = "自定义字段3-不能为空")
    private String extendFieldThree;

    @ApiModelProperty(value = "自定义字段4",notes ="自定义字段4" )
    @NotEmpty(message = "自定义字段4-不能为空")
    private String extendFieldFour;

    @ApiModelProperty(value = "自定义字段5",notes ="自定义字段5" )
    @NotEmpty(message = "自定义字段5-不能为空")
    private String extendFieldFive;

    @ApiModelProperty(value = "自定义字段6",notes ="自定义字段6" )
    @NotEmpty(message = "自定义字段6-不能为空")
    private String extendFieldSix;

    @ApiModelProperty(value = "自定义字段7",notes ="自定义字段7" )
    @NotEmpty(message = "自定义字段7-不能为空")
    private String extendFieldSeven;

    @ApiModelProperty(value = "自定义日期1",notes ="自定义日期1" )
    private Date extendDateFieldOne;

    @ApiModelProperty(value = "自定义日期2",notes ="自定义日期2" )
    private Date extendDateFieldTwo;

}
