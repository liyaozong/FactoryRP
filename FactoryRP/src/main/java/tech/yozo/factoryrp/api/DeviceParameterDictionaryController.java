package tech.yozo.factoryrp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.entity.DeviceParameterDictionary;
import tech.yozo.factoryrp.service.DeviceParameterDictionaryService;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.DeviceParameterDicReq;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 设备基本参数相关相关接口
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/11
 * @description
 */
@RestController
@RequestMapping("/api/deviceParameterDictionary")
@Api(description = "设备基本参数相关相关接口")
public class DeviceParameterDictionaryController extends BaseController {

    @Resource
    private DeviceParameterDictionaryService deviceParameterDictionaryService;

    @Resource
    private UserAuthService userAuthService;


    /**
     * 保存设备基本参数
     * @param deviceParameterDicReq
     * @param request
     * @return
     */
    @PostMapping("/save")
    @ApiOperation(value = "保存设备基本参数",notes = "保存设备基本参数",httpMethod = "POST")
    @ApiImplicitParam(dataType = "DeviceParameterDicReq" ,name = "deviceParameterDicReq", paramType = "VO" ,
                     value = "保存设备基本参数",required = true)
    public ApiResponse save(@Valid @RequestBody DeviceParameterDicReq deviceParameterDicReq, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        deviceParameterDictionaryService.add(deviceParameterDicReq,corporateIdentify);
        return apiResponse();
    }


    /**
     * 更新设备字典
     * @param deviceParameterDicReq
     */
    @PostMapping("/update")
    @ApiOperation(value = "更新设备字典",notes = "更新设备字典",httpMethod = "POST")
    @ApiImplicitParam(dataType = "DeviceParameterDicReq" ,name = "deviceParameterDicReq", paramType = "VO" ,
            value = "更新设备字典",required = true)
    public ApiResponse update(DeviceParameterDicReq deviceParameterDicReq, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        deviceParameterDictionaryService.update(deviceParameterDicReq,corporateIdentify);
        return apiResponse();
    }

    /**
     * 根据code查询设备参数列表
     * @param code
     * @return
     */
    @ApiOperation(value = "根据code查询设备参数列表",notes = "根据code查询设备参数列表",httpMethod = "GET")
    @GetMapping("/findByCode")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String" ,name = "code", paramType = "query" ,
                    value = "参数code",required = true,defaultValue = "1")
    })
    ApiResponse<List<DeviceParameterDictionary>> findByCode(String code, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(deviceParameterDictionaryService.findByCode(code,corporateIdentify));
    }

}
