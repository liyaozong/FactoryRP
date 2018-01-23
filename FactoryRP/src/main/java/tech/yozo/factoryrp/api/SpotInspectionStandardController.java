package tech.yozo.factoryrp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.service.SpotInspectionStandardService;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.SpotInspectionStandardAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionStandardQueryReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionStandardAddResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionStandardQueryResp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
     * 点检标准分页查询
     * @param spotInspectionStandardQueryReq
     * @param spotInspectionStandardQueryReq
     * @return
     */
    @ApiOperation(value = "点检标准分页查询-WEB",notes = "点检标准分页查询-WEB",httpMethod = "POST")
    @PostMapping("/findByPage")
    @ApiImplicitParam(dataType = "SpotInspectionStandardQueryReq" ,name = "spotInspectionStandardQueryReq", paramType = "VO" ,
            value = "点检标准分页查询-WEB",required = true)
    public ApiResponse<List<SpotInspectionStandardQueryResp>> findByPage(@RequestBody SpotInspectionStandardQueryReq spotInspectionStandardQueryReq,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(spotInspectionStandardService.findByPage(spotInspectionStandardQueryReq,corporateIdentify));
    }

    /**
     * 删除巡检标准
     * @param standardId
     */
    @ApiOperation(value = "删除巡检标准",notes = "删除巡检标准",httpMethod = "GET")
    @GetMapping("/deleteInspectionStandard")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Long" ,name = "standardId", paramType = "query" ,
                    value = "巡检ID",required = true,defaultValue = "1"),
    })
    public ApiResponse deleteInspectionStandard(Long standardId,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        spotInspectionStandardService.deleteInspectionStandard(standardId,corporateIdentify);
        return apiResponse();
    }

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
