package cn.tech.yozo.factoryrp.repository;

import cn.tech.yozo.factoryrp.entity.Corporate;
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


}
