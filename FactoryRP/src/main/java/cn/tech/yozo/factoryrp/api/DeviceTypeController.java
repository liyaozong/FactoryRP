package cn.tech.yozo.factoryrp.api;

import cn.tech.yozo.factoryrp.entity.DeviceType;
import cn.tech.yozo.factoryrp.service.DeviceTypeService;
import cn.tech.yozo.factoryrp.vo.req.SaveDeviceTypeReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 设备类型控制器
 */
@RestController
@RequestMapping("deviceType")
@Api(description = "设备类型相关接口")
public class DeviceTypeController {

    @Autowired
    private DeviceTypeService deviceTypeService;

    @ApiOperation(value = "查询设备类型列表",notes = "查询设备类型列表api",httpMethod = "GET")
    @GetMapping("list")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "Long",name = "corporateIdentify",
            value = "企业唯一标识",required = true,defaultValue = "111"))
    public List<DeviceType> list(Long corporateIdentify){
        return deviceTypeService.list(corporateIdentify);
    }


    @ApiOperation(value = "添加同级设备类型",notes = "添加同级设备类型",httpMethod = "POST")
    @RequestMapping("addSameDeviceType")
    public DeviceType addSameDeviceType(@RequestBody SaveDeviceTypeReq param){
        return deviceTypeService.save(param,1);
    }

    @ApiOperation(value = "添加下级设备类型",notes = "添加下级设备类型",httpMethod = "POST")
    @RequestMapping("addSubDeviceType")
    public DeviceType addSubDeviceType(@RequestBody SaveDeviceTypeReq param){
        return deviceTypeService.save(param,2);
    }

    @ApiOperation(value = "修改设备类型",notes = "修改设备类型",httpMethod = "POST")
    @RequestMapping("updateDeviceType")
    public DeviceType updateDeviceType(@RequestBody SaveDeviceTypeReq param){
        return deviceTypeService.save(param,3);
    }

    @ApiOperation(value = "删除设备类型",notes = "删除设备类型",httpMethod = "GET")
    @RequestMapping("deleteDeviceType")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "Long",name = "id",
            value = "需要删除设备类型主键",required = true,defaultValue = "6"))
    public void deleteDeviceType(Long id){
        deviceTypeService.delete(id);
    }

    @ApiOperation(value = "调整上级设备类型",notes = "调整上级设备类型",httpMethod = "GET")
    @RequestMapping("updateUpLevel")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query",dataType = "Long",name = "id",
            value = "需要调整都设备类型主键",required = true,defaultValue = "1"),@ApiImplicitParam(paramType = "query",dataType = "Long",name = "parentId",
            value = "上级设备类型ID",required = true,defaultValue = "2")})
    public void updateUpLevel(Long id,Long parentId){
        deviceTypeService.updateUpLevel(id,parentId);
    }
}
