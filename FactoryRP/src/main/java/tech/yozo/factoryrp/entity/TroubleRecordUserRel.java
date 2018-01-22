package tech.yozo.factoryrp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 故障处理人员关系表
 * @author chenxiang
 * @create 2018-01-16 上午9:10
 **/
@Data
@Table(name = "trouble_record_user_rel")
@Entity
public class TroubleRecordUserRel extends BaseEntity{
    @Column(name = "trouble_record_id")
    private Long troubleRecordId;
    @Column(name = "deal_user_id")
    private Long dealUserId;
    @Column(name = "deal_user_name")
    private String dealUserName;
    @Column(name = "deal_phase")
    private Integer dealPhase;
    @Column(name = "deal_step")
    private Integer dealStep;
    @Column(name = "execute_type")
    private Integer executeType;
    @Column(name = "deal_status")
    private Integer dealStatus;
    @Column(name = "deal_step_status")
    private Integer dealStepStatus;
}
