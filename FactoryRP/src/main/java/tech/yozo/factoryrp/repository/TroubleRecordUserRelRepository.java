package tech.yozo.factoryrp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.TroubleRecordUserRel;

import java.util.List;

/**
 * @author chenxiang
 * @create 2018-01-16 上午9:17
 **/
@Repository
public interface TroubleRecordUserRelRepository extends BaseRepository<TroubleRecordUserRel,Long>{

    public Page<TroubleRecordUserRel> findByDealUserIdAndDealStepStatusAndCorporateIdentifyAndDealStatus(Long dealUserId, Integer dealSetpStatus, Long corporateIdentify,Integer dealStatus, Pageable pageable);

    public List<TroubleRecordUserRel> findByTroubleRecordIdAndCorporateIdentify(Long troubleRecordId,Long corporateIdentify);

    public TroubleRecordUserRel findByTroubleRecordIdAndCorporateIdentifyAndDealStepStatusAndDealUserIdAndDealPhase(Long troubleRecordId,Long corporateIdentify, Integer dealSetpStatus,Long dealUserId,Integer dealPhase);

    public List<TroubleRecordUserRel> findByTroubleRecordIdAndCorporateIdentifyAndDealStep(Long troubleRecordId,Long corporateIdentify,Integer dealStep);

}
