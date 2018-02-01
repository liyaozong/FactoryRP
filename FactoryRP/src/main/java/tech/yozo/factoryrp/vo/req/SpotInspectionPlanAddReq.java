package tech.yozo.factoryrp.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.ApiRequest;
import tech.yozo.factoryrp.vo.validation.NotEmpty;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 巡检计划新增请求VO
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/25
 * @description
 */
@ApiModel
@Data
public class SpotInspectionPlanAddReq extends ApiRequest implements Serializable {

    private static final long serialVersionUID = 8472950818740060265L;

    /**
     * 巡检计划名称
     */
    @ApiModelProperty(value = "巡检计划名称",notes ="巡检计划名称",example = "一级巡检")
    @NotEmpty(message = "巡检计划名称不能为空")
    private String name;


    /**
     * 下次执行时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "下次执行时间",notes ="下次执行时间" ,example = "2018-01-25 : 10:53:50")
    private Date nextExecuteTime;


    /**
     * 执行者
     */
    @ApiModelProperty(value = "执行者集合",notes ="执行者集合" ,example = "[1,2,3]")
    private List<String> executors;


    /**
     * 计划状态 1启用 2停用 3编辑中
     */
    @ApiModelProperty(value = "计划状态 1启用 2停用 3编辑中",notes ="计划状态 1启用 2停用 3编辑中",example = "1")
    private Integer planStatus;


    /**
     * 所在部门
     */
    @ApiModelProperty(value = "所在部门",notes ="所在部门",example = "研发")
    private Long department;


    /**
     * 所在范围
     */
    @ApiModelProperty(value = "所在范围",notes ="所在范围",example = "厂区")
    private String range;


    /**
     * 循环周期
     */
    @ApiModelProperty(value = "循环周期，周，年，天，小时，月等，根据后端枚举取值",notes ="循环周期，周，年，天，小时，月等",example = "1")
    private Integer recyclePeriod;


    /**
     * 截止时间 如果为空表示计划长期有效
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "截止时间",notes ="截止时间" ,example = "2018-01-25 : 10:53:50")
    private Date endTime;

    @ApiModelProperty(value = "点检计划-设备集合",notes ="点检计划-设备集合", example = "list")
    List<SpotInspectionPlanDeviceReq> list;
}
