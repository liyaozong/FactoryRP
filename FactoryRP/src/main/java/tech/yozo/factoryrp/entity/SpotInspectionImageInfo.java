package tech.yozo.factoryrp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 点巡检图片信息
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/15
 * @description
 */
@Table(name = "spot_inspection_image_info")
@Entity
@Data
public class SpotInspectionImageInfo extends BaseEntity implements Serializable {


    /**
     * 点检计划ID
     */
    @ApiModelProperty(value = "点检计划ID",notes ="点检计划ID" )
    @Column(name = "spot_inspection_plan",length = 20)
    private Long spotInspectionPlan;


    /**
     * 点检标准ID
     */
    @ApiModelProperty(value = "点检标准ID",notes ="点检标准ID" )
    @Column(name = "spot_inspection_standard",length = 20)
    private Long spotInspectionStandard;

    /**
     * 巡检记录ID
     */
    @ApiModelProperty(value = "巡检记录ID",notes ="巡检记录ID" )
    @Column(name = "record_id",length = 20)
    private Long recordId;


    /**
     * 图片KEY
     */
    @ApiModelProperty(value = "图片KEY",notes ="图片KEY" )
    @Column(name = "image_key",length = 20)
    private String imageKey;

}
