package tech.yozo.factoryrp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.service.ProcessService;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.DeviceParameterDicBatchAddReq;
import tech.yozo.factoryrp.vo.req.DeviceProcessAddReq;
import tech.yozo.factoryrp.vo.resp.process.DeviceProcessAddResp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
}
