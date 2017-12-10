package tech.yozo.factoryrp.vo.resp.sparepars;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/9
 * @description
 */
@ApiModel
@Data
public class DeviceSparesMobileResp implements Serializable {

    @ApiModelProperty(value = "备件id",notes ="备件id" )
    private Long id;

    @ApiModelProperty(value = "备件名称",notes ="备件名称" )
    private String name;

    @ApiModelProperty(value = "备件编号",notes ="备件编号" )
    private String code;


    @ApiModelProperty(value = "规格型号",notes ="规格型号" )
    private String specificationsAndodels;



}
