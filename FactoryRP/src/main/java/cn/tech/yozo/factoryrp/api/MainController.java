package cn.tech.yozo.factoryrp.api;

import cn.tech.yozo.factoryrp.utils.ErrorCode;
import cn.tech.yozo.factoryrp.utils.UUIDSequenceWorker;
import cn.tech.yozo.factoryrp.vo.base.ApiResponse;
import cn.tech.yozo.factoryrp.vo.resp.ErrorCodeResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * main控制器
 */
@RestController
@RequestMapping(value = "/main")
@Api(description = "main控制器(系统说明)")
public class MainController extends BaseController{



    /**
     * 测试API
     * @ApiImplicitParam 接口参数说明
     * @ApiOperation 接口说明
     * @return
     */
    @ApiOperation(value = "查看所有系统返回码",notes = "查看所有系统返回码",httpMethod = "GET")
    @RequestMapping(value = "/viewReturnCode",method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<ErrorCodeResp>> viewReturnCode(){
        ApiResponse<List<ErrorCodeResp>> apiResponse = new ApiResponse<>();
        apiResponse.setErrorCode(ErrorCode.SUCCESS.getCode());
        apiResponse.setErrorMessage(ErrorCode.SUCCESS.getCode());
        apiResponse.setData(ErrorCode.getAllCodesAndMessage());
        apiResponse.setRequestSeqNo(UUIDSequenceWorker.uniqueSequenceId().toString());
        return apiResponse;
    }


}
