package cn.tech.yozo.factoryrp.entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description 系统菜实体
 */
@Table(name = "system_permission")
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class Permission extends BaseEntity implements Serializable {


    /**
     * 访问URL
     */
    @Column(name = "permission_url",length = 20)
    private String permissionUrl;


    /**
     * 访问的方法
     */
    @Column(name = "method",length = 20)
    private String method;

    /**
     * 企业标识
     */
    @Column(name = "corporate_identify")
    private Long corporateIdentify;

    /**
     * 权限备注
     */
    @Column(name = "remark",length = 50)
    private String remark;

    /**
     * 权限名称
     */
    @Column(name = "name",length = 20)
    private String name;

    /**
     * 权限类型
     * 1菜单权限 2其他权限
     */
    @Column(name = "perssion_type")
    private Integer perssionType;


    /**
     * 使用@ManyToMany映射双向关联关系。作为非映射主体一方，只需要简单的
     * 配置该注解的mappedBy="xxx"即可。xxx是对方实体（映射主体）中集合
     * 属性的名称。表示由对方主体的哪个属性来完成映射关系。
     */
    @ManyToMany(mappedBy="permissionList")
    private List<Role> roleList;

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Long getCorporateIdentify() {
        return corporateIdentify;
    }

    public void setCorporateIdentify(Long corporateIdentify) {
        this.corporateIdentify = corporateIdentify;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public Integer getPerssionType() {
        return perssionType;
    }

    public void setPerssionType(Integer perssionType) {
        this.perssionType = perssionType;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
