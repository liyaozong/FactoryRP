package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.validation.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * 点检标准项细节查询返回VO
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/23
 * @description
 */
@ApiModel
@Data
public class SpotInspectionStandardItemsQueryResp implements Serializable {

    private static final long serialVersionUID = 6313045572385406968L;

    @ApiModelProperty(value = "巡检项目ID",notes ="巡检项目ID" )
    private Long itemId;

    /**
     * 巡检项目名称
     */
    @ApiModelProperty(value = "巡检项目名称",notes ="巡检项目名称" )
    @NotEmpty(message = "巡检项目名称不可为空")
    private String name;

    /**
     * 记录方式名称
     */
    @ApiModelProperty(value = "记录方式名称",notes ="记录方式名称" )
    private String recordTypeName;

    /**
     * 输入限制规则，比如列表，文字等，根据记录方式规则限定
     */
    @ApiModelProperty(value = "输入限制规则，比如列表，文字等，根据记录方式规则限定",notes ="输入限制规则，比如列表，文字等，根据记录方式规则限定" )
    private List<String> inputLimitValue;


    /**
     * 上限值
     */
    @ApiModelProperty(value = "上限值",notes ="上限值" )
    private Integer upperLimit;

    /**
     * 下限值
     */
    @ApiModelProperty(value = "下限值",notes ="下限值" )
    private Integer lowerLimit;

    /**
     * 是否执行了提交 1执行2未执行
     */
    @ApiModelProperty(value = "是否执行了提交 1执行2未执行",notes ="是否执行了提交 1执行2未执行" )
    private Integer inspectionStatus;

    /**
     * 如果inspectionStatus字段返回1改字段会被返回，执行的时候需要这个字段
     */
    @ApiModelProperty(value = "如果inspectionStatus字段返回1改字段会被返回，执行的时候需要这个字段",notes ="如果inspectionStatus字段返回1改字段会被返回，执行的时候需要这个字段" )
    private Long executeDetailId;

    /**
     * 记录结果
     */
    @ApiModelProperty(value = "记录结果 是否有异常 数值等",notes ="记录结果 是否有异常 数值等",example = "1")
    private String recordResult;


    /**
     * 异常情况描述 1无异常2有异常
     */
    @ApiModelProperty(value = "异常情况描述 1无异常2有异常",notes ="异常情况描述 1无异常2有异常",example = "1")
    private String abnormalDesc;


    /**
     *  备注
     */
    @ApiModelProperty(value = "备注",notes ="备注",example = "1")
    private String remark;

}
