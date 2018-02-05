package tech.yozo.factoryrp.vo.resp.inspection;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

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


    /**
     * 点检标准ID
     */
    @ApiModelProperty(value = "点检标准ID",required = true,notes = "点检标准ID",example = "1")
    private Long id;


    /**
     * 巡检标准名称
     */
    @ApiModelProperty(value = "巡检标准名称",notes ="巡检标准名称" )
    private String name;

    /**
     * 设备ID集合
     */
    @ApiModelProperty(value = "设备ID集合",notes ="设备ID集合" )
    private List<Long> relateDevices;

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

}
