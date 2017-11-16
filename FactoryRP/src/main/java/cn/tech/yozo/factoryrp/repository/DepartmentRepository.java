package cn.tech.yozo.factoryrp.repository;

import cn.tech.yozo.factoryrp.entity.Department;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门信息数据处理
 */
@Repository
public interface DepartmentRepository extends BaseRepository<Department,Long>{

    /**
     * 根据企业标识和数据有效性查询部门列表
     * @param corporateIdentify
     * @param statusFlag
     * @return
     */
    public List<Department> findByCorporateIdentifyAndStatusFlag(Long corporateIdentify, Integer statusFlag);
}
