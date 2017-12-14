package tech.yozo.factoryrp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.entity.DeviceSparesType;
import tech.yozo.factoryrp.entity.DeviceType;
import tech.yozo.factoryrp.service.DeviceSparesTypeService;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.DeviceSparesSaveReq;
import tech.yozo.factoryrp.vo.resp.sparepars.DeviceSparesTypeResp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 备件类型相关接口
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/5
 * @description
 */
@RestController
@RequestMapping("api/deviceSparesType")
@Api(description = "备件类型相关接口")
public class DeviceSparesTypeController extends BaseController {


    @Resource
    private DeviceSparesTypeService deviceSparesTypeService;

    @Resource
    private UserAuthService userAuthService;

    /**
     * 根据备件id删除备件
     */
    @ApiOperation(value = "查询所有备件类型-WEB/Mobile",notes = "查询所有备件类型-WEB/Mobile",httpMethod = "GET")
    @GetMapping("/queryAllDeviceSparesType")
    public ApiResponse queryAllDeviceSparesType(HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceSparesTypeService.queryAllDeviceSparesType(corporateIdentify));
    }


    /**
     * 新增备件类型 根据operateType来区分操作
     * 功能有 添加同级(operateType为1) 添加下级(operateType为2) 修改备件名称(operateType为3)
     * @param deviceSparesSaveReq
     * @param request
     * @return
     */
    @ApiOperation(value = "添加同级备件类型-WEB",notes = "添加同级备件类型-WEB",httpMethod = "POST")
    @PostMapping("/addSameDeviceSpares")
    @ApiImplicitParam(dataType = "DeviceSparesSaveReq" ,name = "deviceSparesSaveReq", paramType = "VO" ,
            value = "添加同级备件类型",required = true)
    public ApiResponse<DeviceSparesType> addSameDeviceSpares(@Valid @RequestBody DeviceSparesSaveReq deviceSparesSaveReq, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceSparesTypeService.saveDeviceSparesType(deviceSparesSaveReq,1,corporateIdentify));
    }




    /**
     * 新增备件类型 根据operateType来区分操作
     * 功能有 添加同级(operateType为1) 添加下级(operateType为2) 修改备件名称(operateType为3)
     * @param deviceSparesSaveReq
     * @param request
     * @return
     */
    @ApiOperation(value = "添加下级备件类型-WEB",notes = "添加下级备件类型-WEB",httpMethod = "POST")
    @PostMapping("/addSubDeviceSpares")
    @ApiImplicitParam(dataType = "DeviceSparesSaveReq" ,name = "deviceSparesSaveReq", paramType = "VO" ,
            value = "添加下级备件类型",required = true)
    public ApiResponse<DeviceSparesType> addSubDeviceSpares(@Valid @RequestBody DeviceSparesSaveReq deviceSparesSaveReq, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceSparesTypeService.saveDeviceSparesType(deviceSparesSaveReq,2,corporateIdentify));
    }

    /**
     * 新增备件类型 根据operateType来区分操作
     * 功能有 添加同级(operateType为1) 添加下级(operateType为2) 修改备件名称(operateType为3)
     * @param deviceSparesSaveReq
     * @param request
     * @return
     */
    @ApiOperation(value = "修改备件类型-WEB",notes = "修改备件类型-WEB",httpMethod = "POST")
    @PostMapping("/updateDeviceSpares")
    @ApiImplicitParam(dataType = "DeviceSparesSaveReq" ,name = "deviceSparesSaveReq", paramType = "VO" ,
            value = "修改备件类型",required = true)
    public ApiResponse<DeviceSparesType> updateDeviceSpares(@Valid @RequestBody DeviceSparesSaveReq deviceSparesSaveReq, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceSparesTypeService.saveDeviceSparesType(deviceSparesSaveReq,3,corporateIdentify));
    }


    /**
     * 删除备件类型
     * @param id
     * @param request
     * @return
     */
    @ApiOperation(value = "删除备件类型-WEB",notes = "删除备件类型-WEB",httpMethod = "GET")
    @RequestMapping("/deleteDeviceSparesType")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "Long",name = "id",
            value = "需要删除备件类型主键",required = true,defaultValue = "6"))
    public ApiResponse deleteDeviceSparesType(Long id, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        deviceSparesTypeService.deleteDeviceSparesType(id,corporateIdentify);
        return apiResponse();
    }


    /**
     * 调整上级备件类型
     * @param id 需要被调整的备件类型id
     * @param parentId 需要调整成为的上级备件类型
     * @return
     */
    @ApiOperation(value = "调整上级备件类型-WEB",notes = "调整上级备件类型-WEB",httpMethod = "GET")
    @RequestMapping("/updateUpLevel")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",dataType = "Long",name = "id",
            value = "需要调整的备件类型主键",required = true,defaultValue = "1"),@ApiImplicitParam(paramType = "query",dataType = "Long",name = "parentId",
            value = "上级备件类型ID",required = true,defaultValue = "2")})
    public ApiResponse<DeviceSparesTypeResp> updateUpLevel(Long id, Long parentId){
        return apiResponse(deviceSparesTypeService.updateUpLevel(id,parentId));
    }

    /**
     * 调整设备备件显示顺序
     * @param operateType 操作类型 1是上移动，2是下移
     * @param id
     * @return
     */
    @ApiOperation(value = "调整设备备件显示顺序-WEB",notes = "调整设备备件显示顺序-WEB",httpMethod = "GET")
    @RequestMapping("/updateShowOrder")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",dataType = "Integer",name = "operateType",
            value = "操作类型(1为上移，2为下移)",required = true,defaultValue = "1"),
            @ApiImplicitParam(paramType = "query",dataType = "Long",name = "id",
            value = "需要被操作的备件id",required = true,defaultValue = "2")})
    public ApiResponse<DeviceSparesTypeResp> updateShowOrder(Integer operateType,Long id){
        return apiResponse(deviceSparesTypeService.updateShowOrder(operateType,id));
    }

}
