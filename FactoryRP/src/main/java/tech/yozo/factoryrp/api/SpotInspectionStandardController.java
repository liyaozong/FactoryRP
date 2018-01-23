package tech.yozo.factoryrp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.service.SpotInspectionStandardService;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.SpotInspectionStandardAddReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionStandardAddResp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 点巡检相关接口
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/23
 * @description
 */
@RestController
@RequestMapping(value = "api/spotInspectionStandard")
@Api(description = "点巡检相关接口")
public class SpotInspectionStandardController extends BaseController{

    @Resource
    private SpotInspectionStandardService spotInspectionStandardService;

    @Resource
    private UserAuthService userAuthService;


    /**
     * 点检标准新增
     * @param spotInspectionStandardAddReq
     * @return
     */
    @ApiOperation(value = "点检标准新增",notes = "点检标准新增",httpMethod = "POST")
    @PostMapping("/addSpotInspectionStandard")
    @ApiImplicitParam(dataType = "SpotInspectionStandardAddReq" ,name = "addSpotInspectionStandard", paramType = "VO" ,
            value = "点检标准新增",required = true)
    public ApiResponse<SpotInspectionStandardAddResp> addSpotInspectionStandard(@Valid @RequestBody SpotInspectionStandardAddReq spotInspectionStandardAddReq, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(spotInspectionStandardService.addSpotInspectionStandard(spotInspectionStandardAddReq,corporateIdentify));
    }

}
