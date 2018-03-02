package tech.yozo.factoryrp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.service.MaintainPlanService;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.AddMaintainPlanReq;
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
}
