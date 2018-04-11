package tech.yozo.factoryrp.repository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.TroubleRecordImageInfo;

import java.util.List;

/**
 * @author chenxiang
 * @create 2018-04-11 下午2:11
 **/
@Repository
public interface TroubleRecordImageInfoRepository extends BaseRepository<TroubleRecordImageInfo,Long>{

    public List<TroubleRecordImageInfo> findByTroubleRecordId(@Param("troubleRecordId") Long troubleRecordId);
}
