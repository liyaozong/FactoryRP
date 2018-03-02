package tech.yozo.factoryrp.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.req.AddMaintainPlanReq;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2018-03-03 上午12:08
 **/
@Data
public class MaintainPlanDetailVo extends AddMaintainPlanReq implements Serializable{
    @ApiModelProperty(value = "主键",example = "2")
    private Long id;
}
