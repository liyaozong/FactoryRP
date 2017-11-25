package cn.tech.yozo.factoryrp.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description 系统菜单实体
 */
@Table(name = "system_menu")
@Entity
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "entityCache")
public class Menu extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 9004310558317140193L;


    /**
     * 父级菜单Id
     * 若为0则为一级菜单
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 菜单访问URL
     */
    @Column(name = "url",length = 20)
    private String url;


    /**
     * 菜单名称
     */
    @Column(name = "name",length = 20)
    private String name;


    /**
     * 菜单排序号
     */
    @Column(name = "order_number")
    private Integer orderNumber;


    /**
     * 菜单备注
     */
    @Column(name = "remark",length = 50)
    private String remark;
}
