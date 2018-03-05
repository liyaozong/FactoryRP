package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2018-03-05 下午10:19
 **/
@Data
public class AuditWorkNumReq implements Serializable{
    @ApiModelProperty(value = "故障信息主键")
    private Long troubleRecordId;

    @ApiModelProperty(value = "审核结果，1：通过；2：拒绝")
    private Integer dealStatus;

    @ApiModelProperty(value = "审核处理意见")
    private String dealSuggest;
}
