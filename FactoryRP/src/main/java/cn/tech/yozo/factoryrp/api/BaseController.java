package cn.tech.yozo.factoryrp.api;

import cn.tech.yozo.factoryrp.exception.ApiException;
import cn.tech.yozo.factoryrp.exception.BussinessException;
import cn.tech.yozo.factoryrp.utils.DateTimeUtil;
import cn.tech.yozo.factoryrp.utils.ErrorCode;
import cn.tech.yozo.factoryrp.vo.base.ApiCorporateIdentifyRequest;
import cn.tech.yozo.factoryrp.vo.base.ApiRequest;
import cn.tech.yozo.factoryrp.vo.base.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description 验证器 基本的控制器
 */
@RestController
public class BaseController {


    Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 生成APIResopnse
     * @param apiRequest
     * @return
     */
    public ApiResponse apiResponse(ApiRequest apiRequest){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setRequestSeqNo(apiRequest.getRequestSeqNo());
        apiResponse.setResponseTime(DateTimeUtil.currentDateToStr(""));
        return apiResponse;
    }


    /**
     * GET请求的返回封装
     * @param requestSeqNo
     * @param object
     * @return
     */
    public ApiResponse apiResponse(String requestSeqNo,Object object){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setRequestSeqNo(requestSeqNo);
        apiResponse.setResponseTime(DateTimeUtil.currentDateToStr(""));
        apiResponse.setData(object);
        return apiResponse;
    }

    /**
     * 生成DATA里面带有指定类型的
     * @param apiRequest
     * @param object
     * @return
     */
    public ApiResponse apiResponse(ApiCorporateIdentifyRequest apiRequest, Object object){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setRequestSeqNo(apiRequest.getRequestSeqNo());
        apiResponse.setResponseTime(DateTimeUtil.currentDateToStr(""));
        apiResponse.setData(object);
        return apiResponse;
    }

    /**
     * 生成DATA里面带有指定类型的
     * @param apiRequest
     * @param object
     * @return
     */
    public ApiResponse apiResponse(ApiRequest apiRequest,Object object){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setRequestSeqNo(apiRequest.getRequestSeqNo());
        apiResponse.setResponseTime(DateTimeUtil.currentDateToStr(""));
        apiResponse.setData(object);
        return apiResponse;
    }

    /**
     * 根据HttpServlet生成返回请求信息
     * @param request
     * @return
     */
    public ApiResponse apiResponse(HttpServletRequest request){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setResponseTime(DateTimeUtil.currentDateToStr(""));
        apiResponse.setRequestSeqNo(request.getParameter("requestSeqNo"));

        return apiResponse;
    }


    /**
     * 处理基本的异常信息
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler
    public ApiResponse<Map<String,Object>> handleBaseException(HttpServletRequest request, Exception e){
        ApiResponse apiResponse = apiResponse(request);
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>"+request.getRequestURI()+"<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        if(e instanceof MethodArgumentNotValidException) return methodArgumentNotValidExceptionHandler(apiResponse,e);
        if (e instanceof ApiException) return apiExceptionHandlerMethod(apiResponse,e);
        if (e instanceof BussinessException) return businessExceptionHandlerMethod(apiResponse,e);
        return otherExceptionHandler(apiResponse,e);
    }


    /**
     * 其他异常处理器
     * @param resp
     * @param e
     * @return
     */
    private ApiResponse<Map<String, Object>> otherExceptionHandler(ApiResponse<Map<String, Object>> resp,Exception e){
        resp.setErrorCode(ErrorCode.ERROR.getCode());
        resp.setErrorMessage(ErrorCode.ERROR.getMessage());
        return resp;
    }

    /**
     * 业务异常处理器
     * @param resp
     * @param e
     * @return
     */
    private ApiResponse<Map<String, Object>> businessExceptionHandlerMethod(ApiResponse<Map<String, Object>> resp,	Exception e) {
        BussinessException se = (BussinessException) e;
        resp.setErrorCode(se.getErrorCode());
        resp.setErrorMessage(se.getErrorMessage());

        return resp;
    }

    /**
     * API异常处理器
     * @param resp
     * @param e
     * @author wb-wanghao
     */
    private ApiResponse<Map<String, Object>> apiExceptionHandlerMethod(ApiResponse<Map<String, Object>> resp,Exception e){
        ApiException se = (ApiException) e;
        resp.setErrorCode(se.getErrorCode());
        resp.setErrorMessage(se.getErrorMessage());
        return resp;
    }

    /**
     * 处理参数格式验证不通过的时候的处理器
     * @param resp
     * @param e
     */
    public ApiResponse methodArgumentNotValidExceptionHandler(ApiResponse resp, Exception e){

        MethodArgumentNotValidException be = (MethodArgumentNotValidException) e;
        StringBuilder errStr = new StringBuilder();
        if(be.getBindingResult().getErrorCount()>0){
            for (ObjectError oe : be.getBindingResult().getAllErrors()) {
                errStr.append(oe.getDefaultMessage()).append(";");
            }
        }
        resp.setErrorCode(ErrorCode.PARAM_ERROR.getCode());
        resp.setErrorMessage(errStr.length()>0?errStr.toString():ErrorCode.PARAM_ERROR.getMessage());
        return resp;
    }

    public static void main(String[] args) {
        String s = DateTimeUtil.currentDateToStr("");
        System.out.println(s);
    }

}
