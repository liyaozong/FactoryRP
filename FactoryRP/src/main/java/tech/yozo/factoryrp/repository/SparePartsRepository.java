package tech.yozo.factoryrp.repository;

import tech.yozo.factoryrp.entity.SpareParts;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/28
 * @description 备件相关Repository
 */
public interface SparePartsRepository extends BaseRepository<SpareParts,Long> {


    /**
     * 根据备件名称进行查询
     * @param name
     * @return
     */
    SpareParts findByName(String name);


    /**
     * 根据备件名称和企业唯一标识进行查询
     * @param name
     * @param corporateIdentify
     * @return
     */
    SpareParts findByNameAndCorporateIdentify(String name,Long corporateIdentify);


}
