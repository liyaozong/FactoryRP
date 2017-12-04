package tech.yozo.factoryrp.repository;

import tech.yozo.factoryrp.entity.ContactCompany;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 往来单位数据处理
 */
@Repository
public interface ContactCompanyRepository extends BaseRepository<ContactCompany,Long>{

    public List<ContactCompany> findByCorporateIdentifyAndStatusFlag(Long corporateIdentify,Integer statusFlag);
}
