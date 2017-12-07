package tech.yozo.factoryrp.api;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.entity.DeviceInfo;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.service.DeviceInfoService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.DeviceInfoReq;
import tech.yozo.factoryrp.vo.resp.device.info.FullDeviceInfoResp;
import tech.yozo.factoryrp.vo.resp.device.info.SimpleDeviceInfoResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/deviceInfo")
@Api(description = "设备信息相关接口")
public class DeviceInfoController extends BaseController{

    @Autowired
    private DeviceInfoService deviceInfoService;
    @Autowired
    private UserAuthService userAuthService;

    @ApiOperation(value = "分页查询设备信息列表--WEB",notes = "分页查询设备信息列表--WEB",httpMethod = "POST")
    @RequestMapping("list")
    public ApiResponse<Pagination<FullDeviceInfoResp>> list(@RequestBody DeviceInfoReq param, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceInfoService.findByPage(param,corporateIdentify));
    }

    @ApiOperation(value = "新增或修改设备信息--WEB/Mobile",notes = "新增或修改设备信息--WEB/Mobile",httpMethod = "POST")
    @RequestMapping("save")
    public ApiResponse<DeviceInfo> updateContactCompany(@RequestBody DeviceInfo param, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        param.setCorporateIdentify(corporateIdentify);
        return apiResponse(deviceInfoService.save(param));
    }

    @ApiOperation(value = "查询单个设备信息--WEB/Mobile",notes = "查询单个设备信息--WEB/Mobile",httpMethod = "GET")
    @RequestMapping("get")
    public ApiResponse<FullDeviceInfoResp> getById(Long id, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceInfoService.getById(id,corporateIdentify));
    }

    @ApiOperation(value = "分页查询设备信息列表--Mobile",notes = "分页查询设备信息列表--Mobile",httpMethod = "POST")
    @RequestMapping("listSimpleInfo")
    public ApiResponse<Pagination<SimpleDeviceInfoResp>> listSimpleInfo(@RequestBody DeviceInfoReq param, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceInfoService.findSimpleInfoByPage(param,corporateIdentify));
    }

    /**
     * 批量删除关联信息
     * @param ids
     */
    @RequestMapping("batchDelete")
    @ApiOperation(value = "批量删除设备信息--WEB",notes = "批量删除设备信息--WEB",httpMethod = "GET")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "String",name = "ids",
            value = "需要删除的主键，多个主键用逗号分割",required = true,defaultValue = "1,2"))
    public ApiResponse deleteRelInfoByIds(String ids,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        List<Long> idList = new ArrayList<>();
        if (!CheckParam.isNull(ids)){
            String[] idArray =ids.split(",");
            if (idArray.length>0){
                for(String id : idArray){
                    idList.add(Long.parseLong(id));
                }
            }
        }
        deviceInfoService.deleteRelInfoByIds(idList,corporateIdentify);
        return apiResponse();
    }
}
