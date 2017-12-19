package tech.yozo.factoryrp.repository;

import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.WorkTime;

import java.util.List;

/**
 * @author chenxiang
 * @create 2017-12-19 下午3:13
 **/
@Repository
public interface WorkTimeRepository extends BaseRepository<WorkTime,Long>{

    /**
     * 根据维修单主键查询
     * @param repairRecordId
     * @return
     */
    public List<WorkTime> findByRepairRecordId(Long repairRecordId);
}
