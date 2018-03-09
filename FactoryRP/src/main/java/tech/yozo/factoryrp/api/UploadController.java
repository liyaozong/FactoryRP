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
import tech.yozo.factoryrp.service.FileService;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.BatchDeleteOSSItemReq;
import tech.yozo.factoryrp.vo.req.FileUrlBatchQueryReq;
import tech.yozo.factoryrp.vo.resp.FileUploadResp;
import tech.yozo.factoryrp.vo.resp.ImageUploadResp;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 文件上传相关接口
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/7
 * @description
 */
@RestController
@RequestMapping("/api/itemUpload")
@Api(description = "文件相关接口")
public class UploadController extends BaseController{


    @Resource
    private FileService fileService;


    @Resource
    private tech.yozo.factoryrp.service.Impl.OSSService ossService;

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
     * 查询文件访问地址
     * @param key
     * @return
     */
    @ApiOperation(value = "查询文件访问地址-WEB",notes = "查询文件访问地址-WEB",httpMethod = "GET")
    @GetMapping("/viewItem")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String" ,name = "key", paramType = "query" ,
                    value = "上传返回的文件ID",required = true,defaultValue = "3")
    })
    public ApiResponse<String> querySpotInspectionRecordByPlanId(@RequestParam("key") String key, HttpServletRequest request){
        return apiResponse(ossService.getOSSUrl(key));
    }

    /**
     * 批量查询OSS图片相关信息
     * @param fileUrlBatchQueryReq
     * @return
     */
    @ApiOperation(value = "图片批量查询接口-WEB",notes = "图片批量查询接口-WEB",httpMethod = "POST")
    @PostMapping("/batchQueryImageInfo")
    @ApiImplicitParam(dataType = "FileUrlBatchQueryReq" ,name = "fileUrlBatchQueryReq", paramType = "VO" ,
            value = "图片批量查询接口-WEB",required = true)
    public ApiResponse<List<ImageUploadResp>> batchQueryImageInfo(@RequestBody FileUrlBatchQueryReq fileUrlBatchQueryReq){

        return apiResponse(fileService.batchQueryImageInfo(fileUrlBatchQueryReq.getFileIdList()));
    }

    /**
     * 图片上传接口 会返回图片相关信息
     * @param file
     * @return
     */
    @ApiOperation(value = "图片上传接口 会返回图片相关信息",notes = "图片上传接口 会返回图片相关信息",httpMethod = "POST")
    @PostMapping("/uploadImageToOSS")
    @ApiImplicitParam(dataType = "MultipartFile" ,name = "file", paramType = "Object" ,
            value = "图片",required = true)
    public ApiResponse<ImageUploadResp> uploadImageToOSS(@RequestParam(value = "file", required = true) MultipartFile file, String type){
        return apiResponse(fileService.imageToOSS(file,type));
    }

    /**
     * OSS文件批量删除
     * @param batchDeleteOSSItemReq
     */
    @ApiOperation(value = "批量删除接口",notes = "批量删除接口",httpMethod = "POST")
    @PostMapping("/batchDeleteItems")
    @ApiImplicitParam(dataType = "BatchDeleteOSSItemReq" ,name = "batchDeleteOSSItemReq", paramType = "Object" ,
            value = "图片ID集合",required = true)
    public ApiResponse batchDeleteItems(@RequestBody BatchDeleteOSSItemReq batchDeleteOSSItemReq){
        fileService.batchDeleteItems(batchDeleteOSSItemReq);
        return apiResponse();
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
    public ApiResponse<FileUploadResp> uploadToOSS(@RequestParam(value = "file", required = true) MultipartFile file, String type){
        return apiResponse(fileService.uploadFileToOSS(file,type));
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

    /**
     * 文件按照当前日期
     * @return
     */
    private static String getCurrentDateFilePath() {
        String currentDateFilePath = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        currentDateFilePath = calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/"
                + calendar.get(Calendar.DAY_OF_MONTH) + "/";
        return currentDateFilePath;
    }


    public static void main(String[] args) {
        try {
        InputStream is = new URL("http://oss.factoryrp.ridewhale.cn/cd4e7cbe22ccbfded476a306bdfc7312?Expires=1520530929&OSSAccessKeyId=LTAI15LVPdJNL31Y&Signature=Zbj0YEAqBwqbBmF7GeZYGtlF4u8%3D").openStream();

        BufferedImage sourceImg = null;

            sourceImg = ImageIO.read(is);
            System.out.println(sourceImg.getWidth());
            System.out.println(sourceImg.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
        }
//           // System.out.println(String.format("%.1f",picture.length()/1024.0));

    }

}
