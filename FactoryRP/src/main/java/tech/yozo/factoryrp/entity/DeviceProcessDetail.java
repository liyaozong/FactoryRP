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
    private Long processStep;

    /**
     * 审核类型 标识是指定审核人还是指定审核组
     */
    @Column(name = "process_type",length = 20)
    private Long auditType;


    /**
     * 审核人 这个流程审核关联表的id
     */
    @Column(name = "process_auditor",length = 20)
    private Long processAuditor;


    /**
     * 处理要求
     */
    @Column(name = "handle_demand",length = 20)
    private Long handleDemand;


    /**
     * 流程id
     */
    @Column(name = "process_id",length = 20)
    private Long processId;

}
