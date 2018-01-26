package tech.yozo.factoryrp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.service.SpotInspectionPlanService;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.SpotInspectionPlanAddReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionPlanAddResp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 巡检计划前端控制器
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/25
 * @description
 */
@RestController
@RequestMapping(value = "api/spotInspectionPlan")
@Api(description = "巡检计划相关接口")
public class SpotInspectionPlanController extends BaseController{


    @Resource
    private SpotInspectionPlanService spotInspectionPlanService;

    @Resource
    private UserAuthService userAuthService;


    /**
     * 点检标准新增
     * @param spotInspectionPlanAddReq
     * @return
     */
    @ApiOperation(value = "新增点检计划",notes = "新增点检计划",httpMethod = "POST")
    @PostMapping("/addSpotInspectionPlan")
    @ApiImplicitParam(dataType = "SpotInspectionPlanAddReq" ,name = "spotInspectionPlanAddReq", paramType = "VO" ,
            value = "新增点检计划",required = true)
    public ApiResponse<SpotInspectionPlanAddResp> addSpotInspectionPlan(@Valid @RequestBody SpotInspectionPlanAddReq spotInspectionPlanAddReq, HttpServletRequest request) {
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(spotInspectionPlanService.addSpotInspectionPlan(spotInspectionPlanAddReq, corporateIdentify));

    }
}
