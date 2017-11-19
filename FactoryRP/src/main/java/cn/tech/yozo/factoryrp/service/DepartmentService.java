package cn.tech.yozo.factoryrp.service;

import cn.tech.yozo.factoryrp.entity.Department;
import cn.tech.yozo.factoryrp.vo.req.SaveDepartmentReq;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 部门信息业务逻辑处理
 */
public interface DepartmentService {

    /**
     * 根据企业标识查询部门列表
     * @param corporateIdentify
     * @return
     */
    public List<Department> list(Long corporateIdentify);

    /**
     * 保存部门信息
     * @param param
     * @param opType 保存类型，1:添加同级：2:添加下级；3：修改
     * @return
     */
    public Department save(SaveDepartmentReq param,Integer opType);

    /**
     * 删除部门级子部门
     * @param id
     * @return
     */
    public void delete(Long id);
}
