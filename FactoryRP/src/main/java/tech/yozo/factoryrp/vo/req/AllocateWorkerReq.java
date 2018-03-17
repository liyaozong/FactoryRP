package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2018-03-17 下午10:52
 **/
@Data
@ApiModel(value = "维修派工参数")
public class AllocateWorkerReq implements Serializable{
    @ApiModelProperty(value = "故障信息主键")
    private Long troubleRecordId;
    @ApiModelProperty(value = "维修人员主键")
    private Long repairUserId;
    @ApiModelProperty(value = "维修人员姓名")
    private String repairUserName;
}
