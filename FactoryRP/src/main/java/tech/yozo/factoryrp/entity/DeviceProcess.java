package tech.yozo.factoryrp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 设备流程实体
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
@Data
@Table(name = "device_process")
@Entity
public class DeviceProcess extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 6715128784852431111L;

    /**
     * 流程名称
     */
    @Column(name = "process_name",length = 20)
    private String processName;


    /**
     * 流程类型
     */
    @Column(name = "process_type",length = 20)
    private Long processType;


    /**
     * 流程阶段
     */
    @Column(name = "process_stage",length = 20)
    private Long processStage;

    /**
     * 触发条件类型 对应设备类型 金额上限 部门等
     */
    @Column(name = "trigger_condition_type",length = 20)
    private Long triggerConditionType;


    /**
     * 条件详情
     */
    @Column(name = "trigger_condition",length = 20)
    private Long triggerCondition;

    /**
     * 流程备注
     */
    @Column(name = "process_remark")
    private String processRemark;

}
