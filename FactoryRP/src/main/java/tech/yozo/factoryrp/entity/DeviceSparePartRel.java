package tech.yozo.factoryrp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 设备备件关联信息
 * @author chenxiang
 * @create 2017-12-02 上午10:27
 **/
@Data
@Table(name = "device_spare_part_rel")
@Entity
public class DeviceSparePartRel extends BaseEntity implements Serializable{

    @Column(name = "device_id")
    private Long deviceId;

    @Column(name = "spare_part_id")
    private Long sparePartId;
}
