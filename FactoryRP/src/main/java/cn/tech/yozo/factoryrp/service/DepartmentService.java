package cn.tech.yozo.factoryrp.service;

import cn.tech.yozo.factoryrp.entity.Department;

import java.util.List;

/**
 * 部门信息业务逻辑处理
 */
public interface DepartmentService {

    public List<Department> list(Long corporateIdentify);
}
