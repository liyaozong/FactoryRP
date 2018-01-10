package tech.yozo.factoryrp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 流程阶段
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/10
 * @description
 */
@Data
@Table(name = "device_process_phase")
@Entity
public class DeviceProcessPhase extends BaseEntity implements Serializable {


    /**
     * 流程阶段
     */
    @Column(name = "device_process_phase",length = 50)
    private String deviceProcessPhase;


    /**
     * 排序号
     */
    @Column(name = "order_number",length = 11)
    private Integer orderNumber;

}
