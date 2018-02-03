package tech.yozo.factoryrp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.yozo.factoryrp.entity.Department;
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
    List<Department> findByCorporateIdentifyAndStatusFlag(Long corporateIdentify, Integer statusFlag);

    /**
     * 根据上级部门ID查询
     * @param parentId
     * @return
     */
    List<Department> findByParentId(Long parentId);


    /**
     * 根据ID和企业标识进行IN查询
     * @param corporateIdentify
     * @param ids
     * @return
     */
    @Query("select d from Department d where d.corporateIdentify = :corporateIdentify and d.id in :ids")
    List<Department> findByCorporateIdentifyAndIdIn(@Param("corporateIdentify") Long corporateIdentify,@Param("ids") List<Long> ids);
}
