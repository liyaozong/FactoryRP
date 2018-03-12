package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 巡检记录查询Resp
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/5
 * @description
 */
@ApiModel
@Data
public class SpotInspectionRecordResp implements Serializable {

    /**
     * 记录ID
     */
    @ApiModelProperty(value = "记录ID",notes ="记录ID",example = "1")
    private Long recordId;


    /**
     * 巡检计划名称
     */
    @ApiModelProperty(value = "巡检计划名称",notes ="巡检计划名称",example = "1")
    private String planName;


    /**
     * 巡检计划ID
     */
    @ApiModelProperty(value = "巡检计划ID",notes ="巡检计划ID",example = "1")
    private Long planId;

    /**
     * 巡检时间
     */
    @ApiModelProperty(value = "巡检时间",notes ="巡检时间",example = "1")
    private Date executeTime;


    /**
     * 执行者
     */
    @ApiModelProperty(value = "执行者",notes ="执行者",example = "1")
    private String executor;


    /**
     * 计划时间
     */
    @ApiModelProperty(value = "计划时间",notes ="计划时间",example = "1")
    private String planTime;


    /**
     * 循环周期类型 周，年，天，小时，月等
     */
    @ApiModelProperty(value = "循环周期类型",notes ="循环周期类型" )
    private String recyclePeriodType;


    /**
     * 循环周期
     */
    @ApiModelProperty(value = "循环周期",notes ="循环周期" )
    private Integer recyclePeriod;


    /**
     * 设备异常数
     */
    @ApiModelProperty(value = "设备异常数",notes ="设备异常数" )
    private Integer abnormalDeviceCount;


    /**
     * 设备漏检数
     */
    @ApiModelProperty(value = "设备漏检数",notes ="设备漏检数" )
    private Integer missCount;


    /**
     * 延误天数
     */
    @ApiModelProperty(value = "延误天数",notes ="延误天数" )
    private Integer delayDayCount;

    /**
     * 异常处理情况描述
     */
    @ApiModelProperty(value = "异常处理情况描述",notes ="异常处理情况描述" )
    private String abnormalHandelDesc;
}
