package tech.yozo.factoryrp.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenxiang
 * @create 2017-12-15 下午9:28
 **/
@Data
@Table(name = "repair_record")
@Entity
public class RepairRecord extends BaseEntity implements Serializable{
    @Column(name = "trouble_record_id")
    private Long troubleRecordId;

    @Column(name = "repair_status")
    private Integer repairStatus;

    @OneToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "trouble_type_id")
    private DeviceTroubleType troubleType;

    @Column(name = "trouble_reason")
    private Integer troubleReason;

    @Column(name = "repair_level")
    private Integer repairLevel;

    @Column(name = "repair_amount")
    private String repairAmount;

    @Column(name = "work_remark")
    private String workRemark;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_time")
    private Date startTime;

    @JSONField (format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "repair_type")
    private Integer repairType;

    @Column(name = "out_repair_company")
    private String outRepairCompany;

    @Column(name = "out_repair_men")
    private String outRepairMen;

    @Column(name = "stoped")
    private Integer stoped;

    @Column(name = "stoped_hour")
    private String stopedHour;

    @Column(name = "cost_hour")
    private String costHour;
}
