package tech.yozo.factoryrp.vo.resp.device.info;

import lombok.Data;

@Data
public class SimpleDeviceInfoResp {

//    @ApiModelProperty(value = "主键")
    private Long id;

//    @ApiModelProperty(value = "设备名称")
    private String name;

//    @ApiModelProperty(value = "规格型号")
    private String specification;

//    @ApiModelProperty(value = "设备编号")
    private String code;

//    @ApiModelProperty(value = "安装地点")
    private String installationAddress;

//    @ApiModelProperty(value = "使用部门")
    private String useDept;
}
