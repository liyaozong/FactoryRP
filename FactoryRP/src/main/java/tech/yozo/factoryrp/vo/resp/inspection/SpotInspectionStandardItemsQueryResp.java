package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.validation.NotEmpty;

import java.io.Serializable;
import java.util.List;

/**
 * 点检标准项查询返回VO
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/23
 * @description
 */
@ApiModel
@Data
public class SpotInspectionStandardItemsQueryResp implements Serializable {

    private static final long serialVersionUID = 6313045572385406968L;

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


}
