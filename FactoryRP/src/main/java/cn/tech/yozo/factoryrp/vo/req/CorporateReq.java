package cn.tech.yozo.factoryrp.vo.req;

import cn.tech.yozo.factoryrp.vo.base.BaseRequest;
import cn.tech.yozo.factoryrp.vo.validation.IsNumberStr;
import cn.tech.yozo.factoryrp.vo.validation.NotEmpty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description
 */
@ApiModel
@Data
public class CorporateReq extends BaseRequest {
    private static final long serialVersionUID = -2215380313157569727L;

    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称",required = true,notes = "企业名称",example = "阿里巴巴")
    @NotEmpty(message = "企业名称不能为空")
    private String corporateName;


    /**
     * 是否启用 1启用2不启用
     */
    @ApiModelProperty(value = "是否启用 1启用2不启用",required = true,notes = "是否启用 1启用2不启用",example = "1")
    @IsNumberStr(message = "启用标志格式不正确")
    private String enableStatus;
}
