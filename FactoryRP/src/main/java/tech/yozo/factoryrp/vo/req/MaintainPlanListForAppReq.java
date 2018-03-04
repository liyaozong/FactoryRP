package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.BaseRequest;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2018-03-03 上午9:24
 **/
@Data
public class MaintainPlanListForAppReq extends BaseRequest implements Serializable {

    @ApiModelProperty(value = "计划状态(1:今日计划；2：明日计划；3：已过期计划)",example = "1")
    private Integer planType;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "设备编号")
    private String deviceCode;

    @ApiModelProperty(value = "设备规格型号")
    private String deviceSpec;

    @ApiModelProperty(value = "维修班组")
    private Long repairGroupId;

    @ApiModelProperty(value = "当前页码",example = "1")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示记录数",example = "10")
    private Integer itemsPerPage;
}
