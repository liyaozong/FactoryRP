package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 巡检记录详情Resp
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/5
 * @description
 */
@ApiModel
@Data
public class SpotInspectionRecordDeatailResp implements Serializable {

    /**
     * 巡检项目名称
     */
    @ApiModelProperty(value = "巡检项目名称",notes ="巡检项目名称",example = "标准点检")
    private String inspectionItemName;

    /**
     * 巡检项目ID
     */
    @ApiModelProperty(value = "巡检项目ID",notes ="巡检项目ID",example = "1")
    private Long inspectionItemId;



}
