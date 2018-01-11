package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * @author chenxiang
 * @create 2017-12-18 下午10:54
 **/
@Data
public class ValidateRepairReq implements Serializable{

    @ApiModelProperty(value = "故障信息主键")
    private Long troubleRecordId;

    @ApiModelProperty(value = "故障是否修复，0：否；1：是")
    private Integer repaired;

    @ApiModelProperty(value = "是否重新修复，0：否；1：是")
    private Integer needRepair;

    @ApiModelProperty(value = "评价或建议")
    private String suggest;

    @ApiModelProperty(value = "星级")
    private Integer starLevel;
}
