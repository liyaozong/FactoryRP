package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.entity.SpotInspectionRecordDetail;

import java.io.Serializable;
import java.util.List;


/**
 * 巡检记录新增详情
 */
@ApiModel
@Data
public class SpotInspectionRecordAddResp implements Serializable {


    private static final long serialVersionUID = -5470994976638144559L;


    /**
     * 巡检记录主键
     */
    @ApiModelProperty(value = "巡检记录主键",notes ="巡检记录主键",example = "1")
    private Long id;



}
