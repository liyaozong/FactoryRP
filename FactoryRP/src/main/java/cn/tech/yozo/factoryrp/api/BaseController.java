package cn.tech.yozo.factoryrp.api;

import cn.tech.yozo.factoryrp.utils.DateTimeUtil;
import cn.tech.yozo.factoryrp.vo.base.ApiRequest;
import cn.tech.yozo.factoryrp.vo.base.ApiResponse;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基本的控制器
 */
@RestController
public class BaseController {


    /**
     * 生成APIResopnse
     * @param apiRequest
     * @return
     */
    public ApiResponse apiResponse(ApiRequest apiRequest){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setRequestSeqNo(apiRequest.getRequestSeqNo());
        apiResponse.setResponseTime(DateTimeUtil.currentDateToStr(""));
        apiResponse.setRequestTime(apiRequest.getRequestTime());
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
        apiResponse.setRequestTime(apiRequest.getRequestTime());
        apiResponse.setData(object);
        return apiResponse;
    }


    public static void main(String[] args) {
        String s = DateTimeUtil.currentDateToStr("");
        System.out.println(s);
    }

}
