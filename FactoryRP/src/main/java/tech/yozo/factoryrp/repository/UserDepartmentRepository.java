package tech.yozo.factoryrp.repository;

import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.UserDepartment;

/**
 * 用户，部门关联Repository
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/11
 * @description
 */
@Repository
public interface UserDepartmentRepository extends BaseRepository<UserDepartment,Long> {
}
