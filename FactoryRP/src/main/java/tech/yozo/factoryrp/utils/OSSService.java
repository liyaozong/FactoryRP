package tech.yozo.factoryrp.utils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
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

    static {
        client = new OSSClient("", "",
                "");
    }

    /**
     * 此方法只针对贷安啦用户的资料文件上传到阿时云的oss中</br>
     * 需要将文件类型、文件流、用户id号设置到ossObjectVo对象中
     *
     * @author cns 20160808
     * @param ossObjectVo
     *            上传到oss的对象
     * @return
     */
    /* public static Stirng uploadFile(OSSObjectVo ossObjectVo) {
       UploadOSSResult result = new UploadOSSResult();
        result.setResult(true);
        result.setMessage("上传成功");
		*//* 判断上传的对象必要属性不能为空 *//*
        if (ossObjectVo == null) {
            result.setResult(false);
            result.setMessage("上传的对象不能为空");
            return result;
        }
        if (ossObjectVo.getFileType() == null || ossObjectVo.getFileType().equals("")) {
            result.setResult(false);
            result.setMessage("上传的文件类型不能为空");
            return result;
        }
        if (ossObjectVo.getInputStream() == null) {
            result.setResult(false);
            result.setMessage("上传的文件流不能为空");
            return result;
        }
        if (ossObjectVo.getUserId() == null) {
            result.setResult(false);
            result.setMessage("用户的id号不能为空");
            return result;
        }

        ObjectMetadata objectMeta = new ObjectMetadata();
        try {
            objectMeta.setContentLength(ossObjectVo.getInputStream().available());
        } catch (IOException e1) {
            logger.error("获取文件长度出错：" + e1.getMessage());
            result.setResult(false);
            result.setMessage("获取文件长度出错");
            return result;
        }
        // 判断上传类型，多的可根据需求来判定
        if (ossObjectVo.getFileType().equals("xml") || ossObjectVo.getFileType().equals("txt")) {
            objectMeta.setContentType("text/xml");
        } else if (ossObjectVo.getFileType().equals("jpg")) {
            objectMeta.setContentType("image/jpeg");
        } else if (ossObjectVo.getFileType().equals("png")) {
            objectMeta.setContentType("image/png");
        } else if (ossObjectVo.getFileType().equals("pdf")) {
            objectMeta.setContentType("application/pdf");
        } else if(ossObjectVo.getFileType().equals("xls") || ossObjectVo.getFileType().equals("xlsx") ){
            objectMeta.setContentType("application/vnd.ms-excel");
        }else if (ossObjectVo.getFileType().equals("html")) {
            objectMeta.setContentType("text/html");
        }else{
            result.setResult(false);
            result.setMessage("目前上传文件只支持xml, txt, pdf, jpg, png, xls, xlsx, html这几种类型");
            return result;
        }

        try {
            //client.putObject(ResourceConstants.BUCKET_NAME, result.getObjectkeyName(), ossObjectVo.getInputStream());
        } catch (Exception e) {
            logger.error("上传到阿里云oss的文件出错：" + e.getMessage());
            e.printStackTrace();
            //result.setResult(false);
            //result.setMessage("上传文件失败");
            return result;
        }
        //return result;


        return null;
    }
*/
    /**
     * 根据保存到阿里云上文件路径，获取有效的访问url地址
     *
     * @param ossName
     *            保存到阿里云上文件路径名
     * @return
     */
    public static String getOSSUrl(String ossName) {
        // 设置URL过期时间为1小时
        Date expiration = new Date(new Date().getTime() + 3600 * 1000);
        // 生成URL
        //URL url = client.generatePresignedUrl(ResourceConstants.BUCKET_NAME, ossName, expiration);
        //return url.toString();

        return null;
    }

    /**
     * 根据保存到阿里云上文件路径与时间，获取有效的访问url地址
     *
     * @author cns 20161021
     * @param ossName
     *            保存到阿里云上文件路径名
     * @return
     */
    public static String getOSSUrl(String ossName,long time) {
        // 设置URL过期时间为1小时
        Date expiration = new Date(time);
        // 生成URL
        //URL url = client.generatePresignedUrl(ResourceConstants.BUCKET_NAME, ossName, expiration);
        //return url.toString();

        return null;
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
