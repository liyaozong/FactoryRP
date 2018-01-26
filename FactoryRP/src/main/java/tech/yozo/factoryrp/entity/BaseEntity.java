package tech.yozo.factoryrp.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 此类是基础的实体类
 * MappedSuperclass 此标签会把父类的字段映射到子类上面
 */
@MappedSuperclass
public class BaseEntity implements Serializable{


    private static final long serialVersionUID = -279378218553793536L;


    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    @ApiModelProperty(value = "主键",notes ="主键" )
    private Long id;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间",notes ="创建时间" )
    private Date createTime = new Date();


    @JSONField (format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "update_time")
    @ApiModelProperty(value = "修改时间",notes ="修改时间" )
    private Date updateTime = new Date();

    @Column(name="corporate_identify")
            @ApiModelProperty(value = "企业唯一标识",notes ="企业唯一标识" )
    private Long corporateIdentify;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
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
