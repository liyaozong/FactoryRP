package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 流程编辑请求对象
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/19
 * @description
 */
@ApiModel
@Data
public class DeviceProcessDetailEditReq implements Serializable {

    private static final long serialVersionUID = -7960906668962348494L;

    /**
     * 流程细节ID
     */
    @ApiModelProperty(value = "流程细节ID",notes ="流程细节ID" )
    private Long detailId;

    /**
     * 流程步骤 对于同一个流程，依次递增
     */
    @ApiModelProperty(value = "流程步骤 对于同一个流程，依次递增",notes ="流程步骤 对于同一个流程，依次递增" )
    private Integer processStep;

    /**
     * 审核类型 标识是指定审核人还是指定审核组
     */
    @ApiModelProperty(value = "审核类型 标识是指定审核人还是指定审核组",notes ="审核类型 标识是指定审核人还是指定审核组" )
    private Integer auditType;


    /**
     * 审核人 这个流程审核关联表的id 多个用逗号分隔
     */
    @ApiModelProperty(value = "审核人 这个流程审核关联表的id 多个用逗号分隔",notes ="审核人 这个流程审核关联表的id 多个用逗号分隔" )
    private List<Long> processAuditor;


    /**
     * 处理要求类型 1单人签署生效2多人签署生效
     */
    @ApiModelProperty(value = "处理要求类型 1单人签署生效2多人签署生效",notes ="处理要求类型 1单人签署生效2多人签署生效" )
    private Integer handleDemandType;

}
