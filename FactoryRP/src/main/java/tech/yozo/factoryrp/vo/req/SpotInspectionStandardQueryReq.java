package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * 点检标准分页查询入参VO
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/23
 * @description
 */
@ApiModel
@Data
public class SpotInspectionStandardQueryReq implements Serializable{

    private static final long serialVersionUID = 7104236325515312557L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键",notes ="主键" )
    private Long id;

    /**
     * 巡检标准名称
     */
    @ApiModelProperty(value = "巡检标准名称",notes ="巡检标准名称" )
    private String name;

    /**
     * 适用设备类型
     */
    @ApiModelProperty(value = "适用设备类型",notes ="适用设备类型" )
    private Long relateDeviceType;

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

}
