package tech.yozo.factoryrp.vo.resp.corporate;

import tech.yozo.factoryrp.vo.base.ApiCorporateIdentifyRsponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description
 */
@ApiModel
public class CorporateResp extends ApiCorporateIdentifyRsponse implements Serializable {


    private static final long serialVersionUID = -135267418493214488L;


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


    /**
     * 企业唯一标识
     */
    @ApiModelProperty(value = "企业唯一标识",required = true,notes = "企业唯一标识",example = "323213213213213")
    private String corporateIdentify;

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

    @Override
    public String getCorporateIdentify() {
        return corporateIdentify;
    }

    @Override
    public void setCorporateIdentify(String corporateIdentify) {
        this.corporateIdentify = corporateIdentify;
    }
}
