package tech.yozo.factoryrp.vo.resp.process;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 流程细节查询返回VO
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/19
 * @description
 */
@ApiModel
@Data
public class DeviceProcessDetailQueryResp implements Serializable {

    private static final long serialVersionUID = -5014092912334704882L;

    /**
     * 流程步骤 对于同一个流程，依次递增
     */
    @ApiModelProperty(value = "流程步骤 对于同一个流程，依次递增",required = true,notes = "流程步骤 对于同一个流程，依次递增",example = "processStep")
    private Integer processStep;

    /**
     * 审核类型 标识是指定审核人还是指定审核组 1审核人2审核组 默认审核人
     */
    @ApiModelProperty(value = "审核类型 标识是指定审核人还是指定审核组 1审核人2审核组 默认审核人",required = true,notes = "审核类型 标识是指定审核人还是指定审核组 1审核人2审核组 默认审核人",example = "auditType")
    private Integer auditType;


    /**
     * 审核人姓名集合
     */
    @ApiModelProperty(value = "审核人姓名集合",required = true,notes = "审核人姓名集合",example = "processAuditorList")
    private List<String> processAuditorList;


    /**
     * 处理要求类型 1单人签署生效2多人签署生效
     */
    @ApiModelProperty(value = "处理要求类型 1单人签署生效2多人签署生效",required = true,notes = "处理要求类型 1单人签署生效2多人签署生效",example = "handleDemandType")
    private Integer handleDemandType;


    /**
     * 流程id
     */
    @ApiModelProperty(value = "流程id",required = true,notes = "流程id",example = "processId")
    private Long processId;


}
