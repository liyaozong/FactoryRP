package tech.yozo.factoryrp.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.service.SpotInspectionRecordService;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.SpotInspectionRecordAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionRecordMobileAddReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionRecordAddResp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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

}

