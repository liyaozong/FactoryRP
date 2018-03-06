package tech.yozo.factoryrp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.SpotInspectionStandard;

import java.util.List;

/**
 * 巡检标准Repository
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/22
 * @description
 */
@Repository
public interface SpotInspectionStandardRepository extends BaseRepository<SpotInspectionStandard,Long> {


    /**
     * 根据名称和企业唯一标识进行查询
     * @param name
     * @param corporateIdentify
     * @return
     */
    SpotInspectionStandard findByNameAndCorporateIdentify(String name,Long corporateIdentify);


    /**
     * 根据设备类型和企业唯一标识查询点检标准
     * @param deviceType
     * @param corporateIdentify
     * @return
     */
    @Query(value = "select s from SpotInspectionStandard s where s.deviceType = :deviceType and s.corporateIdentify = :corporateIdentify")
    List<SpotInspectionStandard> findByDeviceTypeAndCorporateIdentify(@Param("deviceType") Long deviceType,@Param("corporateIdentify") Long corporateIdentify);

    /**
     * 根据企业唯一标识和ID进行in查询
     * @param corporateIdentify
     * @param ids
     * @return
     */
    @Query(value = "select s from SpotInspectionStandard s where s.corporateIdentify = :corporateIdentify and s.id in :ids")
    List<SpotInspectionStandard> findByCorporateIdentifyAndIdIn(@Param("corporateIdentify") Long corporateIdentify,@Param("ids") List<Long> ids);


    /**
     * 根据企业唯一标识和ID进行查询
     * @param corporateIdentify
     * @param id
     * @return
     */
    @Query(value = "select s from SpotInspectionStandard s where s.corporateIdentify = :corporateIdentify and s.id = :id")
    SpotInspectionStandard findByCorporateIdentifyAndId(@Param("corporateIdentify") Long corporateIdentify,@Param("id") Long id);

}
