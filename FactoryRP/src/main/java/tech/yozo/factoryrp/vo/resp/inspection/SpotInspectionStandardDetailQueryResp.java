package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 点检标准详情查询返回VO
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/23
 * @description
 */
@ApiModel
@Data
public class SpotInspectionStandardDetailQueryResp implements Serializable{


    private static final long serialVersionUID = 8490239817220523932L;


    /**
     * 设备详情
     */
    @ApiModelProperty(value = "设备详情",notes ="设备详情" )
    private Long deviceType;

    /**
     * 巡检标准名称
     */
    @ApiModelProperty(value = "巡检标准名称",notes ="巡检标准名称" )
    private String name;

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
     * 设备类型关联名称
     */
    @ApiModelProperty(value = "设备类型关联名称",notes ="设备类型关联名称" )
    private String deviceTypeName;

    /**
     * 巡检项目集合
     */
    @ApiModelProperty(value = "巡检项目集合",notes ="巡检项目集合" )
    private List<SpotInspectionStandardItemsQueryResp> spotInspectionItems;
}
