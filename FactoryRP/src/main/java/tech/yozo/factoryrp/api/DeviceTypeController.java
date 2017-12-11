package tech.yozo.factoryrp.api;

import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.entity.DeviceType;
import tech.yozo.factoryrp.service.DeviceTypeService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.SaveDeviceTypeReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * 设备类型控制器
 */
@RestController
@RequestMapping("api/deviceType")
@Api(description = "设备类型相关接口")
public class DeviceTypeController extends BaseController{

    @Autowired
    private DeviceTypeService deviceTypeService;

    @Autowired
    private UserAuthService userAuthService;

    @ApiOperation(value = "查询设备类型列表",notes = "查询设备类型列表api",httpMethod = "GET")
    @GetMapping("list")
    public ApiResponse<List<DeviceType>> list(HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceTypeService.list(corporateIdentify));
    }


    @ApiOperation(value = "添加同级设备类型",notes = "添加同级设备类型",httpMethod = "POST")
    @RequestMapping("addSameDeviceType")
    public ApiResponse<DeviceType> addSameDeviceType(@RequestBody SaveDeviceTypeReq param, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceTypeService.save(param,1,corporateIdentify));
    }

    @ApiOperation(value = "添加下级设备类型",notes = "添加下级设备类型",httpMethod = "POST")
    @RequestMapping("addSubDeviceType")
    public ApiResponse<DeviceType> addSubDeviceType(@RequestBody SaveDeviceTypeReq param,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceTypeService.save(param,2,corporateIdentify));
    }

    @ApiOperation(value = "修改设备类型",notes = "修改设备类型",httpMethod = "POST")
    @RequestMapping("updateDeviceType")
    public ApiResponse<DeviceType> updateDeviceType(@RequestBody SaveDeviceTypeReq param,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceTypeService.save(param,3,corporateIdentify));
    }

    @ApiOperation(value = "删除设备类型",notes = "删除设备类型",httpMethod = "GET")
    @RequestMapping("deleteDeviceType")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "Long",name = "id",
            value = "需要删除设备类型主键",required = true,defaultValue = "6"))
    public ApiResponse deleteDeviceType(Long id){
        deviceTypeService.delete(id);
        return apiResponse();
    }

    @ApiOperation(value = "调整上级设备类型",notes = "调整上级设备类型",httpMethod = "GET")
    @RequestMapping("updateUpLevel")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",dataType = "Long",name = "id",
            value = "需要调整都设备类型主键",required = true,defaultValue = "1"),@ApiImplicitParam(paramType = "query",dataType = "Long",name = "parentId",
            value = "上级设备类型ID",required = true,defaultValue = "2")})
    public ApiResponse updateUpLevel(Long id,Long parentId){
        deviceTypeService.updateUpLevel(id,parentId);
        return apiResponse();
    }




}
