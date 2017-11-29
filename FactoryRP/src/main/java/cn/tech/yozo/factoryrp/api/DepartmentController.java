package cn.tech.yozo.factoryrp.api;

import cn.tech.yozo.factoryrp.entity.Department;
import cn.tech.yozo.factoryrp.service.DepartmentService;
import cn.tech.yozo.factoryrp.vo.base.ApiResponse;
import cn.tech.yozo.factoryrp.vo.req.SaveDepartmentReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门控制器
 */
@RestController
@RequestMapping("api/department")
@Api(description = "部门信息相关接口")
public class DepartmentController extends BaseController{

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "查询部门列表",notes = "查询部门列表api",httpMethod = "GET")
    @GetMapping("list")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "Long",name = "corporateIdentify",
            value = "企业唯一标识",required = true,defaultValue = "111"))
    public ApiResponse<List<Department>> list(Long corporateIdentify){
        return apiResponse(departmentService.list(corporateIdentify));
    }


    @ApiOperation(value = "添加同级部门",notes = "添加同级部门",httpMethod = "POST")
    @RequestMapping("addSameDept")
    public ApiResponse<Department> addSameDept(@RequestBody SaveDepartmentReq param){
        return apiResponse(departmentService.save(param,1));
    }

    @ApiOperation(value = "添加下级部门",notes = "添加下级部门",httpMethod = "POST")
    @RequestMapping("addSubDept")
    public ApiResponse<Department> addSubDept(@RequestBody SaveDepartmentReq param){
        return apiResponse(departmentService.save(param,2));
    }

    @ApiOperation(value = "修改部门",notes = "修改部门",httpMethod = "POST")
    @RequestMapping("updateDept")
    public ApiResponse<Department> updateDept(@RequestBody SaveDepartmentReq param){
        return apiResponse(departmentService.save(param,3));
    }

    @ApiOperation(value = "删除部门",notes = "删除部门",httpMethod = "GET")
    @RequestMapping("deleteDept")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "Long",name = "id",
            value = "需要删除部门主键",required = true,defaultValue = "6"))
    public void deleteDept(Long id){
        departmentService.delete(id);
    }

    @ApiOperation(value = "调整上级部门",notes = "调整上级部门",httpMethod = "GET")
    @RequestMapping("updateUpLevel")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",dataType = "Long",name = "id",
            value = "需要调整都部门主键",required = true,defaultValue = "1"),@ApiImplicitParam(paramType = "query",dataType = "Long",name = "parentId",
            value = "上级部门ID",required = true,defaultValue = "2")})
    public void updateUpLevel(Long id,Long parentId){
        departmentService.updateUpLevel(id,parentId);
    }
}
