package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 点检标准返回VO
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/23
 * @description
 */
@ApiModel
@Data
public class SpotInspectionStandardQueryResp implements Serializable {

    private static final long serialVersionUID = -648132552572844153L;

    @ApiModelProperty(value = "点检标准ID",required = true,notes = "点检标准ID",example = "1")
    private Long id;


    @ApiModelProperty(value = "巡检标准名称",notes ="巡检标准名称" )
    private String name;


    /**
     * 适用设备名称
     */
    @ApiModelProperty(value = "适用设备名称",notes ="适用设备名称" )
    private String relateDeviceName;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注",notes ="备注" )
    private String remark;


    /**
     * 巡检要求
     */
    @ApiModelProperty(value = "巡检要求",notes ="巡检要求" )
    private String requirement ;

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
}
