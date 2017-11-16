package cn.tech.yozo.factoryrp.service.Impl;

import cn.tech.yozo.factoryrp.entity.Department;
import cn.tech.yozo.factoryrp.repository.DepartmentRepository;
import cn.tech.yozo.factoryrp.service.DepartmentService;
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
}
