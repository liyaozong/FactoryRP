package tech.yozo.factoryrp.repository;

import tech.yozo.factoryrp.entity.Corporate;
import org.springframework.stereotype.Repository;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description 企业Repository
 */
@Repository
public interface CorporateRepository extends BaseRepository<Corporate,Long>{


    /**
     * 根据企业名称进行查询
     * @param corporateName
     * @return
     */
    Corporate findByCorporateName(String corporateName);


    /**
     * 根据企业唯一标识进行查询
     * @param corporateIdentify
     * @return
     */
    Corporate findByCorporateIdentify(Long corporateIdentify);


    /**
     * 根据企业标识和企业名称进行查找
     * @param corporateName
     * @param corporateIdentify
     * @return
     */
    Corporate findByCorporateNameAndCorporateIdentify(String corporateName,Long corporateIdentify);

}
