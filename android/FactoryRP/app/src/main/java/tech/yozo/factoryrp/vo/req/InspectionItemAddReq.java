package tech.yozo.factoryrp.vo.req;

import lombok.Data;

/**
 *
 * 手机端提交巡检项目详情
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/4
 * @description
 */
//@ApiModel
@Data
public class InspectionItemAddReq {


    /**
     * 巡检项ID
     */
//    @ApiModelProperty(value = "巡检项ID",notes ="巡检项ID",example = "1")
    private Long itemId;


    /**
     * 记录结果
     */
//    @ApiModelProperty(value = "记录结果 是否有异常 数值等",notes ="记录结果 是否有异常 数值等",example = "1")
    private String recordResult;


    /**
     * 异常情况描述 1无异常2有异常
     */
//    @ApiModelProperty(value = "异常情况描述 1无异常2有异常",notes ="异常情况描述 1无异常2有异常",example = "1")
    private String abnormalDesc;


    /**
     *  备注
     */
//    @ApiModelProperty(value = "备注",notes ="备注",example = "1")
    private String remark;

}
