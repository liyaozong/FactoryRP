package tech.yozo.factoryrp.entity;

import io.swagger.annotations.ApiModelProperty;
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


    /**
     * 阶段枚举代码
     */
    @ApiModelProperty(value = "阶段枚举代码",notes ="阶段枚举代码",example = "device_process_phase_application_approval")
    @Column(name = "code",length = 50)
    private String code;

}
