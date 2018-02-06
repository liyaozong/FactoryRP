package tech.yozo.factoryrp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.service.SpotInspectionStandardService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.SpotInspectionStandardAddReq;
import tech.yozo.factoryrp.vo.req.SpotInspectionStandardQueryReq;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionStandardAddResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionStandardDetailQueryResp;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionStandardQueryResp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
     * 查询点巡检标准详情
     * @param standardId
     * @return
     */
    @ApiOperation(value = "查询点巡检标准详情-WEB",notes = "查询点巡检标准详情-WEB",httpMethod = "GET")
    @GetMapping("/queryInspectionStandardDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Long" ,name = "standardId", paramType = "query" ,
                    value = "巡检ID",required = true,defaultValue = "1"),
    })
    public ApiResponse<SpotInspectionStandardDetailQueryResp> queryInspectionStandardDetail(@RequestParam("standardId") Long standardId,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(spotInspectionStandardService.queryInspectionStandardDetail(standardId,corporateIdentify));
    }

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

    /**
     * 批量删除点检标准
     * @param ids
     */
    @RequestMapping("/deleteSpotInspectionStandardByIds")
    @ApiOperation(value = "批量删除点检标准--WEB",notes = "批量删除点检标准--WEB",httpMethod = "GET")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "String",name = "ids",
            value = "需要删除的主键，多个主键用逗号分割",required = true,defaultValue = "1,2"))
    public ApiResponse deleteSpotInspectionStandardByIds(String ids,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        List<Long> idList = new ArrayList<>();
        if (!CheckParam.isNull(ids)){
            String[] idArray =ids.split(",");
            if (idArray.length>0){
                Stream.of(idArray).forEach(s1 -> {
                    idList.add(Long.parseLong(s1));
                });
            }
        }

        spotInspectionStandardService.deleteSpotInspectionStandardByIds(idList,corporateIdentify);

        return apiResponse();
    }

    /**
     * 根据设备ID查询点检标准-MOBILE
     * @param deviceId
     * @return
     */
    @RequestMapping("/queryStanardByDeviceId")
    @ApiOperation(value = "根据设备ID查询点检标准-MOBILE-",notes = "根据设备ID查询点检标准-MOBILE",httpMethod = "GET")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "Long",name = "deviceId",
            value = "设备ID",required = true,defaultValue = "1"))
    public ApiResponse<List<SpotInspectionStandardQueryResp>> queryStanardByDeviceId(@RequestParam(name = "deviceId",required = true) Long deviceId,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(spotInspectionStandardService.queryStanardByDeviceId(deviceId,corporateIdentify));
    }

}
