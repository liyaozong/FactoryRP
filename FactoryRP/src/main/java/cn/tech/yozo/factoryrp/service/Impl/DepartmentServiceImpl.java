package cn.tech.yozo.factoryrp.service.Impl;

import cn.tech.yozo.factoryrp.entity.Department;
import cn.tech.yozo.factoryrp.repository.DepartmentRepository;
import cn.tech.yozo.factoryrp.service.DepartmentService;
import cn.tech.yozo.factoryrp.vo.req.SaveDepartmentReq;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService{

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> list(Long corporateIdentify) {
        return departmentRepository.findByCorporateIdentifyAndStatusFlag(corporateIdentify,1);
    }

    @Override
    public Department save(SaveDepartmentReq param,Integer opType) {
        Department d = new Department();
        d.setCorporateIdentify(param.getCorporateIdentify());
        d.setStatusFlag(1);
        d.setShowOrder(999);
        if (1==opType){
            //添加同级
            d.setParentId(param.getParentId());
        }
        if (2==opType){
            //添加下级
            d.setParentId(param.getId());
        }
        if (3==opType){
            //修改
            d= departmentRepository.getOne(param.getId());
        }
        d.setCode(param.getCode());
        d.setName(param.getName());
        return departmentRepository.save(d);
    }

    @Override
    public void delete(Long id) {
        List<Department> needDelete = departmentRepository.findByParentId(id);
        if (CollectionUtils.isNotEmpty(needDelete)){
            for (Department d : needDelete){
                departmentRepository.delete(d.getId());
            }
        }
        departmentRepository.delete(id);
    }

    @Override
    public Department updateUpLevel(Long id, Long parentId) {
       Department d= departmentRepository.getOne(id);
       d.setParentId(parentId);
       return departmentRepository.save(d);
    }
}
