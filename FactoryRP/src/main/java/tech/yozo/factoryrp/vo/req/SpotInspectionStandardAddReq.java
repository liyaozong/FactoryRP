package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.ApiRequest;
import tech.yozo.factoryrp.vo.validation.NotEmpty;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.List;

/**
 * 巡检标准新增请求封装类
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/23
 * @description
 */
@ApiModel
@Data
public class SpotInspectionStandardAddReq extends ApiRequest implements Serializable {

    private static final long serialVersionUID = 878732320910722990L;


    /**
     * 巡检标准名称
     */
    @ApiModelProperty(value = "巡检标准名称",notes ="巡检标准名称" )
    @NotEmpty(message = "巡检标准名称不可为空")
    private String name;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注",notes ="备注" )
    @NotEmpty(message = "备注不可为空")
    private String remark;

    /**
     * 巡检要求
     */
    @ApiModelProperty(value = "巡检要求",notes ="巡检要求" )
    @NotEmpty(message = "巡检要求不可为空")
    private String requirement ;

    /**
     * 设备类型关联ID
     */
    @ApiModelProperty(value = "设备类型关联ID",notes ="设备类型关联ID" )
    private Long deviceType;

    /**
     * 适用设备列表ID，数组形式
     */
    @ApiModelProperty(value = "适用设备列表ID，数组形式",notes ="适用设备列表ID，数组形式" )
    private List<Long> relateDevices;

    /**
     * 巡检项目集合
     */
    @ApiModelProperty(value = "巡检项目集合",notes ="巡检项目集合" )
    private List<SpotInspectionItemsAddReq> spotInspectionItems;

}
