package cn.tech.yozo.factoryrp.api;

import cn.tech.yozo.factoryrp.entity.DeviceInfo;
import cn.tech.yozo.factoryrp.page.Pagination;
import cn.tech.yozo.factoryrp.service.DeviceInfoService;
import cn.tech.yozo.factoryrp.vo.base.ApiResponse;
import cn.tech.yozo.factoryrp.vo.req.DeviceInfoReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/deviceInfo")
@Api(description = "设备信息相关接口")
public class DeviceInfoController extends BaseController{

    @Autowired
    private DeviceInfoService deviceInfoService;

    @ApiOperation(value = "分页查询设备信息列表",notes = "分页查询设备信息列表",httpMethod = "POST")
    @RequestMapping("list")
    public ApiResponse<Pagination<DeviceInfo>> list(@RequestBody DeviceInfoReq param){
        return apiResponse(deviceInfoService.findByPage(param));
    }

    @ApiOperation(value = "新增或修改设备信息",notes = "新增或修改设备信息",httpMethod = "POST")
    @RequestMapping("save")
    public ApiResponse<DeviceInfo> updateContactCompany(@RequestBody DeviceInfo param){
        return apiResponse(deviceInfoService.save(param));
    }

    @ApiOperation(value = "查询单个设备信息",notes = "查询单个设备信息",httpMethod = "GET")
    @RequestMapping("get")
    public ApiResponse<DeviceInfo> getById(Long id){
        return apiResponse(deviceInfoService.getById(id));
    }
}
