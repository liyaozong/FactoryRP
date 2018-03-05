package tech.yozo.factoryrp.vo.resp.inspection;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 巡检计划查询返回VO
 * @author created by Singer email:313402703@qq.com
 * @time 2018/2/1
 * @description
 */
@ApiModel
@Data
public class SpotInspectionPlanQueryResp implements Serializable {


    /**
     * 巡检项目ID
     */
    @ApiModelProperty(value = "巡检项目ID",notes ="巡检项目ID" )
    private Long id;

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
    private String nextExecuteTime;


    /**
     * 计划状态 1启用 2停用 3编辑中
     */
    @ApiModelProperty(value = "计划状态 1启用 2停用 3编辑中",notes ="计划状态 1启用 2停用 3编辑中" )
    private Integer planStatus;

    /**
     * 状态 是否过期 1未过期 2过期
     */
    @ApiModelProperty(value = "状态 是否过期 1未过期 2过期",notes ="状态 是否过期 1未过期 2过期" )
    private Integer expiredStatus;

    /**
     * 所在部门
     */
    @ApiModelProperty(value = "所在部门",notes ="所在部门" )
    private String departmentName;


    /**
     * 上次执行时间
     */
    @ApiModelProperty(value = "上次执行时间",notes ="上次执行时间" )
    private String lastExecuteTime;


    /**
     * 执行人ID
     */
    @ApiModelProperty(value = "执行人ID",notes ="执行人ID" )
    private List<Long> executors;


    /**
     * 设备数
     */
    @ApiModelProperty(value = "设备数",notes ="设备数" )
    private Integer deviceCount;

    /**
     * 周期
     */
    @ApiModelProperty(value = "周期",notes ="周期" )
    private String recyclePeriod;
}
