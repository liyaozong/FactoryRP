package tech.yozo.factoryrp.vo.resp.device.info;

import lombok.Data;

import java.io.Serializable;

@Data
public class SimpleDeviceInfoResp implements Serializable {

//    @ApiModelProperty(value = "主键")
    protected Long id;

//    @ApiModelProperty(value = "设备名称")
    protected String name;

//    @ApiModelProperty(value = "规格型号")
    protected String specification;

//    @ApiModelProperty(value = "设备编号")
    protected String code;

//    @ApiModelProperty(value = "安装地点")
    protected String installationAddress;

//    @ApiModelProperty(value = "使用部门")
    protected String useDept;
}
