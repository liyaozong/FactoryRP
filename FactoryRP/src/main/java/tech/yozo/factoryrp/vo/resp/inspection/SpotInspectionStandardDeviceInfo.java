package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 点检标准--设备信息
 */
@ApiModel
@Data
public class SpotInspectionStandardDeviceInfo implements Serializable {


    /**
     * 关联设备ID
     */
    @ApiModelProperty(value = "关联设备类型ID",notes ="关联设备类型ID",example = "1",dataType = "Long")
    private Long id;


    /**
     * 设备名称
     */
    @ApiModelProperty(value = "设备名称",notes ="设备名称" )
    private String name;


    /**
     * 规格型号
     */
    @ApiModelProperty(value = "规格型号",notes ="规格型号" )
    private String specification;


    /**
     * 设备编号
     */
    @ApiModelProperty(value = "设备编号",notes ="设备编号" )
    private String code;

}
