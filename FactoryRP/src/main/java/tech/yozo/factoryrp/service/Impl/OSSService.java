package tech.yozo.factoryrp.service.Impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.EncryptUtils;
import tech.yozo.factoryrp.utils.UUIDSequenceWorker;
import tech.yozo.factoryrp.vo.resp.FileUploadResp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * OSS服务
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/6
 * @description
 */
@Service
public class OSSService {


    private static final Logger logger = LoggerFactory.getLogger(OSSService.class);

    private static OSSClient client;

    @Value("${oss.endpoint}")
    private String endpoint;

    @Value("${oss.accessKeyId}")
    private String accessKeyId;

    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${oss.bucketName}")
    private String bucketName;


   /*static {
        client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }*/

    /**
     * 批量删除object
     */
    public void deleteBatchObect(List<String> keys) {

        initClient();

        try {
            // 删除Objects
            DeleteObjectsResult deleteObjectsResult = client.deleteObjects(new DeleteObjectsRequest(bucketName).withKeys(keys));
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        } catch (Exception e) {
            logger.error(">>>>>>>>>>>>文件批量删除出现异常<<<<<<<<<<<"+e.getMessage(),e);
        }finally {
            client.shutdown();// 关闭client
        }
    }

    /**
     * 初始化OSS客户端
     */
   public void initClient(){
       if(CheckParam.isNull(client)){
           client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
       }
   }


    /**
     * 上传文件到OSS
     * @param itemName
     * @return
     */
    public String upToOSS(String itemName, File file){
        initClient();
        //上传的item名称
        String uploadItemName = EncryptUtils.MD5String(itemName);

        // 创建OSSClient实例 ObjectMetadata
        // 上传文件 第二个参数是文件名称
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.length());
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(getcontentType(itemName.substring(itemName.lastIndexOf("."))));
        objectMetadata.setContentDisposition("inline;filename=" + itemName);
        client.putObject(bucketName, uploadItemName, file,objectMetadata);
        // 关闭client
        client.shutdown();
        return uploadItemName;
    }

    /**
     * 上传文件到OSS
     * @param itemName
     * @param inputStream
     * @param dicName
     * @return
     */
    public FileUploadResp toOSS(String itemName, FileInputStream inputStream, String dicName){
        initClient();
        //上传的item名称
        String uploadItemName = EncryptUtils.MD5String(UUIDSequenceWorker.longUniqueSequenceId()+itemName);

        // 创建OSSClient实例 ObjectMetadata
        ObjectMetadata objectMetadata = new ObjectMetadata();
        try {
            objectMetadata.setContentLength(Long.parseLong(String.valueOf(inputStream.available())));
        } catch (IOException e) {
            logger.error(">>>>>>>>>>>>文件上传出现异常<<<<<<<<<<<"+e.getMessage(),e);
        }
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(getcontentType(itemName.substring(itemName.lastIndexOf("."))));
        objectMetadata.setContentDisposition("inline;filename=" + itemName);

        String filePathAndName= "/"+getCurrentDateFilePath()+"/"+uploadItemName;

        //client.putObject(bucketName, uploadItemName, inputStream,objectMetadata);
        client.putObject(bucketName, uploadItemName, inputStream,objectMetadata);

        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        // 生成URL
        URL url = client.generatePresignedUrl(bucketName, uploadItemName, expiration);

        FileUploadResp resp = new FileUploadResp();
        resp.setKey(uploadItemName);
        resp.setUrl(String.valueOf(url));

        // 关闭client
        //client.shutdown();
        return resp;
    }

    /**
     * 上传文件到OSS
     * @param itemName
     * @param inputStream
     * @return
     */
    public String upToOSS(String itemName, FileInputStream inputStream){
        initClient();
       //上传的item名称
       String uploadItemName = EncryptUtils.MD5String(UUIDSequenceWorker.uniqueSequenceId()+itemName);

        // 创建OSSClient实例 ObjectMetadata
        ObjectMetadata objectMetadata = new ObjectMetadata();
        try {
            objectMetadata.setContentLength(Long.parseLong(String.valueOf(inputStream.available())));
        } catch (IOException e) {
            logger.error(">>>>>>>>>>>>文件上传出现异常<<<<<<<<<<<"+e.getMessage(),e);
        }
        objectMetadata.setCacheControl("no-cache");
        objectMetadata.setHeader("Pragma", "no-cache");
        objectMetadata.setContentType(getcontentType(itemName.substring(itemName.lastIndexOf("."))));
        objectMetadata.setContentDisposition("inline;filename=" + itemName);
        client.putObject(bucketName, uploadItemName, inputStream,objectMetadata);
        // 关闭client
        client.shutdown();
        return uploadItemName;
    }

    /**
     * 根据保存到阿里云上文件路径，获取有效的访问url地址
     * 不会初始化阿里云OSSClient,需要调用方主动初始化Client
     *
     * @param ossName
     *            保存到阿里云上文件路径名
     * @return
     */
    public String getOSSUrlNotInitClient(String ossName) {
        // 设置URL过期时间为1小时
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        // 生成URL
        URL url = client.generatePresignedUrl(bucketName, ossName, expiration);
        return url.toString();
    }


    /**
     * 根据保存到阿里云上文件路径，获取有效的访问url地址
     *
     * @param ossName
     *            保存到阿里云上文件路径名
     * @return
     */
    public String getOSSUrl(String ossName) {
        initClient();
        // 设置URL过期时间为1小时
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        // 生成URL
        URL url = client.generatePresignedUrl(bucketName, ossName, expiration);
        return url.toString();
    }


    /**
     * 根据保存到阿里云上文件路径与时间，获取有效的访问url地址
     *
     * @author cns 20161021
     * @param ossName
     *            保存到阿里云上文件路径名
     * @return
     */
    public String getOSSUrl(String ossName,long time) {
        // 设置URL过期时间为1小时
        Date expiration = new Date(time);
        // 生成URL
        URL url = client.generatePresignedUrl(bucketName, ossName, expiration);
        return url.toString();
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }



    public static void main(String[] args) {

        String str = "123";
        System.out.println(EncryptUtils.MD5String(str));
    }

    /**
     * 文件夹按照日期来区分
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

}
