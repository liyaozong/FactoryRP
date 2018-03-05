package tech.yozo.factoryrp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.service.SpotInspectionPlanService;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.SpotInspectionPlanAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionPlanQueryReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionPlanAddResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionPlanDetailWarpResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionPlanQueryWarpResp;
import tech.yozo.factoryrp.vo.resp.inspection.mobile.SpotInspectionPlanDeviceQueryResp;
import tech.yozo.factoryrp.vo.resp.inspection.mobile.SpotInspectionPlanResp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
     * 手机端查询点检任务关联设备
     * @param planId
     * @return
     */
    @ApiOperation(value = "手机端查询点检任务关联设备-Mobile",notes = "手机端查询点检任务关联设备-Mobile",httpMethod = "GET")
    @RequestMapping("/querySpotInspectionPlanDevices")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Long" ,name = "planId", paramType = "query" ,
                    value = "点检计划ID",required = true,defaultValue = "3")
    })
    public ApiResponse<List<SpotInspectionPlanDeviceQueryResp>> querySpotInspectionPlanDevices(HttpServletRequest request,
                                                                                  @RequestParam(value="planId",required = true,defaultValue = "1") Long planId){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(spotInspectionPlanService.querySpotInspectionPlanDevices(planId,corporateIdentify));

    }


    /**
     * 手机端查询点检任务
     * @return
     */
    @ApiOperation(value = "手机端查询点检任务-Mobile",notes = "手机端查询点检任务-Mobile",httpMethod = "GET")
    @RequestMapping("/queryMobilePlan")
    public ApiResponse<List<SpotInspectionPlanResp>> queryMobilePlan(HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        Long userId = userAuthService.getCurrentUserId(request);

        return apiResponse(spotInspectionPlanService.queryMobilePlan(userId,corporateIdentify));
    }

    /**
     * 点检标准新增
     * @param spotInspectionPlanAddReq
     * @return
     */
    @ApiOperation(value = "新增点检计划-WEB",notes = "新增点检计划-WEB",httpMethod = "POST")
    @PostMapping("/addSpotInspectionPlan")
    @ApiImplicitParam(dataType = "SpotInspectionPlanAddReq" ,name = "spotInspectionPlanAddReq", paramType = "VO" ,
            value = "新增点检计划",required = true)
    public ApiResponse<SpotInspectionPlanAddResp> addSpotInspectionPlan(@Valid @RequestBody SpotInspectionPlanAddReq spotInspectionPlanAddReq, HttpServletRequest request) {
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(spotInspectionPlanService.addSpotInspectionPlan(spotInspectionPlanAddReq, corporateIdentify));
    }

    /**
     * 根据部门ID查询点检计划
     * @param spotInspectionPlanQueryReq
     * @return
     */
    @ApiOperation(value = "根据部门ID查询点检计划-WEB",notes = "根据部门ID查询点检计划-WEB",httpMethod = "POST")
    @PostMapping("/findByPage")
    @ApiImplicitParam(dataType = "SpotInspectionPlanQueryReq" ,name = "spotInspectionPlanQueryReq", paramType = "VO" ,
            value = "根据部门ID查询点检计划-WEB",required = true)
    public ApiResponse<SpotInspectionPlanQueryWarpResp> findByPage(@Valid @RequestBody SpotInspectionPlanQueryReq spotInspectionPlanQueryReq, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(spotInspectionPlanService.findByPage(spotInspectionPlanQueryReq, corporateIdentify));
    }


    /**
     * 根据点检计划ID查询点检计划详情-WEB
     * @param planId
     * @param request
     * @return
     */
    @ApiOperation(value = "根据点检计划ID查询点检计划详情-WEB",notes = "根据点检计划ID查询点检计划详情-WEB",httpMethod = "GET")
    @GetMapping("/QuerySpotInspectionPlanDetailByPlanId")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Long" ,name = "planId", paramType = "query" ,
                    value = "巡检ID",required = true,defaultValue = "3")
    })
    public ApiResponse<SpotInspectionPlanDetailWarpResp> querySpotInspectionPlanDetailByPlanId(@RequestParam("planId") Long planId, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(spotInspectionPlanService.querySpotInspectionPlanDetailByPlanId(planId, corporateIdentify));
    }


    /**
     * 删除巡检计划
     * @param planId
     */
    @ApiOperation(value = "删除巡检计划-WEB",notes = "删除巡检计划-WEB",httpMethod = "POST")
    @GetMapping("/deleteSpotInspectionPlanDetailByPlanId")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Long" ,name = "planId", paramType = "query" ,
                    value = "巡检ID",required = true,defaultValue = "3")
    })
    public ApiResponse deleteSpotInspectionPlanDetailByPlanId(Long planId,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        spotInspectionPlanService.deleteSpotInspectionPlanDetailByPlanId(planId,corporateIdentify);
        return apiResponse();
    }

}
