package tech.yozo.factoryrp.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author chenxiang
 * @create 2017-12-03 下午3:47
 **/
@Data
@Table(name = "trouble_record")
@Entity
public class TroubleRecord extends BaseEntity implements Serializable{

    @Column(name = "order_no")
    private String orderNo;

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

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "happen_time")
    private Date happenTime;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "create_user_id")
    private Long createUserId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id")
    private DeviceInfo deviceInfo;

    @Column(name = "repair_user_id")
    private Long repairUserId;

    @Column(name = "repair_user_name")
    private String repairUserName;

    @Column(name = "validate_user_id")
    private Long validateUserId;
}
