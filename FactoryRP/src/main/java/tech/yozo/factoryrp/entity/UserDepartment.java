package tech.yozo.factoryrp.entity;

import lombok.Data;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 用户-部门关联表
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/11
 * @description
 */
@Table(name = "system_user_department")
@Entity
@Data
public class UserDepartment extends BaseEntity implements Serializable {

    /**
     * 用户id
     */
    @Column(name = "user_id",length = 20)
    private Long userId;

    /**
     * 部门id
     */
    @Column(name = "department_id",length = 20)
    private Long departmentId;

}
