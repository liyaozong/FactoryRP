package tech.yozo.factoryrp.vo.resp.device.trouble;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2017-12-18 下午9:39
 **/
@Data
public class WorkTimeVo implements Serializable{

    @ApiModelProperty(value = "维修人员主键")
    private Long repairUserId;

    @ApiModelProperty(value = "维修人员名称")
    private String repairUserName;

    @ApiModelProperty(value = "维修开始时间，格式yyyy-MM-dd HH:mm:ss")
    private String startTime;

    @ApiModelProperty(value = "维修结束时间，格式yyyy-MM-dd HH:mm:ss")
    private String endTime;

    @ApiModelProperty(value = "维修耗时")
    private Integer costHour;
}
