package tech.yozo.factoryrp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.service.Impl.OSSService;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.resp.OSSUploadResp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * 文件上传相关接口
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/7
 * @description
 */
@RestController
@RequestMapping("/api/itemUpload")
@Api(description = "上传相关接口")
public class UploadController extends BaseController{


    @Resource
    private OSSService ossService;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${oss.bucketName}")
    private String bucketName;

    private Logger logger = LoggerFactory.getLogger(UploadController.class);

    /**
     * 查询图片访问地址
     * @param key
     * @return
     */
    @ApiOperation(value = "查询图片访问地址-WEB",notes = "查询图片访问地址-WEB",httpMethod = "GET")
    @GetMapping("/viewItem")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String" ,name = "key", paramType = "query" ,
                    value = "上传返回的文件ID",required = true,defaultValue = "3")
    })
    public ApiResponse<String> querySpotInspectionRecordByPlanId(@RequestParam("key") String key, HttpServletRequest request){
        return apiResponse(ossService.getOSSUrl(key));
    }

    /**
     * 文件上传接口
     * @param file
     * @return
     */
    @ApiOperation(value = "文件上传接口",notes = "文件上传接口",httpMethod = "POST")
    @PostMapping("/uploadToOSS")
    @ApiImplicitParam(dataType = "MultipartFile" ,name = "file", paramType = "Object" ,
            value = "文件",required = true)
    public ApiResponse<OSSUploadResp> uploadToOSS(@RequestParam(value = "file", required = true) MultipartFile file){
        if(!file.isEmpty()){
            try {
                String name = file.getOriginalFilename();

                File newFile = new File(name);
                FileOutputStream outStream = null; // 文件输出流用于将数据写入文件
                    outStream = new FileOutputStream(newFile);
                    outStream.write(file.getBytes());
                    outStream.close();
                    // 关闭文件输出流
                    file.transferTo(newFile);
                    FileInputStream fileInputStream = new FileInputStream(newFile);
                    int available = fileInputStream.available();
                    logger.info(">>>>>>>>>>>>>>>>>>>>>>文件流size<<<<<<<<<<<<<<<<<<<<<<<<" + available);
                    // 上传到阿里云

                    OSSUploadResp ossUploadResp = ossService.toOSS(name, fileInputStream);
                    newFile.delete();
                    return apiResponse(ossUploadResp);

                } catch (Exception e) {
                    logger.error(">>>>>>>>>>>>>>>>>>上传文件出现异常<<<<<<<<<<<<<<<" + e.getMessage(), e);
                    return apiResponse("upload exception is"+e.getMessage());
                }
            }
            throw new BussinessException(ErrorCode.REQUEST_FILE_NOT_EXIST.getCode(),
                    ErrorCode.REQUEST_FILE_NOT_EXIST.getMessage());
    }

    //单文件上传
    @RequestMapping(value = "/singleFile" ,method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)//@RequestParam(value = "key", required = true) String key,
    public String singleFileUpload(@RequestParam(value = "file", required = true) MultipartFile file){

        if(!file.isEmpty()){
            try {
                String name = file.getOriginalFilename();

                File newFile = new File(name);
                FileOutputStream outStream = null; // 文件输出流用于将数据写入文件
                try {
                    outStream = new FileOutputStream(newFile);
                    outStream.write(file.getBytes());
                    outStream.close();
                    // 关闭文件输出流
                    file.transferTo(newFile);
                    FileInputStream fileInputStream = new FileInputStream(newFile);
                    int available = fileInputStream.available();
                    String key = ossService.upToOSS(name, fileInputStream);
                    //String key = ossService.upToOSS(name, newFile);
                    // 上传到阿里云
                    newFile.delete();
                    return key;
                } catch (Exception e) {
                    logger.error(">>>>>>>>>>>>>>>>>>上传文件出现异常<<<<<<<<<<<<<<<"+e.getMessage(),e);
                }

                return "upload successful the path is "+file.getName();
            } catch (Exception e) {
                logger.error(">>>>>>>>>>>>>>>>>>上传文件出现异常<<<<<<<<<<<<<<<"+e.getMessage(),e);
            }

        }
        return "";
    }

}
