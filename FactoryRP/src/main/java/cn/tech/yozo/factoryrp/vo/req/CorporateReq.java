package cn.tech.yozo.factoryrp.vo.req;

import cn.tech.yozo.factoryrp.vo.base.ApiRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description
 */
@ApiModel
public class CorporateReq extends ApiRequest{
    private static final long serialVersionUID = -2215380313157569727L;

    /**
     * 企业名称
     */
    @ApiModelProperty(value = "企业名称",required = true,notes = "企业名称",example = "阿里巴巴")
    private String corporateName;


    /**
     * 是否启用 1启用2不启用
     */
    @ApiModelProperty(value = "是否启用 1启用2不启用",required = true,notes = "是否启用 1启用2不启用",example = "1")
    private String enableStatus;

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(String enableStatus) {
        this.enableStatus = enableStatus;
    }
}
