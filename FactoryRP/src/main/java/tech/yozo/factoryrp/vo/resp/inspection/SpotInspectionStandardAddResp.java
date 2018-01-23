package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 点检标准新增返回封装类
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/23
 * @description
 */
@ApiModel
@Data
public class SpotInspectionStandardAddResp implements Serializable {


    /**
     * 点检标准ID
     */
    @ApiModelProperty(value = "点检标准ID",required = true,notes = "点检标准ID",example = "1")
    private Long id;


    /**
     * 点检标准名称
     */
    @ApiModelProperty(value = "点检标准名称",required = true,notes = "点检标准名称",example = "1")
    private String name;

}
