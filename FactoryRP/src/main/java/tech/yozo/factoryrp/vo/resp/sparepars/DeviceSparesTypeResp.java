package tech.yozo.factoryrp.vo.resp.sparepars;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * 备件类型返回Vo
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/5
 * @description
 */
@ApiModel
@Data
public class DeviceSparesTypeResp implements Serializable {


    @ApiModelProperty(value = "备件id",notes ="备件id")
    private Long id;

    @ApiModelProperty(value = "备件名称",notes ="备件名称")
    private String name;

    @ApiModelProperty(value = "上级ID",notes ="上级ID" )
    private Long parentId;

    @ApiModelProperty(value = "显示顺序",notes ="显示顺序" )
    private Integer showOrder;

    @ApiModelProperty(value = "是否有效",notes ="是否有效" )
    private Integer statusFlag;

}
