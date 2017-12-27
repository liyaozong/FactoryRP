package tech.yozo.factoryrp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 流程运行时数据
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
@Table(name = "device_process_runtime_info")
@Entity
@Data
public class ProcessRuntimeInfo extends BaseEntity implements Serializable {


    /**
     * 流程实例ID，表明是属于哪个流程实例的
     */
    @Column(name = "process_instance_id",length = 20)
    private Long processId;


    /**
     * 流程运行时状态 1正在运行2成功3失败
     */
    @Column(name = "process_runtime_status",length = 20)
    private Integer processRuntimeStatus;


    /**
     * 处理成功的人数
     */
    @Column(name = "handler_success_number",length = 20)
    private Integer handlerSuccessNumber;

}
