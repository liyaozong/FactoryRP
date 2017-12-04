package tech.yozo.factoryrp.api;

import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.service.TroubleRecordService;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.AddTroubleRecordReq;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.yozo.factoryrp.vo.req.TroubleListReq;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import tech.yozo.factoryrp.vo.resp.device.trouble.SimpleTroubleRecordVo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenxiang
 * @create 2017-12-03 下午11:13
 **/
@RestController
@RequestMapping("troubleRecord")
@Api(description = "故障报修相关接口")
public class TroubleRecordController extends BaseController{
    @Autowired
    private TroubleRecordService troubleRecordService;

    @Autowired
    private UserAuthService userAuthService;

    @RequestMapping("add")
    @ApiOperation(value = "故障报修/故障提出接口--WEB/Mobile",notes = "故障报修/故障提出接口--WEB/Mobile",httpMethod = "POST")
    public ApiResponse addTroubleRecord(@RequestBody AddTroubleRecordReq param, HttpServletRequest request){
        Long corporateIdentify =userAuthService.getCurrentUserCorporateIdentify(request);
        AuthUser user = userAuthService.getCurrentUserName(request);
        troubleRecordService.addTroubleRecord(param,corporateIdentify,user);
        return apiResponse();
    }

    @RequestMapping("list")
    @ApiOperation(value = "设备对应的故障列表--WEB",notes = "设备对应的故障列表--WEB",httpMethod = "POST")
    public ApiResponse<Pagination<SimpleTroubleRecordVo>> list(@RequestBody TroubleListReq param){
        return apiResponse(troubleRecordService.findByPage(param));
    }
}
