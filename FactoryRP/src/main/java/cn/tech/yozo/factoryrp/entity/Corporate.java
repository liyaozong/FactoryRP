package cn.tech.yozo.factoryrp.entity;

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
     * 企业唯一标识
     */
    @Column(name = "corporate_identify")
    private Long corporateIdentify;


    /**
     * 企业唯一标识
     */
    @Column(name = "corporate_name")
    private Long corporateName;


    /**
     * 是否启用 1启用2不启用
     */
    @Column(name = "enable_status")
    private Integer enableStatus;


    public Long getCorporateIdentify() {
        return corporateIdentify;
    }

    public void setCorporateIdentify(Long corporateIdentify) {
        this.corporateIdentify = corporateIdentify;
    }

    public Long getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(Long corporateName) {
        this.corporateName = corporateName;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }
}
