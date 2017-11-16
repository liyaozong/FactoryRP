package cn.tech.yozo.factoryrp.api;

import cn.tech.yozo.factoryrp.entity.Department;
import cn.tech.yozo.factoryrp.service.DepartmentService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门控制器
 */
@RestController
@RequestMapping("department")
@Api(description = "部门信息相关接口")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "查询部门列表",notes = "查询部门列表api",httpMethod = "GET")
    @GetMapping("list")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "Long",name = "corporateIdentify",
            value = "企业唯一标识",required = true,defaultValue = "111"))
    public List<Department> list(Long corporateIdentify){
        return departmentService.list(corporateIdentify);
    }
}
