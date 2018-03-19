package tech.yozo.factoryrp.vo.resp;

import lombok.Data;

@Data
public class UploadImageResp {
    /**
     * 文件上传返回的key
     */
//    @ApiModelProperty(value = "文件上传返回的key")
    private String key;

    /**
     * 文件上传访问地址
     */
//    @ApiModelProperty(value = "文件上传访问地址")
    private String url;
    /**
     * 图片宽度
     */
//    @ApiModelProperty(value = "图片宽度")
    private Integer width;

    /**
     * 图片高度
     */
//    @ApiModelProperty(value = "图片高度")
    private Integer height;

    /**
     * 图片大小
     */
//    @ApiModelProperty(value = "图片大小")
    private Long size;
}
