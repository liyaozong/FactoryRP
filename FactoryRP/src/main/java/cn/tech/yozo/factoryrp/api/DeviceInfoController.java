package cn.tech.yozo.factoryrp.api;

import cn.tech.yozo.factoryrp.entity.DeviceInfo;
import cn.tech.yozo.factoryrp.page.Pagination;
import cn.tech.yozo.factoryrp.service.DeviceInfoService;
import cn.tech.yozo.factoryrp.vo.req.DeviceInfoReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("deviceInfo")
@Api(description = "设备信息相关接口")
public class DeviceInfoController {

    @Autowired
    private DeviceInfoService deviceInfoService;

    @ApiOperation(value = "分页查询设备信息列表",notes = "分页查询设备信息列表",httpMethod = "POST")
    @RequestMapping("list")
    public Pagination<DeviceInfo> list(@RequestBody DeviceInfoReq param){
        return deviceInfoService.findByPage(param);
    }

    @ApiOperation(value = "新增或修改设备信息",notes = "新增或修改设备信息",httpMethod = "POST")
    @RequestMapping("save")
    public DeviceInfo updateContactCompany(@RequestBody DeviceInfo param){
        return deviceInfoService.save(param);
    }
}