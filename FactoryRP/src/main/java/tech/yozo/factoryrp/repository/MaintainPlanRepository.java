package tech.yozo.factoryrp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.MaintainPlan;

/**
 * @author chenxiang
 * @create 2018-03-02 下午2:23
 **/
@Repository
public interface MaintainPlanRepository extends BaseRepository<MaintainPlan,Long>{

    public Page<MaintainPlan> findByCorporateIdentify(Long corporateIdentify,Pageable pageable);

    public Page<MaintainPlan> findByCorporateIdentifyAndDeviceId(Long corporateIdentify,Long deviceId,Pageable pageable);
}
