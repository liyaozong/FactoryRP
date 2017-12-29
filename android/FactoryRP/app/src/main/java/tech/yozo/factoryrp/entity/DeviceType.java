package tech.yozo.factoryrp.entity;

//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Table;
//import java.io.Serializable;

/**
 * 设备类型信息
 */
//@Table(name = "device_type")
//@Entity
@Data
public class DeviceType {
    private Long id;

//    @ApiModelProperty(value = "设备类型名称",notes ="设备类型名称" )
//    @Column(name = "name",length = 50)
    private String name;

//    @ApiModelProperty(value = "上级ID",notes ="上级ID" )
//    @Column(name = "parent_id",length = 20)
    private Long parentId;

//    @ApiModelProperty(value = "显示顺序",notes ="显示顺序" )
//    @Column(name = "show_order",length = 20)
//    private Integer showOrder;

//    @ApiModelProperty(value = "是否有效",notes ="是否有效" )
//    @Column(name="status_flag",length = 20)
//    private Integer statusFlag;
}
