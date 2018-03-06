package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 点检计划执行请求对象
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/6
 * @description
 */
@ApiModel
@Data
public class SpotInspectionPlanExecuteReq implements Serializable {

    /**
     * 巡检计划-设备对应ID
     */
    @ApiModelProperty(value = "巡检计划-设备对应ID",notes ="巡检计划-设备对应ID" )
    private Long planDeviceId;

    /**
     * 巡检标准ID
     */
    @ApiModelProperty(value = "巡检标准ID",notes ="巡检标准ID" )
    private Long standardId;


    /**
     * 巡检项集合
     */
    @ApiModelProperty(value = "巡检项集合",notes ="巡检项集合" )
    private List<SpotInspectionPlanExecuteItemReq> itemList;


}
