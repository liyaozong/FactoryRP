package tech.yozo.factoryrp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import tech.yozo.factoryrp.entity.TroubleRecord;
import org.springframework.stereotype.Repository;

/**
 * @author chenxiang
 * @create 2017-12-03 下午3:59x
 **/
@Repository
public interface TroubleRecordRepository extends BaseRepository<TroubleRecord,Long>{

    /**
     * 按照故障状态分页查询
     * @param status
     * @param pageable
     * @return
     */
    public Page<TroubleRecord> findByStatusAndCorporateIdentify(Integer status,Long corporateIdentify,Pageable pageable);

    public Page<TroubleRecord> findByStatusAndRepairUserId(Integer status,Long repairUserId, Pageable pageable);
}
