package tech.yozo.factoryrp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 备件类型实体
 *
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/5
 * @description
 */
@Data
@Table(name = "device_spare_type")
@Entity
public class DeviceSparesType extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -3598180151719836169L;


    @ApiModelProperty(value = "备件名称",notes ="备件名称")
    @Column(name = "name",length = 20)
    private String name;

    @ApiModelProperty(value = "上级ID",notes ="上级ID" )
    @Column(name = "parent_id",length = 20)
    private Long parentId;

    @ApiModelProperty(value = "显示顺序",notes ="显示顺序" )
    @Column(name = "show_order",length = 20)
    private Integer showOrder;

    @ApiModelProperty(value = "是否有效",notes ="是否有效" )
    @Column(name="status_flag",length = 20)
    private Integer statusFlag;

}
