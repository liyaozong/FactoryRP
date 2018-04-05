package tech.yozo.factoryrp.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenxiang
 * @create 2018-04-05 下午10:42
 **/
@Data
@ApiModel(value = "首页故障统计数量")
public class IndexTroubleRecordCountVo implements Serializable{
    @ApiModelProperty(value = "设备总数")
    private Integer countDevice;
    @ApiModelProperty(value = "故障台数")
    private Integer countTrouble;
    @ApiModelProperty(value = "正在维修的台数")
    private Integer countRepairing;
    private List<IndexSimpleTroubleRecord> troubleRecords;
}
