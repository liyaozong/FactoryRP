package cn.tech.yozo.factoryrp.api;

import cn.tech.yozo.factoryrp.entity.DeviceInfoExtendField;
import cn.tech.yozo.factoryrp.service.DeviceInfoExtendFieldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("deviceInfoExtendField")
@Api(description = "设备信息扩展字段定义相关接口")
public class DeviceInfoExtendFieldController extends BaseController{

    @Autowired
    private DeviceInfoExtendFieldService deviceInfoExtendFieldService;

    @RequestMapping("save")
    @ApiOperation(value = "保存设备信息扩展字段设置",notes = "保存设备信息扩展字段设置",httpMethod = "POST")
    public DeviceInfoExtendField save(@RequestBody DeviceInfoExtendField param){
        return deviceInfoExtendFieldService.save(param);
    }

    @ApiOperation(value = "查询设备扩展字段信息",notes = "查询设备扩展字段信息",httpMethod = "GET")
    @GetMapping("get")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "Long",name = "corporateIdentify",
            value = "企业唯一标识",required = true,defaultValue = "111"))
    public DeviceInfoExtendField findByCorporateIdentify(Long corporateIdentify){
        return deviceInfoExtendFieldService.findByCorporateIdentify(corporateIdentify);
    }
}
