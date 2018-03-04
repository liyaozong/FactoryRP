package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 手机端提交巡检项目详情
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/4
 * @description
 */
@ApiModel
@Data
public class SpotInspectionItemsMobileAddReq implements Serializable {


    /**
     * 巡检记录ID
     */
    @ApiModelProperty(value = "巡检记录ID",notes ="巡检记录ID",example = "1")
    private Long recordId;

    /**
     * 巡检项ID
     */
    @ApiModelProperty(value = "巡检项ID",notes ="巡检项ID",example = "1")
    private Long standardItemId;


    /**
     * 记录结果
     */
    @ApiModelProperty(value = "记录结果 是否有异常 数值等",notes ="记录结果 是否有异常 数值等",example = "1")
    private String recordResult;


    /**
     * 异常情况描述
     */
    @ApiModelProperty(value = "异常情况描述",notes ="异常情况描述",example = "1")
    private String abnormalDesc;


    /**
     *  备注
     */
    @ApiModelProperty(value = "备注",notes ="备注",example = "1")
    private String remark;

}
