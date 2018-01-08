package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.BaseRequest;

import java.io.Serializable;
import java.util.List;

/**
 * 流程新增请求Vo
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
@ApiModel
@Data
public class DeviceProcessAddReq extends BaseRequest implements Serializable {


    /**
     * 流程名称
     */
    @ApiModelProperty(value = "流程名称",notes = "流程名称",example = "测试流程1")
    private String processName;


    /**
     * 流程类型
     */
    @ApiModelProperty(value = "流程类型,给列表值",notes = "流程类型,给列表值",example = "1")
    private Long processType;


    /**
     * 流程阶段
     */
    @ApiModelProperty(value = "流程阶段,给列表值",notes = "流程阶段,给列表值",example = "1")
    private Long processStage;

    /**
     * 触发条件类型 对应设备类型 金额上限 部门等
     */
    @ApiModelProperty(value = "触发条件类型,对应设备类型(1),金额上限(2),部门(3)等",notes = "触发条件类型,对应设备类型(1),金额上限(2),部门(3)等",example = "1")
    private Long triggerConditionType;


    /**
     * 条件详情
     */
    @ApiModelProperty(value = "条件详情,给列表值",notes = "条件详情,给列表值",example = "1")
    private Long triggerCondition;

    /**
     * 流程备注
     */
    @ApiModelProperty(value = "流程备注，可有可无",notes = "流程备注，可有可无",example = "1")
    private String processRemark;


    /**
     * 流程细节集合
     */
    @ApiModelProperty(value = "流程细节集合",notes = "流程细节集合",example = "List")
    private List<DeviceProcessDetailAddReq> list;

}
