package tech.yozo.factoryrp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 点检计划-设备关联
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/25
 * @description
 */
@Table(name = "spot_inspection_plan_device")
@Entity
@Data
public class SpotInspectionPlanDevice extends BaseEntity implements Serializable {

    /**
     * 点检计划ID
     */
    @ApiModelProperty(value = "点检计划ID",notes ="点检计划ID" )
    @Column(name = "spot_inspection_plan",length = 20)
    private Long spotInspectionPlan;


    /**
     * 点检标准ID
     */
    @ApiModelProperty(value = "点检标准ID",notes ="点检标准ID" )
    @Column(name = "spot_inspection_standard",length = 20)
    private Long SpotInspectionStandard;

    /**
     * 路线顺序
     */
    @ApiModelProperty(value = "路线顺序",notes ="路线顺序" )
    @Column(name = "line_order",length = 20)
    private Integer lineOrder;


    /**
     *适用设备类型
     */
    @ApiModelProperty(value = "适用设备类型",notes ="适用设备类型" )
    @Column(name = "device_type",length = 50)
    private Long deviceType;

    /**
     * 适用设备列表 [1,2,3] 这种格式，存放设备ID
     */
    @ApiModelProperty(value = "适用设备列表",notes ="适用设备列表" )
    @Column(name = "relate_devices",length = 200)
    private String relateDevices;

}
