package cn.tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 设备信息请求参数
 */
@Data
public class DeviceInfoReq implements Serializable{

    @ApiModelProperty(value = "设备名称",notes ="设备名称",example = "空气压缩机")
    private String name;

    @ApiModelProperty(value = "设备编号",notes ="设备编号",example = "FZK-08-00")
    private String code;

    @ApiModelProperty(value = "生产厂商",notes ="生产厂商",example = "1")
    private Long manufacturer;

    @ApiModelProperty(value = "供应商",notes ="供应商",example = "2")
    private Long supplier;

    @ApiModelProperty(value = "设备类别",notes ="设备类别",example = "3")
    private Long deviceType;

    @ApiModelProperty(value = "购置时间",notes ="购置时间",example = "2016/11/14")
    private Date buyDate;

    @ApiModelProperty(value = "负责人",notes ="负责人",example = "王兵")
    private String head;

    @ApiModelProperty(value = "使用状况",notes ="使用状况",example = "1")
    private Integer useStatus;

    @ApiModelProperty(value = "使用部门",notes ="使用部门",example = "5")
    private Long useDept;

    @ApiModelProperty(value = "当前页码",example = "1")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示记录数",example = "10")
    private Integer itemsPerPage;
}
