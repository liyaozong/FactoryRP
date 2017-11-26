package cn.tech.yozo.factoryrp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 设备信息
 */
@Table(name = "device_info")
@Entity
@Data
public class DeviceInfo extends BaseEntity implements Serializable{
    @ApiModelProperty(value = "设备名称",notes ="设备名称" )
    @Column(name = "name")
    private String name;

    @ApiModelProperty(value = "规格型号",notes ="规格型号" )
    @Column(name = "specification")
    private String specification;

    @ApiModelProperty(value = "设备编号",notes ="设备编号" )
    @Column(name = "code")
    private String code;

    @ApiModelProperty(value = "生产厂商",notes ="生产厂商" )
    @Column(name = "manufacturer")
    private String manufacturer;

    @ApiModelProperty(value = "供应商",notes ="供应商" )
    @Column(name = "supplier")
    private String supplier;

    @ApiModelProperty(value = "设备类别",notes ="设备类别" )
    @Column(name = "device_type")
    private String deviceType;

    @ApiModelProperty(value = "购置时间",notes ="购置时间" )
    @Column(name = "buy_date")
    private Date buyDate;

    @ApiModelProperty(value = "资产原值",notes ="资产原值" )
    @Column(name = "source_price")
    private Integer sourcePrice;

    @ApiModelProperty(value = "资产净值",notes ="资产净值" )
    @Column(name = "net_worth")
    private Integer netWorth;

    @ApiModelProperty(value = "折旧年限",notes ="折旧年限" )
    @Column(name = "useful_life")
    private Integer usefulLife;

    @ApiModelProperty(value = "净残率",notes ="净残率" )
    @Column(name = "net_residue_rate")
    private Double netResidueRate;

    @ApiModelProperty(value = "设备标识，多个标识用逗号分割",notes ="设备标识，多个标识用逗号分割" )
    @Column(name = "device_flag")
    private String deviceFlag;

    @ApiModelProperty(value = "负责人",notes ="负责人" )
    @Column(name = "head")
    private String head;

    @ApiModelProperty(value = "使用状况",notes ="使用状况" )
    @Column(name = "use_status")
    private String useStatus;

    @ApiModelProperty(value = "安装地点",notes ="安装地点" )
    @Column(name = "installation_address")
    private String installationAddress;

    @ApiModelProperty(value = "操作员",notes ="操作员" )
    @Column(name = "operator")
    private String operator;

    @ApiModelProperty(value = "使用部门",notes ="使用部门" )
    @Column(name = "use_dept")
    private String useDept;

    @ApiModelProperty(value = "备注",notes ="备注" )
    @Column(name = "remark")
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
    private Integer extendNumberField;

    @ApiModelProperty(value = "自定义日期",notes ="自定义日期" )
    @Column(name = "extend_date_field")
    private Date extendDateField;

    @ApiModelProperty(value = "是否有效",notes ="是否有效" )
    @Column(name="status_flag")
    private Integer statusFlag=1;

}
