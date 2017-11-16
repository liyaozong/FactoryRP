package cn.tech.yozo.factoryrp.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description 权限角色关联实体
 */
@Table(name = "system_permission_role")
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class PermissionRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -299645908618274956L;

    /**
     * 企业标识
     */
    @Column(name = "corporate_identify")
    private Long corporateIdentify;


    /**
     * 角色标识
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 权限ID
     */
    @Column(name = "permission_id")
    private Long permissionId;


    /**
     * 备注描述
     */
    @Column(name = "remark")
    private String remark;


    public Long getCorporateIdentify() {
        return corporateIdentify;
    }

    public void setCorporateIdentify(Long corporateIdentify) {
        this.corporateIdentify = corporateIdentify;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
