package tech.yozo.factoryrp.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.ApiRequest;
import tech.yozo.factoryrp.vo.validation.IsDateStr;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 巡检计划编辑包装对象 对应前端修改巡检计划相关信息，删除巡检计划对应的设备，新增巡检计划对应的设备
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/6
 * @description
 */
@ApiModel
@Data
public class SpotInspectionPlanEditReq extends ApiRequest implements Serializable{


    /**
     * 巡检计划ID
     */
    @ApiModelProperty(value = "巡检计划ID",notes ="巡检计划ID" )
    private Long planId;

    /**
     * 巡检计划名称
     */
    @ApiModelProperty(value = "巡检计划名称",notes ="巡检计划名称" )
    private String name;

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
     * 执行者ID集合
     */
    @ApiModelProperty(value = "执行者ID集合",notes ="执行者ID集合" )
    private List<Long> executors;

    /**
     * 下次执行时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "下次执行时间",notes ="下次执行时间" )
    @IsDateStr(message = "必须为日期格式(yyyy-MM-dd HH:mm:ss)的字符串")
    private String nextExecuteTime;

    /**
     * 计划状态 1启用 2停用 3编辑中
     */
    @ApiModelProperty(value = "计划状态 1启用 2停用 3编辑中",notes ="计划状态 1启用 2停用 3编辑中" )
    private Integer planStatus;

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

    /**
     * 需要被删除的巡检计划设备ID->planDeviceId
     */
    @ApiModelProperty(value = "需要被删除的巡检计划设备ID->planDeviceId",notes ="需要被删除的巡检计划设备ID->planDeviceId" )
    private List<Long> planDeviceDeleteList;

    /**
     * 巡检计划设备新增集合
     */
    @ApiModelProperty(value = "巡检计划设备新增集合",notes ="巡检计划设备新增集合" )
    private List<SpotInspectionPlanDeviceReq> planDeviceList;

}
