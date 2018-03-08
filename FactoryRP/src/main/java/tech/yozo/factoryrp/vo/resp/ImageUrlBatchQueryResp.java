package tech.yozo.factoryrp.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 图片批量查询返回对象
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/9
 * @description
 */
@ApiModel
@Data
public class ImageUrlBatchQueryResp implements Serializable {


    /**
     * 图片批量查询返回集合
     */
    @ApiModelProperty(value = "图片批量查询返回集合",required = true,notes = "图片批量查询返回集合",example = "List")
    private List<ImageUploadResp> imageList;


}
