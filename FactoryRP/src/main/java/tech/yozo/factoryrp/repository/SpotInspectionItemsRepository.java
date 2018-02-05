package tech.yozo.factoryrp.repository;

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
     * @param Standard
     * @param corporateIdentify
     * @return
     */
    List<SpotInspectionItems> findByStandardAndCorporateIdentify(Long Standard ,Long corporateIdentify);


    /**
     * 根据巡检标准ID和企业唯一标识进行in查询
     * @param standardIds
     * @param corporateIdentify
     * @return
     */
    List<SpotInspectionItems> findByStandardIdsInAndCorporateIdentify(List<Long> standardIds ,Long corporateIdentify);
}
