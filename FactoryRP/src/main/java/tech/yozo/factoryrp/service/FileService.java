package tech.yozo.factoryrp.service;

import org.springframework.web.multipart.MultipartFile;
import tech.yozo.factoryrp.vo.req.BatchDeleteOSSItemReq;
import tech.yozo.factoryrp.vo.resp.FileUploadResp;
import tech.yozo.factoryrp.vo.resp.ImageUploadResp;

import java.util.List;

/**
 * 文件上传服务
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/9
 * @description
 */
public interface FileService {


    /**
     * 批量查询OSS图片相关信息
     * @param imageIdList
     * @return
     */
    List<ImageUploadResp> batchQueryImageInfo(List<String> imageIdList);

    /**
     * 上传图片到OSS
     * @param file
     * @param type
     * @return
     */
    ImageUploadResp imageToOSS(MultipartFile file, String type);

    /**
     * 文件上传到OSS
     * @param file
     * @param type
     * @return
     */
    FileUploadResp uploadFileToOSS(MultipartFile file, String type);

    /**
     * OSS文件批量删除
     * @param batchDeleteOSSItemReq
     */
    void batchDeleteItems(BatchDeleteOSSItemReq batchDeleteOSSItemReq);

}
