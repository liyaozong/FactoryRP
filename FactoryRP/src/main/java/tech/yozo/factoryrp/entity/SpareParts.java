package tech.yozo.factoryrp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/28
 * @description 备件相关实体
 */
@Table(name = "spare_parts")
@Entity
@Data
public class SpareParts extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 7678699052167645691L;

    @ApiModelProperty(value = "关联设备Id",notes ="关联设备Id" )
    @Column(name = "deviceinfo_id",length = 10)
    private Long deviceInfoId;

    @ApiModelProperty(value = "备件类型",notes ="备件类型" )
    @Column(name = "spare_part_type",length = 10)
    private Long sparePartType;

    @ApiModelProperty(value = "备件名称",notes ="备件名称" )
    @Column(name = "name",length = 20)
    private String name;

    @ApiModelProperty(value = "备件编号",notes ="备件编号" )
    @Column(name = "code",length = 20)
    private String code;

    @ApiModelProperty(value = "计量单位",notes ="计量单位" )
    @Column(name = "measuring_unit",length = 10)
    private Long measuringUnit;

    @ApiModelProperty(value = "生产厂商",notes ="生产厂商" )
    @Column(name = "manufacturer",length = 20)
    private String manufacturer;

    @ApiModelProperty(value = "库存下限",notes ="库存下限" )
    @Column(name = "inventory_floor",length = 10)
    private Integer inventoryFloor;

    @ApiModelProperty(value = "参考价",notes ="参考价" )
    @Column(name = "reference_price",length = 10)
    private Double referencePrice;

    @ApiModelProperty(value = "换算单位",notes ="换算单位" )
    @Column(name = "unit_conversion",length = 10)
    private Long unitConversion;

    @ApiModelProperty(value = "物料属性",notes ="生产厂商" )
    @Column(name = "material_properties",length = 20)
    private String materialProperties;

    @ApiModelProperty(value = "换算比例",notes ="换算比例" )
    @Column(name = "conversion_ratio",length = 20)
    private Double conversionRatio;

    @ApiModelProperty(value = "规格型号",notes ="规格型号" )
    @Column(name = "specifications_andodels",length = 20)
    private String specificationsAndodels;

    @ApiModelProperty(value = "条形码",notes ="条形码" )
    @Column(name = "bar_code",length = 20)
    private String barCode;

    @ApiModelProperty(value = "更换周期",notes ="更换周期" )
    @Column(name = "replacement_cycle",length = 20)
    private Integer replacementCycle;

    @ApiModelProperty(value = "供应商",notes ="供应商" )
    @Column(name = "suppliers",length = 20)
    private Long suppliers;

    @ApiModelProperty(value = "库存上限",notes ="库存上限" )
    @Column(name = "inventory_upper_limit",length = 10)
    private Integer inventoryUpperLimit;

    @ApiModelProperty(value = "备件备注",notes ="备件备注" )
    @Column(name = "remark",length = 10)
    private String remark;

    @ApiModelProperty(value = "自定义字段1",notes ="自定义字段1" )
    @Column(name = "extend_field_one")
    private String extendFieldOne;

    @ApiModelProperty(value = "自定义字段2",notes ="自定义字段2" )
    @Column(name = "extend_field_two")
    private String extendFieldTwo;

    @ApiModelProperty(value = "自定义字段3",notes ="自定义字段3" )
    @Column(name = "extend_field_three")
    private String extendFieldThree;

    @ApiModelProperty(value = "自定义字段4",notes ="自定义字段4" )
    @Column(name = "extend_field_four")
    private String extendFieldFour;

    @ApiModelProperty(value = "自定义字段5",notes ="自定义字段5" )
    @Column(name = "extend_field_five")
    private String extendFieldFive;

    @ApiModelProperty(value = "自定义字段6",notes ="自定义字段6" )
    @Column(name = "extend_field_six")
    private String extendFieldSix;

    @ApiModelProperty(value = "自定义字段7",notes ="自定义字段7" )
    @Column(name = "extend_field_seven")
    private String extendFieldSeven;

    @ApiModelProperty(value = "自定义日期1",notes ="自定义日期1" )
    @Column(name = "extend_date_field_one")
    private Date extendDateFieldOne = new Date();

    @ApiModelProperty(value = "自定义日期2",notes ="自定义日期2" )
    @Column(name = "extend_date_field_two")
    private Date extendDateFieldTwo = new Date();

}
