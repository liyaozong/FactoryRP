package tech.yozo.factoryrp.vo.resp.inspection;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 巡检记录分页查询Resp
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/10
 * @description
 */
@ApiModel
@Data
public class SpotInspectionRecordPageQueryResp implements Serializable{

    /**
     * 巡检记录ID
     */
    @ApiModelProperty(value = "巡检记录ID",notes ="巡检记录ID" )
    private Long recordId;

    /**
     * 巡检项目ID
     */
    @ApiModelProperty(value = "巡检项目ID",notes ="巡检项目ID" )
    private Long planId;

    /**
     * 巡检计划名称
     */
    @ApiModelProperty(value = "巡检计划名称",notes ="巡检计划名称" )
    private String planName;


    /**
     * 执行时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "执行时间",notes ="执行时间" )
    private String executeTime;

    /**
     * 计划时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "计划时间",notes ="下次执行时间" )
    private String planTime;

    /**
     * 所在部门
     */
    /*@ApiModelProperty(value = "所在部门",notes ="所在部门" )
    private String departmentName;*/

    /**
     * 所在部门ID
     */
    @ApiModelProperty(value = "所在部门ID",notes ="所在部门ID" )
    private Long departmentId;

    /**
     * 上次执行时间
     */
    @ApiModelProperty(value = "上次执行时间",notes ="上次执行时间" )
    private String lastExecuteTime;


    /**
     * 延误情况
     */
    @ApiModelProperty(value = "延误情况",notes ="延误情况" )
    private String delayDesc;

    /**
     * 执行人ID
     */
    @ApiModelProperty(value = "执行人ID",notes ="执行人ID" )
    private Long executor;


    /**
     * 设备异常数
     */
    @ApiModelProperty(value = "设备异常数",notes ="设备异常数" )
    private Integer abnormalDeviceCount;

    /**
     * 漏检数
     */
    @ApiModelProperty(value = "漏检数",notes ="漏检数" )
    private Integer missCount;

    /**
     * 周期
     */
    @ApiModelProperty(value = "周期",notes ="周期" )
    private String recyclePeriod;

    /**
     * 异常处理情况
     */
    @ApiModelProperty(value = "异常处理情况",notes ="异常处理情况" )
    private String abnormalHandelDesc;

}
