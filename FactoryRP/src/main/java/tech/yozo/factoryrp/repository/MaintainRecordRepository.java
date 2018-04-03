package tech.yozo.factoryrp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.MaintainRecord;

/**
 * @author chenxiang
 * @create 2018-03-02 下午2:24
 **/
@Repository
public interface MaintainRecordRepository extends BaseRepository<MaintainRecord,Long>{

    @Query(value = "select mr.id,mr.maintain_no,mr.end_time,dp.name as dpn,rg.name,mp.plan_manager_name,mp.plan_remark,mr.maintain_content,mr.maintain_amount " +
            "from maintain_record mr " +
            "left join maintain_plan mp on mp.id = mr.maintain_plan_id " +
            "left join device_parameter_dictionary dp on dp.type=mp.`maintain_level` and dp.code='device_maintenance_level' " +
            "left join repair_group_info rg on rg.id = mp.repair_group_id " +
            "where mp.device_id =?1 \\\n#pageable\\\n",
            countQuery = "select count(1) from maintain_record mr " +
                    "left join maintain_plan mp on mp.id = mr.maintain_plan_id " +
                    "left join device_parameter_dictionary dp on dp.type=mp.`maintain_level` and dp.code='device_maintenance_level' " +
                    "left join repair_group_info rg on rg.id = mp.repair_group_id " +
                    "where mp.device_id =?1",
            nativeQuery = true)
    public Page<Object[]> getByPage(Long deveiceId, Pageable pageable);
}
