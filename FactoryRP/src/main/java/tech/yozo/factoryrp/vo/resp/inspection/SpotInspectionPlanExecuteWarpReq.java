package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 巡检计划执行请求包装对象
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/6
 * @description
 */
@ApiModel
@Data
public class SpotInspectionPlanExecuteWarpReq implements Serializable{

    private static final long serialVersionUID = -357799471796650671L;

    @ApiModelProperty(value = "巡检计划ID",notes ="巡检计划ID" )
    private Long planId;


    @ApiModelProperty(value = "巡检计划-设备请求集合",notes ="巡检计划-设备请求集合" )
    List<SpotInspectionPlanExecuteReq> list;


}
