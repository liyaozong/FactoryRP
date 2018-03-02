package tech.yozo.factoryrp.vo.resp.inspection.mobile;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 手机端查询点检计划接口返回
 */
@ApiModel
@Data
public class SpotInspectionPlanResp implements Serializable {


    @ApiModelProperty(value = "巡检计划ID",required = true,notes = "点检标准ID",example = "1")
    private Long panId;


    @ApiModelProperty(value = "巡检计划ID",required = true,notes = "点检标准ID",example = "1")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date nextExecuteTime;

}
