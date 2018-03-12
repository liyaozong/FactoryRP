package tech.yozo.factoryrp.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.service.SpotInspectionRecordService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.SpotInspectionRecordAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionRecordMobileAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionRecordPageQueryReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordAddResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordDetailWarpResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordPageQueryResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordResp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 巡检记录相关接口
 */
@RestController
@RequestMapping(value = "api/spotInspectionRecord")
@Api(description = "巡检记录相关接口")
public class SpotInspectionRecordController extends BaseController {

    @Resource
    private SpotInspectionRecordService spotInspectionRecordService;

    @Resource
    private UserAuthService userAuthService;

    /**
     * 新增巡检记录
     * @param request
     * @return
     */
    @ApiOperation(value = "新增巡检记录",notes = "新增巡检记录",httpMethod = "POST")
    @PostMapping("/addSpotInspectionRecord")
    @ApiImplicitParam(dataType = "SpotInspectionRecordAddReq" ,name = "spotInspectionRecordAddReq", paramType = "VO" ,
            value = "新增巡检记录",required = true)
    public ApiResponse<SpotInspectionRecordAddResp> addSpotInspectionRecord(@Valid @RequestBody SpotInspectionRecordAddReq spotInspectionRecordAddReq, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(spotInspectionRecordService.addSpotInspectionRecord(spotInspectionRecordAddReq, corporateIdentify));
    }


    /**
     * 手机端提交巡检记录
     * @param spotInspectionRecordMobileAddReq
     * @return
     */
    @ApiOperation(value = "手机端提交巡检记录",notes = "手机端提交巡检记录",httpMethod = "POST")
    @PostMapping("/spotInspectionItemsRecordMobileAdd")
    @ApiImplicitParam(dataType = "SpotInspectionRecordMobileAddReq" ,name = "spotInspectionRecordMobileAddReq", paramType = "VO" ,
            value = "新增巡检记录",required = true)
    public ApiResponse<SpotInspectionRecordAddResp> spotInspectionItemsRecordMobileAdd(@Valid @RequestBody SpotInspectionRecordMobileAddReq spotInspectionRecordMobileAddReq, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        Long userId = userAuthService.getCurrentUserId(request);
        return apiResponse(spotInspectionRecordService.spotInspectionItemsRecordMobileAdd(spotInspectionRecordMobileAddReq, corporateIdentify,userId));
    }


    /**
     * 根据巡检ID查询巡检记录
     * @param planId
     * @return
     */
    @ApiOperation(value = "根据巡检ID查询巡检记录-WEB",notes = "根据巡检ID查询巡检记录-WEB",httpMethod = "GET")
    @GetMapping("/querySpotInspectionRecordByPlanId")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Long" ,name = "planId", paramType = "query" ,
                    value = "巡检ID",required = true,defaultValue = "3")
    })
    public ApiResponse<List<SpotInspectionRecordResp>> querySpotInspectionRecordByPlanId(@RequestParam("planId") Long planId, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(spotInspectionRecordService.querySpotInspectionRecordByPlanId(planId, corporateIdentify));
    }

    /**
     * 根据点检记录ID查询点检详情
     * @param recordId
     * @param planId
     * @return
     */
    @ApiOperation(value = "根据巡检ID查询巡检记录-WEB",notes = "根据巡检ID查询巡检记录-WEB",httpMethod = "GET")
    @GetMapping("/querySpotInspectionRecordDetailByRecordId")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Long" ,name = "recordId", paramType = "query" ,
                    value = "巡检记录ID",required = true,defaultValue = "13"),
            @ApiImplicitParam(dataType = "Long" ,name = "planId", paramType = "query" ,
                    value = "巡检ID",required = true,defaultValue = "8")
    })
    public ApiResponse<SpotInspectionRecordDetailWarpResp> querySpotInspectionRecordDetailByRecordId(@RequestParam("recordId")Long recordId,@RequestParam("planId") Long planId,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(spotInspectionRecordService.querySpotInspectionRecordDetailByRecordId(recordId,planId, corporateIdentify));
    }

    /**
     * 巡检记录分页查询
     * @param spotInspectionRecordPageQueryReq
     * @return
     */
    @ApiOperation(value = "巡检记录分页查询",notes = "巡检记录分页查询",httpMethod = "POST")
    @PostMapping("/findByPage")
    @ApiImplicitParam(dataType = "SpotInspectionRecordPageQueryReq" ,name = "spotInspectionRecordPageQueryReq", paramType = "VO" ,
            value = "巡检记录分页查询",required = true)
    public ApiResponse<Pagination<SpotInspectionRecordPageQueryResp>> findByPage(@RequestBody SpotInspectionRecordPageQueryReq spotInspectionRecordPageQueryReq,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(spotInspectionRecordService.findByPage(spotInspectionRecordPageQueryReq,corporateIdentify));
    }

}

