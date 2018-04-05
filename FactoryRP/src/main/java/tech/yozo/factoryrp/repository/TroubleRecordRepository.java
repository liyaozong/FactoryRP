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

    @Query(value = "select (select count(1) from device_info) as countDevice,(select count(1) from (select id from trouble_record group by device_id) a) as troubleCount,(select count(1) from (select id,status from trouble_record group by device_id having status=2) b) as repairCount",nativeQuery = true)
    public Object[] getTroubleCount();

    @Query(value = "select distinct di.name,tr.status from trouble_record tr left join device_info di on di.id = tr.device_id where tr.status <> 4 and tr.status <> 5 limit 20;", nativeQuery = true)
    public List<Object[]> getCountList();
}
