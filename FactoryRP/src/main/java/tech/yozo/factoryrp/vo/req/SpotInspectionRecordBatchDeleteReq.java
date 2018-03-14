package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 巡检计划批量删除请求对象
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/14
 * @description
 */
@ApiModel
@Data
public class SpotInspectionRecordBatchDeleteReq implements Serializable {


    /**
     * 巡检计划ID集合
     */
    @ApiModelProperty(value = "巡检计划ID集合",notes ="巡检计划ID集合" )
    private List<Long> recordIdList;

}
