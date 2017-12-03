package cn.tech.yozo.factoryrp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


@Table(name = "contact_company_info")
@Entity
@Data
public class ContactCompany extends BaseEntity implements Serializable{
    @ApiModelProperty(value = "单位编码",notes ="单位编码" )
    private String code;

    @ApiModelProperty(value = "单位名称",notes ="单位名称" )
    private String name;

    @ApiModelProperty(value = "联系人",notes ="联系人" )
    @Column(name = "contact_name")
    private String contactName;

    @ApiModelProperty(value = "联系人电话",notes ="联系人电话" )
    @Column(name = "contact_phone")
    private String contactPhone;

    @ApiModelProperty(value = "传真",notes ="传真" )
    private String fax;

    @ApiModelProperty(value = "税号",notes ="税号" )
    @Column(name = "tax_number")
    private String taxNumber;

    @ApiModelProperty(value = "邮箱",notes ="邮箱" )
    private String email;

    @ApiModelProperty(value = "地址",notes ="地址" )
    private String address;

    @ApiModelProperty(value = "邮编",notes ="邮编" )
    @Column(name = "zip_code")
    private String zipCode;

    @ApiModelProperty(value = "开户行名称",notes ="开户行名称" )
    @Column(name = "bank_name")
    private String bankName;

    @ApiModelProperty(value = "银行卡号",notes ="银行卡号" )
    @Column(name = "bank_number")
    private String bankNumber;

    @ApiModelProperty(value = "单位网址",notes ="单位网址" )
    @Column(name = "web_site")
    private String webSite;

    @ApiModelProperty(value = "备注",notes ="备注" )
    private String remark;

    @ApiModelProperty(value = "是否有效",notes ="是否有效" )
    @Column(name="status_flag")
    private Integer statusFlag=1;
}
