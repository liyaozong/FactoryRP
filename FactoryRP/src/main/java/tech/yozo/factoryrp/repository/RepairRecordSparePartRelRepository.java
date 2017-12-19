package tech.yozo.factoryrp.repository;

import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.RepairRecordSparePartRel;

import java.util.List;

/**
 * @author chenxiang
 * @create 2017-12-19 下午3:14
 **/
@Repository
public interface RepairRecordSparePartRelRepository extends BaseRepository<RepairRecordSparePartRel,Long>{

    public List<RepairRecordSparePartRel> findByRepairRecordId(Long repairRecordId);
}
