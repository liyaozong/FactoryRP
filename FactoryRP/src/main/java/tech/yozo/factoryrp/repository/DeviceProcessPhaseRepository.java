package tech.yozo.factoryrp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tech.yozo.factoryrp.entity.DeviceProcessPhase;
import tech.yozo.factoryrp.entity.DeviceProcessType;

import java.util.List;

/**
 * 设备流程阶段repository
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/22
 * @description
 */
@Repository
public interface DeviceProcessPhaseRepository extends BaseRepository<DeviceProcessPhase,Long> {


    /**
     * 根据code和企业唯一标识进行查询 此处只会返回一个值
     * @param code
     * @param corporateIdentify
     * @return
     */
    @Query(value = "select d from DeviceProcessPhase d where d.code =:code and d.corporateIdentify = :corporateIdentify")
    DeviceProcessPhase findByCodeAndCorporateIdentify(@Param("code") String code, @Param("corporateIdentify") Long corporateIdentify);

}
