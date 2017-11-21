package cn.tech.yozo.factoryrp.service.Impl;

import cn.tech.yozo.factoryrp.entity.RepairGroup;
import cn.tech.yozo.factoryrp.repository.RepairGroupRepository;
import cn.tech.yozo.factoryrp.service.RepairGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepairGroupServiceImpl implements RepairGroupService{
    @Autowired
    RepairGroupRepository repairGroupRepository;

    @Override
    public List<RepairGroup> listAll() {
        return repairGroupRepository.findAll();
    }

    @Override
    public RepairGroup add(String code, String name,Long corporateIdentify) {
        RepairGroup re = new RepairGroup();
        re.setCode(code);
        re.setName(name);
        re.setCorporateIdentify(corporateIdentify);
        return repairGroupRepository.save(re);
    }

    @Override
    public void deleteRgs(List<Long> ids) {
        if (null!=ids && ids.size()>0){
            for (Long id : ids){
                repairGroupRepository.delete(id);
            }
        }
    }
}
