package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 点检标准分页查询抱回包装对象
 * @author created by Singer email:313402703@qq.com
 * @time 2018/2/2
 * @description
 */
@ApiModel
@Data
public class SpotInspectionStandardQueryWarpResp implements Serializable {

    private static final long serialVersionUID = -8017220816522763268L;

    /**
     * 当前页码
     */
    @ApiModelProperty(value = "当前页码",example = "1")
    private Integer currentPage;

    /**
     * 每页显示记录数
     */
    @ApiModelProperty(value = "每页显示记录数",example = "10")
    private Integer itemsPerPage;

    /**
     * 总数量
     */
    @ApiModelProperty(value = "总数量",example = "10")
    private Long totalCount;


    @ApiModelProperty(value = "返回内容",example = "ArrayList",dataType = "List")
    private List<SpotInspectionStandardQueryResp> list;

}
