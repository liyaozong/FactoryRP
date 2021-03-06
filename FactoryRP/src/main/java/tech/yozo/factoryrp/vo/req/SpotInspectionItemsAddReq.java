package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.ApiRequest;
import tech.yozo.factoryrp.vo.validation.NotEmpty;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/23
 * @description
 */
@ApiModel
@Data
public class SpotInspectionItemsAddReq extends ApiRequest implements Serializable {


    private static final long serialVersionUID = 1219856302280691065L;

    /**
     * 巡检项目名称
     */
    @ApiModelProperty(value = "巡检项目名称",notes ="巡检项目名称" )
    @NotEmpty(message = "巡检项目名称不可为空")
    private String name;

    /**
     * 记录方式
     */
    @ApiModelProperty(value = "记录方式",notes ="记录方式" )
    private String recordType;

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
     * 设备部位
     */
    @ApiModelProperty(value = "设备部位",notes ="设备部位" )
    private String devicePlace;

    /**
     * 点检方法
     */
    @ApiModelProperty(value = "点检方法",notes ="设备部位" )
    private String spotInspectionWay;
}
