package tech.yozo.factoryrp.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 此类是基础的实体类
 * MappedSuperclass 此标签会把父类的字段映射到子类上面
 */
public class BaseEntity implements Serializable{


    private static final long serialVersionUID = -279378218553793536L;


    private Long id;

    private Date createTime = new Date();


    private Date updateTime = new Date();

    private Long corporateIdentify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setCorporateIdentify(Long corporateIdentify){
        this.corporateIdentify = corporateIdentify;
    }

    public Long getCorporateIdentify(){
        return this.corporateIdentify;
    }
}
