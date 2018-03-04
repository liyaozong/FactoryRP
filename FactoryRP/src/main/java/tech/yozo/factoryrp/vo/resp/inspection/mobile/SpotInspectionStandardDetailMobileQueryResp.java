package tech.yozo.factoryrp.vo.resp.inspection.mobile;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.resp.inspection.SpotInspectionStandardItemsQueryResp;

import java.io.Serializable;
import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/4
 * @description
 */
@ApiModel
@Data
public class SpotInspectionStandardDetailMobileQueryResp implements Serializable {

    /**
     * 巡检项目集合
     */
    @ApiModelProperty(value = "巡检项目集合",notes ="巡检项目集合" )
    private List<SpotInspectionStandardItemsQueryResp> spotInspectionItems;

}
