package cn.tech.yozo.factoryrp.api;

import cn.tech.yozo.factoryrp.config.auth.UserAuthService;
import cn.tech.yozo.factoryrp.page.Pagination;
import cn.tech.yozo.factoryrp.service.DeviceSparePartRelService;
import cn.tech.yozo.factoryrp.utils.CheckParam;
import cn.tech.yozo.factoryrp.vo.base.ApiResponse;
import cn.tech.yozo.factoryrp.vo.req.QueryDeviceSpareRelReq;
import cn.tech.yozo.factoryrp.vo.req.SaveRelDeviceReq;
import cn.tech.yozo.factoryrp.vo.req.SaveRelSparePartReq;
import cn.tech.yozo.factoryrp.vo.resp.device.rel.DeviceSpartRelResp;
import cn.tech.yozo.factoryrp.vo.resp.device.rel.SpartDeviceRelResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxiang
 * @create 2017-12-02 下午12:56
 **/
@RestController
@RequestMapping("deviceSpareRel")
@Api(description = "设备备件关联信息相关接口")
public class DeviceSparePartRelController extends BaseController{

    @Autowired
    private DeviceSparePartRelService deviceSparePartRelService;

    @Autowired
    private UserAuthService userAuthService;

    /**
     * 保存设备关联的备件信息
     * @param param
     */
    @RequestMapping("saveSpareRel")
    @ApiOperation(value = "保存设备关联的备件信息--WEB",notes = "保存设备关联的备件信息--WEB",httpMethod = "POST")
    public ApiResponse saveRelSparePart(@RequestBody SaveRelSparePartReq param, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        deviceSparePartRelService.saveRelSparePart(param,corporateIdentify);
        return apiResponse();
    }

    /**
     * 保存备件关联的设备信息
     * @param param
     */
    @RequestMapping("saveDeviceRel")
    @ApiOperation(value = "保存备件关联的设备信息--WEB",notes = "保存备件关联的设备信息--WEB",httpMethod = "POST")
    public ApiResponse saveRelDevice(@RequestBody SaveRelDeviceReq param, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        deviceSparePartRelService.saveRelDevice(param,corporateIdentify);
        return apiResponse();
    }

    /**
     *根据备件查询关联的设备信息
     * @param req
     * @return
     */
    @RequestMapping("findRelDeviceinfo")
    @ApiOperation(value = "根据备件查询关联的设备信息--WEB",notes = "根据备件查询关联的设备信息--WEB",httpMethod = "POST")
    public ApiResponse<Pagination<DeviceSpartRelResp>> findRelDeviceInfoByPage(@RequestBody QueryDeviceSpareRelReq req, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceSparePartRelService.findRelDeviceInfoByPage(req,corporateIdentify));
    }

    /**
     *根据设备查询关联的备件信息
     * @param req
     * @return
     */
    @RequestMapping("findRelSparts")
    @ApiOperation(value = "根据设备查询关联的备件信息--WEB/MOBILE",notes = "根据设备查询关联的备件信息--WEB/MOBILE",httpMethod = "POST")
    public ApiResponse<Pagination<SpartDeviceRelResp>> findRelSpareInfoByPage(@RequestBody QueryDeviceSpareRelReq req, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceSparePartRelService.findRelSpareInfoByPage(req,corporateIdentify));
    }

    /**
     * 批量删除关联信息
     * @param ids
     */
    @RequestMapping("batchDelete")
    @ApiOperation(value = "批量删除关联信息--WEB",notes = "批量删除关联信息--WEB",httpMethod = "GET")
    @ApiImplicitParams(@ApiImplicitParam(paramType = "query",dataType = "String",name = "ids",
            value = "需要删除的主键，多个主键用逗号分割",required = true,defaultValue = "1,2"))
    public ApiResponse deleteRelInfoByIds(String ids){
        List<Long> idList = new ArrayList<>();
        if (!CheckParam.isNull(ids)){
           String[] is =ids.split(",");
           if (is.length>0){
               for(String id : is){
                   idList.add(Long.parseLong(id));
               }
           }
        }
        deviceSparePartRelService.deleteRelInfoByIds(idList);
        return apiResponse();
    }
}
