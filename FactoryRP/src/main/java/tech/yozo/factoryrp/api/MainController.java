package tech.yozo.factoryrp.api;

import tech.yozo.factoryrp.enums.ImageTypeEnum;
import tech.yozo.factoryrp.enums.device.DeviceParamDicEnum;
import tech.yozo.factoryrp.enums.device.DeviceStatusEnum;
import tech.yozo.factoryrp.enums.inspection.SpotInspectionItemsRecordTypeEnum;
import tech.yozo.factoryrp.enums.inspection.SpotInspectionPlanRecycleTypeEnum;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.utils.UUIDSequenceWorker;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.resp.DicEnumResp;
import tech.yozo.factoryrp.vo.resp.ErrorCodeResp;
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


    /**
     * 测试API
     * @ApiImplicitParam 接口参数说明
     * @ApiOperation 接口说明
     * @return
     */
    @ApiOperation(value = "查看所有设备字典code",notes = "查看所有设备字典code",httpMethod = "GET")
    @RequestMapping(value = "/viewDeviceDicCode",method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<DicEnumResp>> viewDeviceDicCode(){
        ApiResponse<List<DicEnumResp>> apiResponse = new ApiResponse<>();
        apiResponse.setErrorCode(ErrorCode.SUCCESS.getCode());
        apiResponse.setErrorMessage(ErrorCode.SUCCESS.getCode());
        apiResponse.setData(DeviceParamDicEnum.getAllCodesAndName());
        apiResponse.setRequestSeqNo(UUIDSequenceWorker.uniqueSequenceId().toString());
        return apiResponse;
    }


    /**
     * 查询所有设备状态
     * @return
     */
    @ApiOperation(value = "查询所有设备状态",notes = "查询所有设备状态",httpMethod = "GET")
    @RequestMapping(value = "/queryAllDeviceStatus",method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<DicEnumResp>> queryAllDeviceStatus(){
        ApiResponse<List<DicEnumResp>> apiResponse = new ApiResponse<>();
        apiResponse.setErrorCode(ErrorCode.SUCCESS.getCode());
        apiResponse.setErrorMessage(ErrorCode.SUCCESS.getCode());
        apiResponse.setData(DeviceStatusEnum.getAllCodesAndName());
        apiResponse.setRequestSeqNo(UUIDSequenceWorker.uniqueSequenceId().toString());
        return apiResponse;
    }


    /**
     * 查询所有点检项目记录方式枚举
     * @return
     */
    @ApiOperation(value = "查询所有点检项目记录方式枚举",notes = "查询所有点检项目记录方式枚举",httpMethod = "GET")
    @RequestMapping(value = "/queryAllSpotInspectionItemsRecordType",method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<DicEnumResp>> queryAllSpotInspectionItemsRecordType(){
        ApiResponse<List<DicEnumResp>> apiResponse = new ApiResponse<>();
        apiResponse.setErrorCode(ErrorCode.SUCCESS.getCode());
        apiResponse.setErrorMessage(ErrorCode.SUCCESS.getCode());
        apiResponse.setData(SpotInspectionItemsRecordTypeEnum.getAllCodesAndName());
        apiResponse.setRequestSeqNo(UUIDSequenceWorker.uniqueSequenceId().toString());
        return apiResponse;
    }

    /**
     * 查询巡检计划执行时间类型循环类型枚举
     * @return
     */
    @ApiOperation(value = "查询巡检计划执行时间类型循环类型枚举",notes = "查询巡检计划执行时间类型循环类型枚举",httpMethod = "GET")
    @RequestMapping(value = "/queryAllSpotInspectionPlanRecycleType",method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<DicEnumResp>> queryAllSpotInspectionPlanRecycleType(){
        ApiResponse<List<DicEnumResp>> apiResponse = new ApiResponse<>();
        apiResponse.setErrorCode(ErrorCode.SUCCESS.getCode());
        apiResponse.setErrorMessage(ErrorCode.SUCCESS.getCode());
        apiResponse.setData(SpotInspectionPlanRecycleTypeEnum.getAllCodesAndName());
        apiResponse.setRequestSeqNo(UUIDSequenceWorker.uniqueSequenceId().toString());
        return apiResponse;
    }

    /**
     * 查询所有支持的文件上传类型
     * @return
     */
    @ApiOperation(value = "查询所有支持的文件上传类型",notes = "查询所有支持的文件上传类型",httpMethod = "GET")
    @RequestMapping(value = "/queryAllFileUploadType",method = RequestMethod.GET)
    @ResponseBody
    public ApiResponse<List<DicEnumResp>> queryAllFileUploadType(){
        ApiResponse<List<DicEnumResp>> apiResponse = new ApiResponse<>();
        apiResponse.setErrorCode(ErrorCode.SUCCESS.getCode());
        apiResponse.setErrorMessage(ErrorCode.SUCCESS.getCode());
        apiResponse.setData(ImageTypeEnum.getAllCodesAndName());
        apiResponse.setRequestSeqNo(UUIDSequenceWorker.uniqueSequenceId().toString());
        return apiResponse;
    }

}
