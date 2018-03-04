package tech.yozo.factoryrp.vo.resp.inspect;

import lombok.Data;

@Data
public class InspectDevicesResp {
//    @ApiModelProperty(value = "主键")
    private Long id;

//    @ApiModelProperty(value = "设备名称")
    private String name;

//    @ApiModelProperty(value = "规格型号")
    private String specification;

//    @ApiModelProperty(value = "设备类别ID",notes ="设备类别ID" )
    private Long deviceTypeId;

//    @ApiModelProperty(value = "设备编号")
    private String code;

//    @ApiModelProperty(value = "安装地点")
    private String installationAddress;

//    @ApiModelProperty(value = "使用部门")
    private Long useDept;
}
