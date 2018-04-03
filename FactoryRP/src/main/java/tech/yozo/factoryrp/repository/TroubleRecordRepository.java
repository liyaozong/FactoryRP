package tech.yozo.factoryrp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import tech.yozo.factoryrp.entity.TroubleRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public Page<TroubleRecord> findByIdIn(List<Long> ids, Pageable pageable);

    @Query(value = "select r.id,t.order_no,t.create_user,t.create_time,r.end_time,dp.name as dpn,rg.name,t.repair_user_name," +
            "t.remark,r.work_remark,r.repair_amount " +
            "from repair_record r left join trouble_record t on r.trouble_record_id = t.id " +
            "left join repair_group_info rg on rg.id = t.repair_group_id " +
            "left join device_parameter_dictionary dp on r.repair_level = dp.type and dp.code = 'device_repair_level' " +
            "where t.device_id = ?1 \\\n#pageable\\\n",nativeQuery = true,
            countQuery = "select count(1) from repair_record r left join trouble_record t on r.trouble_record_id = t.id " +
                    "left join repair_group_info rg on rg.id = t.repair_group_id " +
                    "left join device_parameter_dictionary dp on r.repair_level = dp.type and dp.code = 'device_repair_level' " +
                    "where t.device_id = ?1")
    public Page<Object[]> getByPage(Long deveiceId, Pageable pageable);
}
