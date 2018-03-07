package tech.yozo.factoryrp.service.Impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.EncryptUtils;
import tech.yozo.factoryrp.utils.UUIDSequenceWorker;
import tech.yozo.factoryrp.vo.resp.OSSUploadResp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

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

   /* static String endpoint = "oss.factoryrp.ridewhale.cn";
    // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
    static String accessKeyId = "LTAI15LVPdJNL31Y";
    static String accessKeySecret = "EXeeltCSSYBfroKmPfczqrLnWTRagh";
    static String bucketName = "factoryrp"; //bucketName*/


   /*static {
        client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }*/


    /**
     * 初始化OSS客户端
     */
   private void initClient(){
       if(CheckParam.isNull(client)){
           client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
       }
   }


    public static void main(String[] args) {
        // 创建OSSClient实例
        /*String filePath = "D:\\插件.jpg";
        // 上传文件 第二个参数是文件名称
        client.putObject(bucketName, "1232321322132", new File(filePath));
        // 关闭client
        //ossClient.shutdown();
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);

        URL url = client.generatePresignedUrl("factoryrp", "1232321322132", expiration);

        System.out.println(url.toString());*/

        String str = "123";
        System.out.println(EncryptUtils.MD5String(str));
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
        String filePath = "D:\\插件.jpg";
        // 上传文件 第二个参数是文件名称
        //client.putObject(bucketName, uploadItemName, new File(filePath));
        client.putObject(bucketName, uploadItemName, file);
        // 关闭client
        client.shutdown();
        return uploadItemName;
    }

    /**
     * 上传文件到OSS
     * @param itemName
     * @param inputStream
     * @return
     */
    public OSSUploadResp toOSS(String itemName, FileInputStream inputStream){
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

        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        // 生成URL
        URL url = client.generatePresignedUrl(bucketName, uploadItemName, expiration);

        OSSUploadResp resp = new OSSUploadResp();
        resp.setKey(uploadItemName);
        resp.setUrl(String.valueOf(url));

        // 关闭client
        client.shutdown();
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

    /**
     * 通过IO读取文件,尝试转换为Image,并获取image的的width、height,来判断当前文件是否是图片
     *
     *            待测试的inputFile
     * @return true:文件是图片;false:(1)文件不存在 (2)文件不是图片 (3)文件是图片,但获取不到其高度和宽度
     */
    /*public static boolean isImage(InputStream inputFile) throws IOException {
        boolean isImageFile = false;
        Image img = null;
        try {
            img = ImageIO.read(inputFile);
            if (img != null && img.getWidth(null) != -1 && img.getHeight(null) != -1) {
                isImageFile = true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取图片文件出错！" + e.getMessage());
            throw e;
        } finally {
            if (img != null) {
                img.flush();
                img = null;
            }
        }
        return isImageFile;
    }*/

    private static String randomFileName() {
        return UUID.randomUUID().toString();
    }

    private static String getCurrentDateFilePath() {
        String currentDateFilePath = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        currentDateFilePath = calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/"
                + calendar.get(Calendar.DAY_OF_MONTH) + "/";
        return currentDateFilePath;
    }


    /**
     * 拿到icon的Url
     * @param name
     * @return
     */
    public static String getIconUrl(String name){
        //一年过期
        Date expiration = new Date(new Date().getTime() + 3600 * 1000 * 24 * 365);
        //String ossName = ResourceConstants.APP_ICON_PRE+name;
        //return getOSSUrl(ossName, expiration.getTime());

        return null;
    }
}
