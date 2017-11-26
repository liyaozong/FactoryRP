package cn.tech.yozo.factoryrp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 设备信息扩展字段定义
 */
@Table(name = "device_info_extend_field")
@Entity
@Data
public class DeviceInfoExtendField extends BaseEntity implements Serializable{
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

    @ApiModelProperty(value = "自定义字段8",notes ="自定义字段8" )
    @Column(name = "extend_field_eight")
    private String extendFieldEight;

    @ApiModelProperty(value = "自定义字段9",notes ="自定义字段9" )
    @Column(name = "extend_field_nine")
    private String extendFieldNine;

    @ApiModelProperty(value = "自定义字段10",notes ="自定义字段10" )
    @Column(name = "extend_field_ten")
    private String extendFieldTen;

    @ApiModelProperty(value = "自定义数字",notes ="自定义数字" )
    @Column(name = "extend_number_field")
    private String extendNumberField;

    @ApiModelProperty(value = "自定义日期",notes ="自定义日期" )
    @Column(name = "extend_date_field")
    private String extendDateField;

    @ApiModelProperty(value = "是否有效",notes ="是否有效" )
    @Column(name="status_flag")
    private Integer statusFlag=1;

}
