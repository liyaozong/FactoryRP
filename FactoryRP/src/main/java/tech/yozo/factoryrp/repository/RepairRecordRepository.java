package tech.yozo.factoryrp.repository;

import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.RepairRecord;

import java.util.Map;

/**
 * @author chenxiang
 * @create 2017-12-15 下午11:10
 **/
@Repository
public interface RepairRecordRepository extends BaseRepository<RepairRecord,Long>{

    public RepairRecord findByTroubleRecordId(Long troubleRecordId);
}
