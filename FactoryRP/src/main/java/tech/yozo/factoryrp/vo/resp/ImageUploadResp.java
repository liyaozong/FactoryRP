package tech.yozo.factoryrp.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件上传对象返回
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/9
 * @description
 */
@Data
@ApiModel(value = "OSS图片上传返回")
public class ImageUploadResp extends FileUploadResp implements Serializable {


    /**
     * 图片宽度
     */
    @ApiModelProperty(value = "图片宽度")
    private Integer width;

    /**
     * 图片高度
     */
    @ApiModelProperty(value = "图片高度")
    private Integer height;

    /**
     * 图片大小
     */
    @ApiModelProperty(value = "图片大小")
    private Long size;

}
