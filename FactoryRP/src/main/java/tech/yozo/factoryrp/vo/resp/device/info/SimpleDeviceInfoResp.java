package tech.yozo.factoryrp.vo.resp.device.info;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2017-12-01 下午10:23
 **/
@Data
@ApiModel(value = "简略设备信息")
public class SimpleDeviceInfoResp implements Serializable{

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "规格型号")
    private String specification;

    @ApiModelProperty(value = "设备编号")
    private String code;

    @ApiModelProperty(value = "安装地点")
    private String installationAddress;

    @ApiModelProperty(value = "使用部门")
    private String useDept;
}
