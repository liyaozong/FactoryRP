package tech.yozo.factoryrp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description 企业实体
 */
@Table(name = "system_corporate")
@Entity
public class Corporate extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6860779798493665073L;


    /**
     * 企业名称
     */
    @Column(name = "corporate_name" , length = 20)
    private String corporateName;


    /**
     * 是否启用 1启用2不启用
     */
    @Column(name = "enable_status" ,length = 20)
    private Integer enableStatus;


    /**
     * 企业code
     */
    @Column(name = "corporate_code" ,length = 20)
    private String corporateCode;

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public String getCorporateCode() {
        return corporateCode;
    }

    public void setCorporateCode(String corporateCode) {
        this.corporateCode = corporateCode;
    }
}
