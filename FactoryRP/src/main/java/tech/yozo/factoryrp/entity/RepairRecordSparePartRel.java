package tech.yozo.factoryrp.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2017-12-19 下午3:05
 **/
@Data
@Table(name = "repair_record_spare_part_rel")
@Entity
public class RepairRecordSparePartRel extends BaseEntity implements Serializable{
    @Column(name = "repair_record_id")
    private Long repairRecordId;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "specification")
    private String specificationsAndodels;

    @Column(name = "inventory_upper_limit")
    private Integer inventoryUpperLimit;

    @Column(name = "old_order_num")
    private String oldOrderNum;

    @Column(name = "new_order_num")
    private String newOrderNum;

    @Column(name = "amount")
    private Integer amount;
}
