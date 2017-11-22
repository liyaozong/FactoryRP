package cn.tech.yozo.factoryrp.vo.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/20
 * @description
 */
@ApiModel
@Data
public class ApiCorporateIdentifyRequest extends ApiRequest implements Serializable{

        private static final long serialVersionUID = -4494715239670697718L;

        @ApiModelProperty(value = "企业唯一标识",required = true,notes = "企业唯一标识",example = "32132132132213")
        private Long corporateIdentify;



}
