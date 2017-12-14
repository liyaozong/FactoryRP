package tech.yozo.factoryrp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.entity.DeviceSparesType;
import tech.yozo.factoryrp.entity.DeviceTroubleType;
import tech.yozo.factoryrp.service.DeviceTroubleTypeService;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.DeviceTroubleTypeReq;
import tech.yozo.factoryrp.vo.resp.device.trouble.DeviceTroubleTypeVo;
import tech.yozo.factoryrp.vo.resp.sparepars.DeviceSparesTypeResp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 设备故障类型前端控制器
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/14
 * @description
 */
@RestController
@RequestMapping("api/deviceTroubleType")
@Api(description = "故障类型相关接口")
public class DeviceTroubleTypeController extends BaseController{

    @Resource
    private DeviceTroubleTypeService deviceTroubleTypeService;

    @Resource
    private UserAuthService userAuthService;

    /**
     * 根据故障类型id删除故障类型
     */
    @ApiOperation(value = "查询所有故障类型类型-WEB/Mobile",notes = "查询所有故障类型类型-WEB/Mobile",httpMethod = "GET")
    @GetMapping("/queryAlldDeviceTroubleType")
    public ApiResponse<List<DeviceTroubleTypeVo>> queryAllDeviceSparesType(HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceTroubleTypeService.queryAllDeviceTroubleLevel(corporateIdentify));
    }


    /**
     * 新增故障类型类型 根据operateType来区分操作
     * 功能有 添加同级(operateType为1) 添加下级(operateType为2) 修改故障类型名称(operateType为3)
     * @param deviceTroubleTypeReq
     * @param request
     * @return
     */
    @ApiOperation(value = "添加同级故障类型类型-WEB",notes = "添加同级故障类型类型-WEB",httpMethod = "POST")
    @PostMapping("/addSameDeviceTroubleType")
    @ApiImplicitParam(dataType = "DeviceTroubleTypeReq" ,name = "deviceTroubleTypeReq", paramType = "VO" ,
            value = "添加同级故障类型类型",required = true)
    public ApiResponse<DeviceTroubleType> addSameDeviceSpares(@Valid @RequestBody DeviceTroubleTypeReq deviceTroubleTypeReq, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceTroubleTypeService.saveDeviceTroubleLevel(deviceTroubleTypeReq,1,corporateIdentify));
    }




    /**
     * 新增故障类型类型 根据operateType来区分操作
     * 功能有 添加同级(operateType为1) 添加下级(operateType为2) 修改故障类型名称(operateType为3)
     * @param deviceTroubleTypeReq
     * @param request
     * @return
     */
    @ApiOperation(value = "添加下级故障类型类型-WEB",notes = "添加下级故障类型类型-WEB",httpMethod = "POST")
    @PostMapping("/addSubDeviceTroubleType")
    @ApiImplicitParam(dataType = "DeviceTroubleTypeReq" ,name = "deviceTroubleTypeReq", paramType = "VO" ,
            value = "添加下级故障类型类型",required = true)
    public ApiResponse<DeviceSparesType> addSubDeviceSpares(@Valid @RequestBody DeviceTroubleTypeReq deviceTroubleTypeReq, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceTroubleTypeService.saveDeviceTroubleLevel(deviceTroubleTypeReq,2,corporateIdentify));
    }

    /**
     * 修改故障类型类型 根据operateType来区分操作
     * 功能有 添加同级(operateType为1) 添加下级(operateType为2) 修改故障类型名称(operateType为3)
     * @param deviceTroubleTypeReq
     * @param request
     * @return
     */
    @ApiOperation(value = "修改故障类型类型-WEB",notes = "修改故障类型类型-WEB",httpMethod = "POST")
    @PostMapping("/updateDeviceTroubleType")
    @ApiImplicitParam(dataType = "DeviceTroubleTypeReq" ,name = "deviceTroubleTypeReq", paramType = "VO" ,
            value = "修改故障类型类型",required = true)
    public ApiResponse<DeviceTroubleType> updateDeviceSpares(@Valid @RequestBody DeviceTroubleTypeReq deviceTroubleTypeReq, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceTroubleTypeService.saveDeviceTroubleLevel(deviceTroubleTypeReq,3,corporateIdentify));
    }


    /**
     * 删除故障类型类型
     * @param id
     * @param request
     * @return
     */
    @ApiOperation(value = "删除故障类型类型-WEB",notes = "删除故障类型类型-WEB",httpMethod = "GET")
    @RequestMapping("/deleteDeviceTroubleType")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "Long",name = "id",
            value = "需要删除故障类型类型主键",required = true,defaultValue = "6"))
    public ApiResponse deleteDeviceSparesType(Long id, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        deviceTroubleTypeService.deleteDeviceTroubleLevel(id,corporateIdentify);
        return apiResponse();
    }


    /**
     * 调整上级故障类型类型
     * @param id 需要被调整的故障类型类型id
     * @param parentId 需要调整成为的上级故障类型类型
     * @return
     */
    @ApiOperation(value = "调整上级故障类型类型-WEB",notes = "调整上级故障类型类型-WEB",httpMethod = "GET")
    @RequestMapping("/updateUpLevel")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",dataType = "Long",name = "id",
            value = "需要调整的故障类型类型主键",required = true,defaultValue = "1"),@ApiImplicitParam(paramType = "query",dataType = "Long",name = "parentId",
            value = "上级故障类型类型ID",required = true,defaultValue = "2")})
    public ApiResponse<DeviceSparesTypeResp> updateUpLevel(Long id, Long parentId){
        return apiResponse(deviceTroubleTypeService.updateUpLevel(id,parentId));
    }

    /**
     * 调整设备故障类型显示顺序
     * @param operateType 操作类型 1是上移动，2是下移
     * @param id
     * @return
     */
    @ApiOperation(value = "调整设备故障类型显示顺序-WEB",notes = "调整设备故障类型显示顺序-WEB",httpMethod = "GET")
    @RequestMapping("/updateShowOrder")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",dataType = "Integer",name = "operateType",
            value = "操作类型(1为上移，2为下移)",required = true,defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "id",
                    value = "需要被操作的故障类型id",required = true,defaultValue = "2")})
    public ApiResponse<DeviceSparesTypeResp> updateShowOrder(Integer operateType,Long id){
        return apiResponse(deviceTroubleTypeService.updateShowOrder(operateType,id));
    }

    
}
