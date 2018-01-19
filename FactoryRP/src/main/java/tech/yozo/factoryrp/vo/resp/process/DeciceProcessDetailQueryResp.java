package tech.yozo.factoryrp.vo.resp.process;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
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
public class DeciceProcessDetailQueryResp implements Serializable {

    private static final long serialVersionUID = -5014092912334704882L;

    /**
     * 流程步骤 对于同一个流程，依次递增
     */
    @ApiModelProperty(value = "流程步骤 对于同一个流程，依次递增",required = true,notes = "流程步骤 对于同一个流程，依次递增",example = "processStep")
    private Long processStep;

    /**
     * 审核类型 标识是指定审核人还是指定审核组
     */
    @ApiModelProperty(value = "审核类型 标识是指定审核人还是指定审核组",required = true,notes = "审核类型 标识是指定审核人还是指定审核组",example = "auditType")
    @Column(name = "process_type",length = 20)
    private Long auditType;


    /**
     * 审核人姓名集合
     */
    @ApiModelProperty(value = "流程步骤 对于同一个流程，依次递增",required = true,notes = "流程步骤 对于同一个流程，依次递增",example = "processStep")
    @Column(name = "process_auditor")
    private List<String> processAuditorList;


    /**
     * 处理要求类型 1单人签署生效2多人签署生效
     */
    @ApiModelProperty(value = "流程步骤 对于同一个流程，依次递增",required = true,notes = "流程步骤 对于同一个流程，依次递增",example = "processStep")
    private Integer handleDemandType;


    /**
     * 流程id
     */
    @ApiModelProperty(value = "流程步骤 对于同一个流程，依次递增",required = true,notes = "流程步骤 对于同一个流程，依次递增",example = "processStep")
    private Long processId;


}
