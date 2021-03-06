package tech.yozo.factoryrp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.SpotInspectionPlan;

import java.util.List;

/**
 * 巡检计划Repository
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/25
 * @description
 */
@Repository
public interface SpotInspectionPlanRepository extends BaseRepository<SpotInspectionPlan,Long> {


    /**
     * 根据企业唯一标识进行查找
     * @param corporateIdentify
     * @return
     */
    List<SpotInspectionPlan> findByCorporateIdentify(Long corporateIdentify);

    /**
     * 根据企业唯一标识和名称进行查询
     * @param name
     * @param corporateIdentify
     * @return
     */
    SpotInspectionPlan findByNameAndCorporateIdentify(String name,Long corporateIdentify);


    /**
     * 根据企业唯一标识和ID进行IN查询
     * @param corporateIdentify
     * @param ids
     * @return
     */
    @Query("select s from SpotInspectionPlan s where s.corporateIdentify = :corporateIdentify and s.id in :ids")
    List<SpotInspectionPlan> findByCorporateIdentifyAndIdIn(@Param("corporateIdentify") Long corporateIdentify,@Param("ids") List<Long> ids);

}
