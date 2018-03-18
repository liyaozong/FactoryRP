package tech.yozo.factoryrp.vo.req;

import lombok.Data;

@Data
public class AuditTroubleReq {
//    @ApiModelProperty(value = "故障信息主键")
    private Long troubleRecordId;

//    @ApiModelProperty(value = "审核结果，1：通过；2：拒绝")
    private Integer dealStatus;

//    @ApiModelProperty(value = "审核处理意见")
    private String dealSuggest;
}
