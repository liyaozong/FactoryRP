package cn.tech.yozo.factoryrp.api;

import cn.tech.yozo.factoryrp.config.auth.UserAuthService;
import cn.tech.yozo.factoryrp.entity.DeviceInfoExtendField;
import cn.tech.yozo.factoryrp.service.DeviceInfoExtendFieldService;
import cn.tech.yozo.factoryrp.vo.base.ApiResponse;
import cn.tech.yozo.factoryrp.vo.req.DeviceInfoExtendFieldReq;
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

@RestController
@RequestMapping("api/deviceInfoExtendField")
@Api(description = "设备信息扩展字段定义相关接口")
public class DeviceInfoExtendFieldController extends BaseController{

    @Autowired
    private DeviceInfoExtendFieldService deviceInfoExtendFieldService;

    @Autowired
    private UserAuthService userAuthService;

    @RequestMapping("save")
    @ApiOperation(value = "保存设备信息扩展字段设置",notes = "保存设备信息扩展字段设置",httpMethod = "POST")
    public ApiResponse<DeviceInfoExtendField> save(@RequestBody DeviceInfoExtendFieldReq param, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceInfoExtendFieldService.save(param,corporateIdentify));
    }

    @ApiOperation(value = "查询设备扩展字段信息",notes = "查询设备扩展字段信息",httpMethod = "GET")
    @GetMapping("get")
    public ApiResponse<DeviceInfoExtendField> findByCorporateIdentify(HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceInfoExtendFieldService.findByCorporateIdentify(corporateIdentify));
    }
}
