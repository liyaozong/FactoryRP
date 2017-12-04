package tech.yozo.factoryrp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 部门信息
 */
@Table(name = "department_info")
@Entity
@Data
public class Department extends BaseEntity implements Serializable{

    @ApiModelProperty(value = "部门编码",notes ="部门编码" )
    private String code;

    @ApiModelProperty(value = "部门名称",notes ="部门名称" )
    private String name;

    @ApiModelProperty(value = "上级部门ID",notes ="上级部门ID" )
    @Column(name = "parent_id")
    private Long parentId;

    @ApiModelProperty(value = "显示顺序",notes ="显示顺序" )
    @Column(name = "show_order")
    private Integer showOrder;

    @ApiModelProperty(value = "是否有效",notes ="是否有效" )
    @Column(name="status_flag")
    private Integer statusFlag=1;
}
