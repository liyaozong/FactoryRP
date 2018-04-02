package tech.yozo.factoryrp.entity;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 巡检计划
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/25
 * @description
 */
@Table(name = "spot_inspection_plan")
@Entity
@Data
public class SpotInspectionPlan extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 5773873620690157928L;


    /**
     * 巡检项目名称
     */
    @ApiModelProperty(value = "巡检项目名称",notes ="巡检项目名称" )
    @Column(name = "name",length = 20)
    private String name;

    /**
     * 下次执行时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "下次执行时间",notes ="下次执行时间" )
    @Column(name = "next_execute_time")
    private Date nextExecuteTime;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "上次执行时间",notes ="上次执行时间" )
    @Column(name = "last_execute_time")
    private Date lastExecuteTime;

    /**
     * 计划状态 1启用 2停用 3编辑中
     */
    @ApiModelProperty(value = "计划状态 1启用 2停用 3编辑中",notes ="计划状态 1启用 2停用 3编辑中" )
    @Column(name = "plan_status",length = 20)
    private Integer planStatus;

    /**
     * 所在部门
     */
    @ApiModelProperty(value = "所在部门",notes ="所在部门" )
    @Column(name = "department",length = 20)
    private Long department;

    /**
     * 所在范围
     */
    @ApiModelProperty(value = "所在范围",notes ="所在范围" )
    @Column(name = "spot_inspection_range",length = 50)
    private String spotInspectionRange;

    /**
     * 循环周期类型 周，年，天，小时，月等
     */
    @ApiModelProperty(value = "循环周期类型",notes ="循环周期类型" )
    @Column(name = "recycle_period_type",length = 20)
    private String recyclePeriodType;


    /**
     * 循环周期
     */
    @ApiModelProperty(value = "循环周期",notes ="循环周期" )
    @Column(name = "recycle_period",length = 20)
    private Integer recyclePeriod;

    /**
     * 截止时间 如果为空表示计划长期有效
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "截止时间--如果为空表示计划长期有效",notes ="截止时间--如果为空表示计划长期有效" )
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 执行者
     */
    @ApiModelProperty(value = "执行者集合",notes ="执行者集合" )
    @Column(name = "executors",length = 50)
    private String executors;

    /**
     * 巡检计划等级
     */
    @ApiModelProperty(value = "巡检计划等级,1,2,3等",notes ="巡检计划等级,1,2,3等" )
    @Column(name = "spot_inspection_plan_level",length = 10)
    private Integer spotInspectionPlanLevel;

}
