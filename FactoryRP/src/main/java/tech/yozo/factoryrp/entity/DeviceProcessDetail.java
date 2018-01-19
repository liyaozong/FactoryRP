package tech.yozo.factoryrp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 设备流程详情
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
@Data
@Table(name = "device_process_detail")
@Entity
public class DeviceProcessDetail extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6936974941234409800L;


    /**
     * 流程步骤 对于同一个流程，依次递增
     */
    @Column(name = "process_step",length = 20)
    private Integer processStep;

    /**
     * 审核类型 标识是指定审核人还是指定审核组 1审核人2审核组 默认审核人
     */
    @Column(name = "process_audit_type",length = 20)
    private Integer auditType = 1;


    /**
     * 审核人 这个流程审核关联表的id 多个用逗号分隔
     */
    @Column(name = "process_auditor")
    private String processAuditor;


    /**
     * 处理要求类型 1单人签署生效2多人签署生效
     */
    @Column(name = "handle_demand_type",length = 20)
    private Integer handleDemandType;


    /**
     * 流程id
     */
    @Column(name = "process_id",length = 20)
    private Long processId;

}
