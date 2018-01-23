package tech.yozo.factoryrp.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/13
 * @description
 */
@ApiModel
@Data
public class DicEnumResp implements Serializable{

    private static final long serialVersionUID = -5872519921177710245L;

    @ApiModelProperty(value = "设备字典code",required = true,notes = "设备字典code",example = "measuring_unit")
    private String code;

    @ApiModelProperty(value = "设备字典name",required = true,notes = "设备字典name",example = "000000")
    private String name;


}
