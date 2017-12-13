package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.BaseRequest;
import tech.yozo.factoryrp.vo.validation.NotEmpty;

import java.io.Serializable;

/**
 *
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/13
 * @description
 */
@ApiModel
@Data
public class DeviceParameterDicBatchAddReq extends BaseRequest implements Serializable {

    /**
     * 设备参数code，区分不同种类的设备参数 code值可以相同
     */
    @ApiModelProperty(value = "设备参数code,区分不同种类的设备参数 code值可以相同",notes = "设备参数code")
    @NotEmpty(message = "设备参数code不能为空")
    private String code;


    /**
     * 设备参数name，同一个code下面的name不可相同
     */
    @ApiModelProperty(value = "设备参数name,区分不同种类的设备参数 code值可以相同，不同的值用逗号分隔"
            ,notes = "设备参数name,区分不同种类的设备参数 code值可以相同，不同的值用逗号分隔")
    @NotEmpty(message = "设备参数name不能为空")
    private String name;


    /**
     * 设备参数type
     */
    @ApiModelProperty(value = "设备参数type，可不传",notes = "设备参数type，可不传",required = false)
    private String type;



}
