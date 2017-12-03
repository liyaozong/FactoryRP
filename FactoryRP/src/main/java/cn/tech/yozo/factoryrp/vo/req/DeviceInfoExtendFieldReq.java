package cn.tech.yozo.factoryrp.vo.req;

import cn.tech.yozo.factoryrp.vo.base.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2017-12-02 下午4:06
 **/
@Data
public class DeviceInfoExtendFieldReq extends BaseRequest implements Serializable{

    @ApiModelProperty(value = "自定义字段1",notes ="自定义字段1" )
    private String extendFieldOne;

    @ApiModelProperty(value = "自定义字段2",notes ="自定义字段2" )
    private String extendFieldTwo;

    @ApiModelProperty(value = "自定义字段3",notes ="自定义字段3" )
    private String extendFieldThree;

    @ApiModelProperty(value = "自定义字段4",notes ="自定义字段4" )
    private String extendFieldFour;

    @ApiModelProperty(value = "自定义字段5",notes ="自定义字段5" )
    private String extendFieldFive;

    @ApiModelProperty(value = "自定义字段6",notes ="自定义字段6" )
    private String extendFieldSix;

    @ApiModelProperty(value = "自定义字段7",notes ="自定义字段7" )
    private String extendFieldSeven;

    @ApiModelProperty(value = "自定义字段8",notes ="自定义字段8" )
    private String extendFieldEight;

    @ApiModelProperty(value = "自定义字段9",notes ="自定义字段9" )
    private String extendFieldNine;

    @ApiModelProperty(value = "自定义字段10",notes ="自定义字段10" )
    private String extendFieldTen;

    @ApiModelProperty(value = "自定义数字",notes ="自定义数字" )
    private String extendNumberField;

    @ApiModelProperty(value = "自定义日期",notes ="自定义日期" )
    private String extendDateField;
}
