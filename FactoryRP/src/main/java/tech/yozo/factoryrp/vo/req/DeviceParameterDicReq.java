package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.BaseRequest;
import tech.yozo.factoryrp.vo.validation.NotEmpty;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * 设备参数入参
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/11
 * @description
 */
@ApiModel
@Data
public class DeviceParameterDicReq extends BaseRequest implements Serializable {

    private static final long serialVersionUID = -5555539984574080554L;


    /**
     * 设备参数code，区分不同种类的设备参数 code值可以相同
     */
    @ApiModelProperty(value = "设备参数code,区分不同种类的设备参数 code值可以相同",notes = "设备参数code")
    @NotEmpty(message = "设备参数code")
    private String code;


    /**
     * 设备参数name，同一个code下面的name不可相同
     */
    @ApiModelProperty(value = "设备参数name,区分不同种类的设备参数 code值可以相同",notes = "设备参数name")
    @NotEmpty(message = "设备参数name")
    private String name;

}
