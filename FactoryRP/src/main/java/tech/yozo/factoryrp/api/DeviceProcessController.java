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
import tech.yozo.factoryrp.vo.req.DeviceProcessAddReq;
import tech.yozo.factoryrp.vo.req.DeviceProcessEditReq;
import tech.yozo.factoryrp.vo.req.DeviceProcessQueryReq;
import tech.yozo.factoryrp.vo.resp.process.DeviceProcessAddResp;
import tech.yozo.factoryrp.vo.resp.process.DeviceProcessDetailQueryWarpResp;
import tech.yozo.factoryrp.vo.resp.process.DeviceProcessDetailWarpResp;
import tech.yozo.factoryrp.vo.resp.process.DeviceProcessStepQueryResp;

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
     * 分步查询流程审核人员详细信息
     * @param processType  流程类型
     * @param processStage 流程状态
     * @param processStep 步数ID
     * @return
     */
    @ApiOperation(value = "分步查询流程审核人员详细信息-->内部接口，测试用",notes = "分步查询流程审核人员详细信息-->内部接口，测试用",httpMethod = "GET")
    @GetMapping("/queryProcessAduitInfoByStep")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String" ,name = "processType", paramType = "query" ,
                    value = "流程类型",required = true,defaultValue = "device_process_type_receive"),
            @ApiImplicitParam(dataType = "String" ,name = "processStage", paramType = "query" ,
                    value = "流程状态",required = true,defaultValue = "device_process_phase_application_approval"),
            @ApiImplicitParam(dataType = "String" ,name = "processStep", paramType = "query" ,
                    value = "流程步数",required = true,defaultValue = "1")
    })
    public  ApiResponse<DeviceProcessDetailWarpResp> queryProcessAduitInfoByStep(@RequestParam(name = "processType") String processType,@RequestParam(name = "processStage") String processStage,@RequestParam(name = "processStep") Integer processStep, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(processService.queryProcessAduitInfoByStep(processType,processStage,processStep,corporateIdentify));
    }


    /**
     * 根据流程详情ID查询流程审核人员数据
     * @param processStepId
     * @return 流程详情ID
     */
    @ApiOperation(value = "根据流程详情ID查询流程审核人员数据-->内部接口，测试用",notes = "根据流程详情ID查询流程审核人员数据-->内部接口，测试用",httpMethod = "GET")
    @GetMapping("/queryProcessAduitInfoByStepId")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Long" ,name = "processStepId", paramType = "query" ,
                    value = "流程类型",required = true,defaultValue = "1"),
    })
    public ApiResponse<DeviceProcessDetailWarpResp> queryProcessAduitInfoByStepId(Long processStepId){
        return apiResponse(processService.queryProcessAduitInfoByStep(processStepId));
    }

    /**
     * 流程步骤查询
     * 业务逻辑: 不给步数就返回第一步和返回下一步的ID
     *          没有下一步ID就是空
     * @param processType 流程类型
     * @param processStage 流程状态
     * @param processStep 流程步数 可为空
     * @return
     */
    @ApiOperation(value = "流程步骤查询-->内部接口，测试用",notes = "流程步骤查询-->内部接口，测试用",httpMethod = "GET")
    @GetMapping("/processStepQuery")
    @ApiImplicitParams({
                 @ApiImplicitParam(dataType = "String" ,name = "processType", paramType = "query" ,
                            value = "流程类型",required = true,defaultValue = "device_process_type_receive"),
                 @ApiImplicitParam(dataType = "String" ,name = "processStage", paramType = "query" ,
                    value = "流程状态",required = true,defaultValue = "device_process_phase_application_approval"),
                 @ApiImplicitParam(dataType = "Integer" ,name = "processStep", paramType = "query" ,
                    value = "流程步数",required = true,defaultValue = "1")
    })
    public ApiResponse<DeviceProcessStepQueryResp> processStepQuery(@RequestParam("processType") String processType,@RequestParam("processStage")  String processStage, @RequestParam("processStep") Integer processStep, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(processService.processStepQuery(processType,processStage,processStep,corporateIdentify));
    }

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
     * 删除设备流程
     * @param processId
     * @param request
     */
    @ApiOperation(value = "删除设备流程",notes = "删除设备流程",httpMethod = "GET")
    @GetMapping("/deleteDeviceProcess")
    @ApiImplicitParam(dataType = "processId" ,name = "processId", paramType = "VO" ,
            value = "流程ID",required = true)
    public ApiResponse deleteDeviceProcess(Long processId,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        processService.deleteDeviceProcess(processId,corporateIdentify);
        return apiResponse();
    }

    /**
     * 查询所有流程类型集合
     * @return
     */
    @ApiOperation(value = "查询所有流程类型集合",notes = "查询所有流程类型集合",httpMethod = "GET")
    @GetMapping("/queryAllDecviceProcessType")
    public ApiResponse<List<DeviceProcessType>> queryAllDecviceProcessType(HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(processService.queryAllDeviceProcessType(corporateIdentify));
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


    /**
     * 编辑设备流程详情
     * @param deviceProcessEditReq
     * @param request
     */
    @PostMapping("/editDeviceProcess")
    @ApiOperation(value = "编辑设备流程详情",notes = "编辑设备流程详情",httpMethod = "POST")
    @ApiImplicitParam(dataType = "DeviceProcessEditReq" ,name = "deviceProcessEditReq", paramType = "VO" ,
            value = "编辑设备流程详情",required = true)
    public ApiResponse editDeviceProcess(@RequestBody @Valid DeviceProcessEditReq deviceProcessEditReq, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        processService.editDeviceProcess(deviceProcessEditReq,corporateIdentify);
        return apiResponse();
    }

}
