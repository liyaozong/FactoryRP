package tech.yozo.factoryrp.vo.resp.inspection.mobile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 手机端查询巡检计划关联的设备
 */
@ApiModel
@Data
public class SpotInspectionPlanDeviceQueryResp implements Serializable {


    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "设备名称")
    private String name;

    @ApiModelProperty(value = "规格型号")
    private String specification;

    @ApiModelProperty(value = "设备类别ID",notes ="设备类别ID" )
    private Long deviceTypeId;

    @ApiModelProperty(value = "设备编号")
    private String code;

    @ApiModelProperty(value = "安装地点")
    private String installationAddress;

    @ApiModelProperty(value = "使用部门")
    private Long useDept;

    @ApiModelProperty(value = "设备关联的巡检标准")
    private Long deviceStandard;

    @ApiModelProperty(value = "图片集合")
    private List<Long> imageList;

}
