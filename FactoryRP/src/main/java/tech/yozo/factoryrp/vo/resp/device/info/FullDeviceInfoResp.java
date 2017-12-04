package tech.yozo.factoryrp.vo.resp.device.info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chenxiang
 * @create 2017-12-01 下午10:54
 **/
@Data
@ApiModel(value = "完整设备信息")
public class FullDeviceInfoResp implements Serializable{
    @ApiModelProperty(value = "主键",notes ="主键" )
    private Long id;

    @ApiModelProperty(value = "设备名称",notes ="设备名称" )
    private String name;

    @ApiModelProperty(value = "规格型号",notes ="规格型号" )
    private String specification;

    @ApiModelProperty(value = "设备编号",notes ="设备编号" )
    private String code;

    @ApiModelProperty(value = "生产厂商",notes ="生产厂商" )
    private String manufacturer;

    @ApiModelProperty(value = "供应商",notes ="供应商" )
    private String supplier;

    @ApiModelProperty(value = "设备类别",notes ="设备类别" )
    private String deviceType;

    @ApiModelProperty(value = "购置时间",notes ="购置时间" )
    private Date buyDate;

    @ApiModelProperty(value = "资产原值",notes ="资产原值" )
    private Integer sourcePrice;

    @ApiModelProperty(value = "资产净值",notes ="资产净值" )
    private Integer netWorth;

    @ApiModelProperty(value = "折旧年限",notes ="折旧年限" )
    private Integer usefulLife;

    @ApiModelProperty(value = "净残率",notes ="净残率" )
    private Double netResidueRate;

    @ApiModelProperty(value = "设备标识",notes ="设备标识" )
    private String deviceFlag;

    @ApiModelProperty(value = "负责人",notes ="负责人" )
    private String head;

    @ApiModelProperty(value = "使用状况",notes ="使用状况" )
    private String useStatus;

    @ApiModelProperty(value = "安装地点",notes ="安装地点" )
    private String installationAddress;

    @ApiModelProperty(value = "操作员",notes ="操作员" )
    private String operator;

    @ApiModelProperty(value = "使用部门",notes ="使用部门" )
    private String useDept;

    @ApiModelProperty(value = "备注",notes ="备注" )
    private String remark;
}
