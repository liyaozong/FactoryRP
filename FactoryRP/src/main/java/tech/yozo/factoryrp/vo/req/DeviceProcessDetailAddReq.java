package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 流程详情新增请求Vo
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/8
 * @description
 */
@ApiModel
@Data
public class DeviceProcessDetailAddReq implements Serializable{

    private static final long serialVersionUID = -3065279321313078966L;

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
