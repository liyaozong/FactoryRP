package tech.yozo.factoryrp.entity;

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
 * @description 用户，角色关联表
 */
@Table(name = "system_user_role")
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class UserRole extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1041852805642674581L;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Long roleId;

    /**
     * 企业标识
     */
    @Column(name = "corporate_identify")
    private Long corporateIdentify;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getCorporateIdentify() {
        return corporateIdentify;
    }

    public void setCorporateIdentify(Long corporateIdentify) {
        this.corporateIdentify = corporateIdentify;
    }
}
