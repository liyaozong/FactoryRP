package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 巡检记录详情包装VO
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/5
 * @description
 */
@ApiModel
@Data
public class SpotInspectionRecordDetailWarpResp implements Serializable {

    private static final long serialVersionUID = -3149211652492625768L;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称",notes ="名称",example = "1")
    private String planName;

    /**
     * 巡检时间
     */
    @ApiModelProperty(value = "巡检时间",notes ="巡检时间",example = "2018-03-05 13:48:13")
    private String executeTime;

    /**
     * 巡检人
     */
    @ApiModelProperty(value = "巡检人",notes ="巡检人",example = "张三")
    private String executor;

    /**
     * 异常处理情况
     */
    @ApiModelProperty(value = "异常处理情况",notes ="异常处理情况",example = "未处理")
    private String exceptionHandleDesc;

    /**
     * 巡检记录详情
     */
    @ApiModelProperty(value = "巡检记录详情",notes ="巡检记录详情",example = "List")
    private List<SpotInspectionRecordDetailResp> detailList;

}
