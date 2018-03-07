package tech.yozo.factoryrp.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * OSS文件上传Resp
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/7
 * @description
 */
@Data
@ApiModel(value = "OSS文件上传返回")
public class OSSUploadResp implements Serializable {


    /**
     * 文件上传返回的key
     */
    @ApiModelProperty(value = "文件上传返回的key")
    private String key;

    /**
     * 文件上传访问地址
     */
    @ApiModelProperty(value = "文件上传访问地址")
    private String url;
}
