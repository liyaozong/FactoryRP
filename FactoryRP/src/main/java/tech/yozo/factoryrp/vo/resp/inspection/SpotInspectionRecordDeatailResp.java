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
     * 名称
     */
    @ApiModelProperty(value = "名称",notes ="名称",example = "1")
    private String planName;

}
