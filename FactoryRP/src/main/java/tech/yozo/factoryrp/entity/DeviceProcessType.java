package tech.yozo.factoryrp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 设备流程类型
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/10
 * @description
 */
@Data
@Table(name = "device_process_type")
@Entity
public class DeviceProcessType extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 5997913903106313764L;


    /**
     * 流程类型
     */
    @ApiModelProperty(value = "流程类型",notes ="流程类型",example = "1")
    @Column(name = "device_process_type",length = 50)
    private String deviceProcessType;


    /**
     * 流程类型排序号
     */
    @ApiModelProperty(value = "流程类型排序号",notes ="流程类型排序号",example = "1")
    @Column(name = "order_number",length = 11)
    private Integer orderNumber;

    /**
     * 类型枚举代码
     */
    @ApiModelProperty(value = "类型枚举代码",notes ="类型枚举代码",example = "device_process_give_back")
    @Column(name = "code",length = 50)
    private String code;

    /**
     * 对应流程阶段的单向一对多级联
     */
    @ApiModelProperty(value = "对应流程阶段集合",notes ="对应流程阶段集合")
    @OneToMany(cascade=CascadeType.ALL,fetch= FetchType.LAZY)//级联保存、更新、删除、刷新;延迟加载
    @JoinColumn(name="device_process_type_id")//在device_process_phase表增加一个外键列来实现一对多的单向关联
    private List<DeviceProcessPhase> deviceProcessPhaseList;

}
