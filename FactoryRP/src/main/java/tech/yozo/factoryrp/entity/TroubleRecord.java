package tech.yozo.factoryrp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenxiang
 * @create 2017-12-03 下午3:47
 **/
@Data
@Table(name = "trouble_recode")
@Entity
public class TroubleRecord extends BaseEntity implements Serializable{

    @Column(name = "device_id")
    private Long deviceId;

    @Column(name = "trouble_level")
    private Integer troubleLevel;

    @Column(name = "trouble_type")
    private Long troubleType;

    @Column(name = "repair_group_id")
    private Long repairGroupId;

    @Column(name = "device_status")
    private Integer deviceStatus;

    @Column(name = "device_user")
    private String deviceUser;

    @Column(name = "phone")
    private String phone;

    @Column(name = "device_address")
    private String deviceAddress;

    @Column(name = "remark")
    private String remark;

    @Column(name = "happen_time")
    private Date happenTime;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_user")
    private String createUser;
}
