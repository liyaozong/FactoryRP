package tech.yozo.factoryrp.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description 系统角色实体
 */
@Table(name = "system_role")
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class Role extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -3859342233133204256L;


    /**
     * 角色code
     */
    @Column(name = "role_code",length = 20)
    private String roleCode;

    /**
     * 角色名称
     */
    @Column(name = "role_name",length = 20)
    private String roleName;

    /**
     * 角色描述
     */
    @Column(name = "role_description",length = 50)
    private String roleDescription;

    /**
     * 是否启用 1启用2不启用
     */
    @Column(name = "enable_status")
    private Integer enableStatus;


    /**
     * 使用@ManyToMany映射双向关联关系。作为非映射主体一方，只需要简单的
     * 配置该注解的mappedBy="xxx"即可。xxx是对方实体（映射主体）中集合
     * 属性的名称。表示由对方主体的哪个属性来完成映射关系。
     */
    @ManyToMany(mappedBy="roleList")
    private List<User> userList;

    /**
     * 系统角色菜单关联
     */
    @JoinTable(name="system_menu_role",
            joinColumns={@JoinColumn(name="role_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="menu_id", referencedColumnName="id")})
    @ManyToMany(cascade = { CascadeType.PERSIST }, targetEntity = Menu.class, fetch = FetchType.EAGER)
    private List<Menu> menuList;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Integer getEnableStatus() {
        return enableStatus;
    }

    public void setEnableStatus(Integer enableStatus) {
        this.enableStatus = enableStatus;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

/* public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }*/
}
