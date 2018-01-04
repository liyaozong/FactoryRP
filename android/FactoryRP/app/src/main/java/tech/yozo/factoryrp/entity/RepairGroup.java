package tech.yozo.factoryrp.entity;

//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//import javax.persistence.Entity;
//import javax.persistence.Table;
import java.io.Serializable;

//@Table(name = "repair_group_info")
//@Entity
@Data
public class RepairGroup {

//    @ApiModelProperty(value = "班组编码")
    private String code;

//    @ApiModelProperty(value = "班组名称")
    private String name;
}
