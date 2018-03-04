package tech.yozo.factoryrp.vo.resp.inspect;

import lombok.Data;

import java.util.List;

@Data
public class InspectItemResp {

//    @ApiModelProperty(value = "巡检项目ID",notes ="巡检项目ID" )
    private Long itemId;

    /**
     * 巡检项目名称
     */
//    @ApiModelProperty(value = "巡检项目名称",notes ="巡检项目名称" )
//    @NotEmpty(message = "巡检项目名称不可为空")
    private String name;

    /**
     * 记录方式名称
     */
//    @ApiModelProperty(value = "记录方式名称",notes ="记录方式名称" )
    private String recordTypeName;

    /**
     * 输入限制规则，比如列表，文字等，根据记录方式规则限定
     */
//    @ApiModelProperty(value = "输入限制规则，比如列表，文字等，根据记录方式规则限定",notes ="输入限制规则，比如列表，文字等，根据记录方式规则限定" )
    private List<String> inputLimitValue;


    /**
     * 上限值
     */
//    @ApiModelProperty(value = "上限值",notes ="上限值" )
    private Integer upperLimit;

    /**
     * 下限值
     */
//    @ApiModelProperty(value = "下限值",notes ="下限值" )
    private Integer lowerLimit;

    /**
     * 是否执行了提交 1执行2未执行
     */
//    @ApiModelProperty(value = "是否执行了提交 1执行2未执行",notes ="是否执行了提交 1执行2未执行" )
    private Integer inspectionStatus;
}
