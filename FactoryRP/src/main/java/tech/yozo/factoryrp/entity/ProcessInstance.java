package tech.yozo.factoryrp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 流程实例表  每发起流程就会产生数据
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
@Table(name = "device_process_instance")
@Entity
@Data
public class ProcessInstance extends BaseEntity implements Serializable {


    /**
     * 流程id,表明开启哪种流程
     */
    @Column(name = "process_id",length = 20)
    private Long processId;

    /**
     * 流程实例状态，1开启，2正在进行，3正常结束，4终止流程,5流程设置因为中途改变而作废
     */
    @Column(name = "process_status",length = 20)
    private Integer processStatus;

    /**
     * 当前实例所处步骤 在processStatus为1的情况下为空
     */
    @Column(name = "current_step",length = 20)
    private Integer currentStep;

}
