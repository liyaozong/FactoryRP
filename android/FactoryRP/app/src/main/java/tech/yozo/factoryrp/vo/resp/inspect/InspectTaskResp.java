package tech.yozo.factoryrp.vo.resp.inspect;

import lombok.Data;

@Data
public class InspectTaskResp {
//    @ApiModelProperty(value = "巡检计划ID",required = true,notes = "点检标准ID",example = "1")
    private Long panId;

//    @ApiModelProperty(value = "巡检项目名称",required = true,notes = "巡检项目名称",example = "标准巡检")
    private String planName;

//    @ApiModelProperty(value = "计划开始时间",required = true,notes = "计划开始时间",example = "1")
    private String nextExecuteTime;
}
