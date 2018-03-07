package tech.yozo.factoryrp.vo.resp.inspection;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 巡检计划详情包装VO
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/4
 * @description
 */
@ApiModel
@Data
public class SpotInspectionPlanDetailWarpResp implements Serializable {


    /**
     * 巡检项目名称
     */
    @ApiModelProperty(value = "巡检项目名称",notes ="巡检项目名称" )
    private String name;

    /**
     * 下次执行时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "下次执行时间",notes ="下次执行时间" )
    private Date nextExecuteTime;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "上次执行时间",notes ="上次执行时间" )
    private Date lastExecuteTime;

    /**
     * 计划状态 1启用 2停用 3编辑中
     */
    @ApiModelProperty(value = "计划状态 1启用 2停用 3编辑中",notes ="计划状态 1启用 2停用 3编辑中" )
    private Integer planStatus;

    /**
     * 所在部门名称
     */
    @ApiModelProperty(value = "所在部门名称",notes ="所在部门名称" )
    private String departmentName;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID",notes ="部门ID" )
    private Long department;

    /**
     * 所在范围
     */
    @ApiModelProperty(value = "所在范围",notes ="所在范围" )
    private String spotInspectionRange;

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
     * 截止时间 如果为空表示计划长期有效
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "截止时间--如果为空表示计划长期有效",notes ="截止时间--如果为空表示计划长期有效" )
    private Date endTime;

    @ApiModelProperty(value = "异常处理情况",notes ="异常处理情况" )
    private String abnormalHandleDesc;

    /**
     * 执行者ID集合
     */
    @ApiModelProperty(value = "执行者ID集合",notes ="执行者ID集合" )
    private List<Long> executors;


    /**
     * 是否在在执行时间 1是2不是
     */
    @ApiModelProperty(value = "是否在在执行时间 1是2不是",notes ="是否在在执行时间 1是2不是" )
    private Integer inTime;

    /**
     * 巡检计划设备关联集合
     */
    @ApiModelProperty(value = "巡检计划设备关联集合",notes ="巡检计划设备关联集合" )
    private List<SpotInspectionPlanDeviceInfoResp>  infoList;

}
