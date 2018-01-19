package tech.yozo.factoryrp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.entity.DeviceProcess;
import tech.yozo.factoryrp.entity.DeviceProcessType;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.service.ProcessService;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.DeviceParameterDicBatchAddReq;
import tech.yozo.factoryrp.vo.req.DeviceProcessAddReq;
import tech.yozo.factoryrp.vo.req.DeviceProcessQueryReq;
import tech.yozo.factoryrp.vo.resp.process.DeviceProcessAddResp;
import tech.yozo.factoryrp.vo.resp.process.DeviceProcessDetailQueryWarpResp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 设备流程前端控制器
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
@RestController
@RequestMapping("/api/deviceProcess")
@Api(description = "设备流程相关接口")
public class DeviceProcessController extends BaseController  {


    @Resource
    private ProcessService processService;


    @Resource
    private UserAuthService userAuthService;


    /**
     * 查询流程详情-->前端画图所需要的数据
     * @param processId
     * @return
     */
    @ApiOperation(value = "查询流程详情-->前端画图所需要的数据",notes = "查询流程详情-->前端画图所需要的数据",httpMethod = "GET")
    @GetMapping("/queryProcessDetail")
    @ApiImplicitParam(dataType = "processId" ,name = "processId", paramType = "VO" ,
            value = "流程ID",required = true)
    public ApiResponse<DeviceProcessDetailQueryWarpResp> queryProcessDetail(Long processId,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(processService.queryProcessDetail(processId,corporateIdentify));
    }

    /**
     * 查询所有流程类型集合
     * @return
     */
    @ApiOperation(value = "查询所有流程类型集合",notes = "查询所有流程类型集合",httpMethod = "GET")
    @GetMapping("/queryAllDecviceProcessType")
    public ApiResponse<List<DeviceProcessType>> queryAllDecviceProcessType(HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(processService.queryAllDecviceProcessType(corporateIdentify));
    }

    /**
     * 新增流程
     * @param deviceProcessAddReq
     * @param request
     * @return
     */
    @PostMapping("/addDeviceProcess")
    @ApiOperation(value = "新增流程",notes = "新增流程",httpMethod = "POST")
    @ApiImplicitParam(dataType = "DeviceProcessAddReq" ,name = "deviceProcessAddReq", paramType = "VO" ,
            value = "新增流程",required = true)
    public ApiResponse<DeviceProcessAddResp> addDeviceProcess(@Valid @RequestBody DeviceProcessAddReq deviceProcessAddReq, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(processService.addDeviceProcess(deviceProcessAddReq,corporateIdentify));
    }


    /**
     * 流程分页查询
     * @param deviceProcessQueryReq
     * @param request
     * @return
     */
    @PostMapping("/findByPage")
    @ApiOperation(value = "流程分页查询",notes = "流程分页查询",httpMethod = "POST")
    @ApiImplicitParam(dataType = "DeviceProcessQueryReq" ,name = "deviceProcessQueryReq", paramType = "VO" ,
            value = "流程分页查询",required = true)
    public ApiResponse<Pagination<DeviceProcess>> addDeviceProcess(@Valid @RequestBody DeviceProcessQueryReq deviceProcessQueryReq, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(processService.findByPage(deviceProcessQueryReq,corporateIdentify));
    }
}
