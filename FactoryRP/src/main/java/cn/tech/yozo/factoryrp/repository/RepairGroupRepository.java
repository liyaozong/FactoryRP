package cn.tech.yozo.factoryrp.repository;

import cn.tech.yozo.factoryrp.entity.RepairGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepairGroupRepository extends BaseRepository<RepairGroup,Long>{

    public List<RepairGroup> findByCorporateIdentify(Long corporateIdentify);
}
