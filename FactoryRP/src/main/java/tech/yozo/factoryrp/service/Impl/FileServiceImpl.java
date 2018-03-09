package tech.yozo.factoryrp.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.yozo.factoryrp.enums.FileTypeEnum;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.service.FileService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.BatchDeleteOSSItemReq;
import tech.yozo.factoryrp.vo.resp.FileUploadResp;
import tech.yozo.factoryrp.vo.resp.ImageUploadResp;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 文件上传服务
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/9
 * @description
 */
@Service
public class FileServiceImpl implements FileService {

    @Resource
    private OSSService ossService;

    private static Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);


    /**
     * OSS文件批量删除
     * @param batchDeleteOSSItemReq
     */
    public void batchDeleteItems(BatchDeleteOSSItemReq batchDeleteOSSItemReq){

        if(!CheckParam.isNull(batchDeleteOSSItemReq) &&
                !CheckParam.isNull(batchDeleteOSSItemReq.getItemList()) && batchDeleteOSSItemReq.getItemList().isEmpty()){

            List<String> itemList = batchDeleteOSSItemReq.getItemList();
            itemList = itemList.stream().distinct().collect(Collectors.toList());
            ossService.deleteBatchObect(itemList);
        }
    }

    /**
     * 解析图片的size
     * @param uploadResp
     * @return
     */
    private ImageUploadResp parseImageSize(FileUploadResp uploadResp){
        InputStream is = null;
        BufferedImage sourceImg = null;
        ImageUploadResp imageUploadResp= null;
        try {
            is = new URL(uploadResp.getUrl()).openStream();
            sourceImg = ImageIO.read(is);

            imageUploadResp = new ImageUploadResp();
            imageUploadResp.setHeight(sourceImg.getHeight());
            imageUploadResp.setWidth(sourceImg.getWidth());
            imageUploadResp.setUrl(uploadResp.getUrl());
            imageUploadResp.setKey(uploadResp.getKey());
            return imageUploadResp;
        } catch (Exception e) {
            logger.error(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>文件解析出现异常<<<<<<<<<<<<<<<<<<<<<<<<<<<<"+e.getMessage(),e);
            throw new BussinessException(ErrorCode.FILE_PARSE_EXCEPTION.getCode(),ErrorCode.FILE_PARSE_EXCEPTION.getMessage());
        }

    }


    /**
     * 批量查询OSS图片相关信息
     * @param imageIdList
     * @return
     */
    public List<ImageUploadResp> batchQueryImageInfo(List<String> imageIdList){

        if(!CheckParam.isNull(imageIdList) && !imageIdList.isEmpty()){

            List<ImageUploadResp> queryRespList = new ArrayList<>();
            imageIdList.stream().forEach(m1 -> {
                String ossUrl = ossService.getOSSUrl(m1);
                FileUploadResp uploadResp = new FileUploadResp();
                uploadResp.setKey(m1);
                uploadResp.setUrl(ossUrl);
                queryRespList.add(parseImageSize(uploadResp));
            });

            return queryRespList;
        }
            return null;
    }

    /**
     * 上传图片到OSS
     * @param file
     * @param type
     * @return
     */
    public ImageUploadResp imageToOSS(MultipartFile file, String type){
        FileUploadResp uploadResp = toOSS(file, type);
        return parseImageSize(uploadResp);
    }

    /**
     * 文件上传到OSS
     * @param file
     * @param type
     * @return
     */
    public FileUploadResp uploadFileToOSS(MultipartFile file, String type){
        return toOSS(file,type);
    }


    /**
     * 文件上传到OSS
     * @param file
     * @param type
     * @return
     */
    private FileUploadResp toOSS(MultipartFile file, String type){

        if(CheckParam.isNull(FileTypeEnum.getByCode(type))){
            throw new BussinessException(ErrorCode.UNKONW_IMAGE_TYPE_REEOR.getCode(),ErrorCode.UNKONW_IMAGE_TYPE_REEOR.getMessage());
        }

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

                FileUploadResp ossUploadResp = ossService.toOSS(name, fileInputStream,type);
                newFile.delete();
                return ossUploadResp;

            } catch (Exception e) {
                logger.error(">>>>>>>>>>>>>>>>>>上传文件出现异常<<<<<<<<<<<<<<<" + e.getMessage(), e);
                return null;
            }
        }
        throw new BussinessException(ErrorCode.REQUEST_FILE_NOT_EXIST.getCode(),
                ErrorCode.REQUEST_FILE_NOT_EXIST.getMessage());

    }

}
