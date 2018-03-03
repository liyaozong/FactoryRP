package tech.yozo.factoryrp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenxiang
 * @create 2018-03-02 下午1:35
 **/
@Data
@Table(name = "maintain_plan")
@Entity
public class MaintainPlan extends BaseEntity implements Serializable{
    @Column(name = "plan_status")
    private Integer planStatus;

    @Column(name = "device_id")
    private Long deviceId;

    @Column(name = "device_code")
    private String deviceCode;

    @Column(name = "device_name")
    private String deviceName;

    @Column(name = "device_spec")
    private String deviceSpec;

    @Column(name = "use_dept")
    private Long deviceUseDept;

    @Column(name = "use_dept_name")
    private String deviceUseDeptName;

    @Column(name = "device_type")
    private Long deviceType;

    @Column(name = "device_type_name")
    private String deviceTypeName;

    @Column(name = "device_address")
    private String deviceAddress;

    @Column(name = "maintain_level")
    private Integer maintainLevel;

    @Column(name = "repair_group_id")
    private Long repairGroupId;

    @Column(name = "cycle_type")
    private Integer cycleType;

    @Column(name = "cycle_time_value")
    private String cycleTimeValue;

    @Column(name = "cycle_time_unit")
    private String cycleTimeUnit;

    @Column(name = "maintain_part")
    private String maintainPart;

    @Column(name = "maintain_standard")
    private String maintainStandard;

    @Column(name = "last_maintain_time")
    private Date lastMaintainTime;

    @Column(name = "plan_maintain_time_start")
    private Date planMaintainTimeStart;

    @Column(name = "plan_maintain_time_end")
    private Date planMaintainTimeEnd;

    @Column(name = "plan_manager_id")
    private Long planManagerId;

    @Column(name = "plan_manager_name")
    private String planManagerName;

    @Column(name = "plan_remark")
    private String planRemark;
}
