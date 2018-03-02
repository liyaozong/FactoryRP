package tech.yozo.factoryrp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2018-03-02 下午2:03
 **/
@Data
@Table(name = "maintain_record")
@Entity
public class MaintainRecord extends BaseEntity implements Serializable{
    @Column(name = "maintain_plan_id")
    private Long maintainPlanId;

    @Column(name = "maintain_status")
    private Integer maintainStatus;

    @Column(name = "maintain_no")
    private String maintainNo;

    @Column(name = "maintain_content")
    private String maintainContent;

    @Column(name = "stoped")
    private Integer stoped;

    @Column(name = "stoped_hour")
    private Integer stopedHour;

    @Column(name = "cost_hour")
    private Integer costHour;

    @Column(name = "maintain_amount")
    private String maintainAmount;
}
