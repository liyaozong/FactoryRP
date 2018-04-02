package tech.yozo.factoryrp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 巡检标准实体
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/22
 * @description
 */
@Table(name = "spot_inspection_standard")
@Entity
@Data
public class SpotInspectionStandard extends BaseEntity implements Serializable{

    private static final long serialVersionUID = -6151552024283033419L;

    /**
     * 巡检标准名称
     */
    @ApiModelProperty(value = "巡检标准名称",notes ="巡检标准名称" )
    @Column(name = "name",length = 20)
    private String name;


    /**
     * 适用设备列表 [1,2,3] 这种格式，存放设备ID
     */
    @ApiModelProperty(value = "适用设备列表",notes ="适用设备列表" )
    @Column(name = "relate_devices",length = 200)
    private String relateDevices;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注",notes ="备注" )
    @Column(name = "remark")
    private String remark;


    /**
     * 巡检要求
     */
    @ApiModelProperty(value = "巡检要求",notes ="巡检要求" )
    @Column(name = "requirement",columnDefinition = "text")
    private String requirement ;

    /**
     * 设备类型关联ID
     */
    @ApiModelProperty(value = "设备类型关联ID",notes ="设备类型关联ID" )
    @Column(name = "device_type",length = 20)
    private Long deviceType;

}
