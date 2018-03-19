package tech.yozo.factoryrp.vo.req;

import lombok.Data;

@Data
public class AssignTroubleReq {
//    @ApiModelProperty(value = "故障信息主键")
    private Long troubleRecordId;
//    @ApiModelProperty(value = "维修人员主键")
    private Long repairUserId;
//    @ApiModelProperty(value = "维修人员姓名")
    private String repairUserName;
}
