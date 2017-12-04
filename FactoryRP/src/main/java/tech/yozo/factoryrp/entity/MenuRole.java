package tech.yozo.factoryrp.entity;

import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/21
 * @description
 */
@Table(name = "system_menu_role")
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
@Data
public class MenuRole extends BaseEntity implements Serializable {


    /**
     * 角色标识
     */
    @Column(name = "role_id", length = 20)
    private Long roleId;

    /**
     * 权限ID
     */
    @Column(name = "menu_id", length = 20)
    private Long menuId;


    /**
     * 备注描述
     */
    @Column(name = "remark",length = 50)
    private String remark;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
