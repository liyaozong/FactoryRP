package tech.yozo.factoryrp.entity;


import tech.yozo.factoryrp.utils.UUIDSequenceWorker;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * 用户实体
 */
@Table(name = "system_user")
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class User extends BaseEntity implements Serializable{

    private static final long serialVersionUID = -283755687649051591L;

    /**
     * 用户ID 工具类生成
     */
    @Column(name = "user_id")
    private Long userId = new UUIDSequenceWorker().uniqueSequenceId();

    /**
     * 登陆用户名
     */
    @Column(name = "user_name",length = 20)
    private String userName;

    /**
     * 密码
     */
    @Column(name = "password",length = 30)
    private String password;

    /**
     * 盐
     */
    @Column(name = "sault",length = 20)
    private String sault;


    /**
     * 企业code
     */
    @Column(name = "corporate_code",length = 20)
    private String corporateCode;

    @JoinTable(name="system_user_role",
      joinColumns={@JoinColumn(name="user_id", referencedColumnName="id")},
      inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName="id")})
    @ManyToMany(cascade = { CascadeType.PERSIST }, targetEntity = Role.class, fetch = FetchType.EAGER)
    private List<Role> roleList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSault() {
        return sault;
    }

    public void setSault(String sault) {
        this.sault = sault;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String getCorporateCode() {
        return corporateCode;
    }

    public void setCorporateCode(String corporateCode) {
        this.corporateCode = corporateCode;
    }
}
