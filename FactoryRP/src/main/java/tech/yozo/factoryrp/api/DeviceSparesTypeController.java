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

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 设备备件类型相关接口
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/5
 * @description
 */
@RestController
@RequestMapping("/deviceSparesType")
@Api(description = "设备备件类型相关接口")
public class DeviceSparesTypeController extends BaseController {


    @Resource
    private DeviceSparesTypeService deviceSparesTypeService;

    @Resource
    private UserAuthService userAuthService;

    /**
     * 根据备件id删除备件
     */
    @ApiOperation(value = "查询所有备件类型",notes = "查询所有备件类型",httpMethod = "GET")
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
    @ApiOperation(value = "添加同级备件类型",notes = "添加同级备件类型",httpMethod = "POST")
    @PostMapping("addSameDeviceSpares")
    @ApiImplicitParam(dataType = "DeviceSparesSaveReq" ,name = "deviceSparesSaveReq", paramType = "VO" ,
            value = "添加同级备件类型",required = true)
    public ApiResponse<DeviceSparesType> addSameDeviceSpares(DeviceSparesSaveReq deviceSparesSaveReq, HttpServletRequest request){
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
    @ApiOperation(value = "添加下级备件类型",notes = "添加下级备件类型",httpMethod = "POST")
    @PostMapping("addSubDeviceSpares")
    @ApiImplicitParam(dataType = "DeviceSparesSaveReq" ,name = "deviceSparesSaveReq", paramType = "VO" ,
            value = "添加下级备件类型",required = true)
    public ApiResponse<DeviceSparesType> addSubDeviceSpares(DeviceSparesSaveReq deviceSparesSaveReq, HttpServletRequest request){
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
    @ApiOperation(value = "修改备件类型",notes = "修改备件类型",httpMethod = "POST")
    @PostMapping("updateDeviceSpares")
    @ApiImplicitParam(dataType = "DeviceSparesSaveReq" ,name = "deviceSparesSaveReq", paramType = "VO" ,
            value = "修改备件类型",required = true)
    public ApiResponse<DeviceSparesType> updateDeviceSpares(DeviceSparesSaveReq deviceSparesSaveReq, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceSparesTypeService.saveDeviceSparesType(deviceSparesSaveReq,3,corporateIdentify));
    }

}
