package tech.yozo.factoryrp.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenxiang
 * @create 2017-12-19 下午3:01
 **/
@Data
@Table(name = "work_time")
@Entity
public class WorkTime extends BaseEntity implements Serializable{

    @Column(name = "type")
    private Integer type;

    @Column(name = "repair_record_id")
    private Long repairRecordId;

    @Column(name = "repair_user_id")
    private Long repairUserId;

    @Column(name = "repair_user_name")
    private String repairUserName;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "start_time")
    private Date startTime;

    @JSONField (format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "cost_hour")
    private Integer costHour;
}
