package cn.tech.yozo.factoryrp.api;

import cn.tech.yozo.factoryrp.config.auth.UserAuthService;
import cn.tech.yozo.factoryrp.entity.ContactCompany;
import cn.tech.yozo.factoryrp.page.Pagination;
import cn.tech.yozo.factoryrp.service.ContactCompanyService;
import cn.tech.yozo.factoryrp.vo.base.ApiResponse;
import cn.tech.yozo.factoryrp.vo.req.ContactCompanyReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("api/contactCompany")
@Api(description = "往来单位相关接口")
public class ContactCompanyController extends BaseController{
    @Autowired
    private ContactCompanyService contactCompanyService;
    @Autowired
    private UserAuthService userAuthService;

    @ApiOperation(value = "分页查询往来单位列表",notes = "分页查询往来单位列表",httpMethod = "POST")
    @RequestMapping("list")
    public ApiResponse<Pagination<ContactCompany>> list(@RequestBody ContactCompanyReq param, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(contactCompanyService.findByPage(param,corporateIdentify));
    }

    @ApiOperation(value = "根据ID查询往来单位列表",notes = "根据ID查询往来单位列表",httpMethod = "GET")
    @RequestMapping("get")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "Long",name = "id",
            value = "主键",required = true,defaultValue = "1"))
    public ApiResponse<ContactCompany> getById(Long id){
        return apiResponse(contactCompanyService.getById(id));
    }

    @ApiOperation(value = "新增或修改往来单位",notes = "新增或修改往来单位",httpMethod = "POST")
    @RequestMapping("save")
    public ApiResponse<ContactCompany> updateContactCompany(@RequestBody ContactCompany param, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        param.setCorporateIdentify(corporateIdentify);
        return apiResponse(contactCompanyService.save(param));
    }

    @ApiOperation(value = "删除往来单位",notes = "删除往来单位",httpMethod = "GET")
    @RequestMapping("deleteContactCompany")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "Long",name = "id",
            value = "需要删除往来单位主键",required = true,defaultValue = "1"))
    public void deleteContactCompany(Long id){
        contactCompanyService.delete(id);
    }
}
