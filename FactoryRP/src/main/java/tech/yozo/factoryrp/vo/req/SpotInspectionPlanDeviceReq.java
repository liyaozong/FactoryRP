package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

/**
 * 点检计划-设备关联请求VO
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/25
 * @description
 */
@ApiModel
@Data
public class SpotInspectionPlanDeviceReq implements Serializable {


    /**
     * 点检标准ID
     */
    @ApiModelProperty(value = "点检标准ID",notes ="点检标准ID",example = "1")
    private Long spotInspectionStandard;


    /**
     * 路线顺序
     */
    @ApiModelProperty(value = "路线顺序",notes ="路线顺序",example = "1" )
    private Integer lineOrder;

    /**
     *适用设备类型
     */
    @ApiModelProperty(value = "适用设备类型",notes ="适用设备类型",example = "1" )
    private Long deviceType;

    /**
     * 适用设备ID
     */
    @ApiModelProperty(value = "适用设备ID",notes ="适用设备ID",example = "1" )
    private Long deviceId;

}
