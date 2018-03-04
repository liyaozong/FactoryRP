package tech.yozo.factoryrp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.service.MaintainPlanService;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.AddMaintainPlanReq;
import tech.yozo.factoryrp.vo.req.MaintainPlanListForAppReq;
import tech.yozo.factoryrp.vo.req.MaintainPlanListReq;
import tech.yozo.factoryrp.vo.resp.MaintainPlanDetailVo;
import tech.yozo.factoryrp.vo.resp.MaintainPlanListVo;
import tech.yozo.factoryrp.vo.resp.SimpleMaintainPlanVo;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenxiang
 * @create 2018-03-02 下午2:26
 **/
@RestController
@RequestMapping("maintainPlan")
@Api(description = "保养计划相关接口")
public class MaintainPlanController extends BaseController{
    @Autowired
    private MaintainPlanService maintainPlanService;
    @Autowired
    private UserAuthService userAuthService;

    @RequestMapping("add")
    @ApiOperation(value = "新增保养计划接口--WEB",notes = "新增保养计划接口接口--WEB",httpMethod = "POST")
    public ApiResponse addTroubleRecord(@RequestBody AddMaintainPlanReq param, HttpServletRequest request){
        Long corporateIdentify =userAuthService.getCurrentUserCorporateIdentify(request);
        AuthUser user = userAuthService.getCurrentUser(request);
        maintainPlanService.addMaintainPlan(param,corporateIdentify,user);
        return apiResponse();
    }

    @RequestMapping("list")
    @ApiOperation(value = "保养计划列表--WEB",notes = "保养计划列表--WEB",httpMethod = "POST")
    public ApiResponse<Pagination<MaintainPlanListVo>> list(@RequestBody MaintainPlanListReq param,HttpServletRequest request){
        Long corporateIdentify =userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(maintainPlanService.findByPage(param,corporateIdentify));
    }

    @RequestMapping("get")
    @ApiOperation(value = "根据主键查询保养计划接口--WEB",notes = "根据主键查询保养计划接口--WEB",httpMethod = "POST")
    public ApiResponse<MaintainPlanDetailVo> addTroubleRecord(Long id){
        return apiResponse(maintainPlanService.getDetailById(id));
    }

    @RequestMapping("simpleList")
    @ApiOperation(value = "app端保养计划分页查询列表--MOBILE",notes = "app端保养计划分页查询列表--MOBILE",httpMethod = "POST")
    public ApiResponse<Pagination<SimpleMaintainPlanVo>> ListForApp(@RequestBody MaintainPlanListForAppReq param, HttpServletRequest request){
        Long corporateIdentify =userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(maintainPlanService.findSimpleListByPage(param,corporateIdentify));
    }

    @RequestMapping("getCount")
    @ApiOperation(value = "首页展示保养计划数量--Mobile",notes = "首页展示保养计划数量--Mobile",httpMethod = "POST")
    public ApiResponse countWorkOrderList(HttpServletRequest request){
        Long corporateIdentify =userAuthService.getCurrentUserCorporateIdentify(request);
        AuthUser user = userAuthService.getCurrentUser(request);
        return apiResponse(maintainPlanService.getCount(corporateIdentify,user));
    }
}
