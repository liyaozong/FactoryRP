package tech.yozo.factoryrp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.service.MaintainPlanService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.*;
import tech.yozo.factoryrp.vo.resp.*;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
    @ApiOperation(value = "新增/修改保养计划接口--WEB",notes = "新增/修改保养计划接口接口--WEB",httpMethod = "POST")
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

    @PostMapping("listForDevice")
    @ApiOperation(value = "根据设备主键查询保养计划列表--WEB",notes = "根据设备主键查询保养计划列表--WEB",httpMethod = "POST")
    public ApiResponse<Pagination<MaintainPlanListVo>> getListByDeviceId(@RequestBody MaintainPlanListForDeviceReq param,HttpServletRequest request){
        Long corporateIdentify =userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(maintainPlanService.findListByDeviceId(param,corporateIdentify));
    }

    @RequestMapping("get")
    @ApiOperation(value = "根据主键查询保养计划接口--WEB",notes = "根据主键查询保养计划接口--WEB",httpMethod = "POST")
    public ApiResponse<AddMaintainPlanReq> getDetailById(Long id){
        return apiResponse(maintainPlanService.getDetailById(id));
    }

    @RequestMapping("simpleList")
    @ApiOperation(value = "app端保养计划分页查询列表--MOBILE",notes = "app端保养计划分页查询列表--MOBILE",httpMethod = "POST")
    public ApiResponse<Pagination<SimpleMaintainPlanVo>> ListForApp(@RequestBody MaintainPlanListForAppReq param, HttpServletRequest request){
        Long corporateIdentify =userAuthService.getCurrentUserCorporateIdentify(request);
        AuthUser user = userAuthService.getCurrentUser(request);
        return apiResponse(maintainPlanService.findSimpleListByPage(param,corporateIdentify,user));
    }

    @RequestMapping("getCount")
    @ApiOperation(value = "首页展示保养计划数量--Mobile",notes = "首页展示保养计划数量--Mobile",httpMethod = "POST")
    public ApiResponse countWorkOrderList(HttpServletRequest request){
        Long corporateIdentify =userAuthService.getCurrentUserCorporateIdentify(request);
        AuthUser user = userAuthService.getCurrentUser(request);
        return apiResponse(maintainPlanService.getCount(corporateIdentify,user));
    }

    @GetMapping("getDetail")
    @ApiOperation(value = "查询保养计划详情(包括保养计划信息、处理请客、工作量、更换的配件)--Mobile",notes = "查询保养计划详情(包括保养计划信息、处理请客、工作量、更换的配件)--Mobile",httpMethod = "GET")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "String",name = "id",
            value = "故障主键",required = true))
    public ApiResponse<MaintainPlanAppQueryVo> getDetail(HttpServletRequest request, Long id){
        AuthUser user = userAuthService.getCurrentUser(request);
        MaintainPlanAppQueryVo vo=maintainPlanService.getDetail(id,user);
        return apiResponse(vo);
    }

    @PostMapping("submit")
    @ApiOperation(value = "保养提交/执行计划--Mobile/Web",notes = "保养提交/执行计划--Mobile/Web",httpMethod = "POST")
    public ApiResponse submitMaintainPlan(HttpServletRequest request,@RequestBody MaintainDetailSubmitReq param){
        AuthUser user = userAuthService.getCurrentUser(request);
        maintainPlanService.appSubmit(param,user);
        return apiResponse();
    }

    /**
     * 批量删除故障信息
     * @param ids
     */
    @RequestMapping("batchDelete")
    @ApiOperation(value = "批量删除保养计划信息--WEB",notes = "批量删除保养计划信息--WEB",httpMethod = "GET")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "String",name = "ids",
            value = "需要删除的主键，多个主键用逗号分割",required = true,defaultValue = "1,2"))
    public ApiResponse deleteRelInfoByIds(String ids){
        List<Long> idsList = new ArrayList<>();
        if (!CheckParam.isNull(ids)){
            String[] idsArray =ids.split(",");
            if (idsArray.length>0){
                for(String id : idsArray){
                    idsList.add(Long.parseLong(id));
                }
            }
        }
        maintainPlanService.batchDelete(idsList);
        return apiResponse();
    }

    @RequestMapping("maintainRecordlist")
    @ApiOperation(value = "设备对应的保养记录--WEB",notes = "设备对应的保养记录--WEB",httpMethod = "POST")
    public ApiResponse<Pagination<SimpleMaintainRecordVo>> repairRecoreList(@RequestBody TroubleListReq param){
        return apiResponse(maintainPlanService.findSimpleRecordListByPage(param));
    }
}
