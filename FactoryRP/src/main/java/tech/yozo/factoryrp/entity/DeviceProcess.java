package tech.yozo.factoryrp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 设备流程实体-设备流程基本信息
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
    @ApiModelProperty(value = "流程名称",notes = "流程名称",example = "测试流程1")
    private String processName;


    /**
     * 流程类型
     */
    @Column(name = "process_type",length = 20)
    @ApiModelProperty(value = "流程类型",notes = "流程类型",example = "1")
    private String processType;


    /**
     * 流程阶段
     */
    @Column(name = "process_stage",length = 20)
    @ApiModelProperty(value = "流程阶段",notes = "流程阶段",example = "1")
    private String processStage;

    /**
     * 触发条件类型 对应设备类型 金额上限 部门等
     */
    @Column(name = "trigger_condition_type",length = 20)
    @ApiModelProperty(value = "触发条件类型 对应设备类型 金额上限 部门等",notes = "触发条件类型 对应设备类型 金额上限 部门等",example = "1")
    private Long triggerConditionType;


    /**
     * 条件详情
     */
    @Column(name = "trigger_condition",length = 20)
    @ApiModelProperty(value = "条件详情",notes = "条件详情",example = "1")
    private Long triggerCondition;

    /**
     * 流程备注
     */
    @Column(name = "process_remark")
    @ApiModelProperty(value = "流程备注",notes = "流程备注",example = "1")
    private String processRemark;

}
