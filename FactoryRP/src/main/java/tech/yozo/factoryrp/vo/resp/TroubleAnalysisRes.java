package tech.yozo.factoryrp.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.req.ImprovePlan;
import tech.yozo.factoryrp.vo.req.SolutionPlan;

import java.util.List;

/**
 * @author chenxiang
 * @create 2018-04-24 下午4:28
 **/
@ApiModel("故障分析返回参数")
@Data
public class TroubleAnalysisRes {
    @ApiModelProperty(value = "主键,新增的时候为空，修改的时候不能为空",example = "1")
    private Long id;
    @ApiModelProperty(value = "故障主键",example = "1")
    private Long troubleRecordId;
    @ApiModelProperty(value = "主要检修内容")
    private String maintainRemark;
    @ApiModelProperty(value = "检修原因")
    private String maintainReason;
    @ApiModelProperty(value = "备件材料更换情况")
    private String replaceSpare;
    @ApiModelProperty(value = "故障造成的成本损失")
    private String repairCost;
    @ApiModelProperty(value = "备件材料费用")
    private String spareCost;
    @ApiModelProperty(value = "检修工时费用")
    private String maintainCost;
    @ApiModelProperty(value = "维修总成本")
    private String totalCost;
    @ApiModelProperty(value = "外委费用，问题卡独有字段")
    private String outRepairCost;
    @ApiModelProperty(value = "报告人，A3报告独有字段")
    private String reportUser;
    @ApiModelProperty(value = "报告时间，A3报告独有字段")
    private String reportTime;
    @ApiModelProperty(value = "A3报告：问题描述；问题卡：现象描述")
    private String problemDesc;
    @ApiModelProperty(value = "改善计划，A3报告独有字段")
    private List<ImprovePlan> improvePlans;
    @ApiModelProperty(value = "目标设定，A3报告独有字段")
    private String target;
    @ApiModelProperty(value = "A3报告：问题分解或问题分析；问题卡：根本原因分析")
    private String problemAanlysis;
    @ApiModelProperty(value = "A3报告：结果跟踪；问题卡：问题解决结果")
    private String result;
    @ApiModelProperty(value = "标准化和控制方法，A3报告独有字段")
    private String standard;
    @ApiModelProperty(value = "问题，问题卡独有字段")
    private String problem;
    @ApiModelProperty(value = "编号，问题卡独有字段")
    private String no;
    @ApiModelProperty(value = "车间或工厂，问题卡独有字段")
    private String workShop;
    @ApiModelProperty(value = "责任人，问题卡独有字段")
    private String dutyPeople;
    @ApiModelProperty(value = "审核人，问题卡独有字段")
    private String auditUser;
    @ApiModelProperty(value = "审核日期，问题卡独有字段")
    private String auditTime;
    @ApiModelProperty(value = "解决方案及行动计划，问题卡独有字段")
    private List<SolutionPlan> solutionPlans;
}
