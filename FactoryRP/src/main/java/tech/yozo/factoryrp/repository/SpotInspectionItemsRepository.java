package tech.yozo.factoryrp.repository;

import com.sun.tools.doclets.formats.html.resources.standard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.yozo.factoryrp.entity.SpotInspectionItems;

import java.util.List;

/**
 * 巡检项目Repository
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/23
 * @description
 */
public interface SpotInspectionItemsRepository extends BaseRepository<SpotInspectionItems,Long> {




    /**
     * 根据巡检标准ID和企业唯一标识进行查询
     * @param standard
     * @param corporateIdentify
     * @return
     */
    List<SpotInspectionItems> findByStandardAndCorporateIdentify(Long standard ,Long corporateIdentify);


    /**
     * 根据企业唯一标识和点检标准集合进行IN查询
     * @param corporateIdentify
     * @param standardIds
     * @return
     */
    @Query(value = "select s from SpotInspectionItems s where s.corporateIdentify = :corporateIdentify and s.standard in :standardIds")
    List<SpotInspectionItems> findByCorporateIdentifyAndStandardIn(@Param("corporateIdentify") Long corporateIdentify,@Param("standardIds")List<Long> standardIds);

    /**
     * 根据巡检标准ID和企业唯一标识进行in查询
     * @param standardIds
     * @param corporateIdentify
     * @return
     */
    @Query(value = "select s from SpotInspectionItems s where s.standard in :standardIds and s.corporateIdentify = :corporateIdentify")
    List<SpotInspectionItems> findByStandardIdsInAndCorporateIdentify(@Param("standardIds") List<Long> standardIds ,@Param("corporateIdentify") Long corporateIdentify);
}
