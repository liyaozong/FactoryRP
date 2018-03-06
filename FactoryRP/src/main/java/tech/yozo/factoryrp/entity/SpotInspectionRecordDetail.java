package tech.yozo.factoryrp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * 巡检记录详情实体
 */
@Table(name = "spot_inspection_record_detail")
@Entity
@Data
public class SpotInspectionRecordDetail extends BaseEntity implements Serializable {

    /**
     * 设备ID
     */
    @ApiModelProperty(value = "设备ID",notes ="设备ID" )
    @Column(name = "device_id",length = 20)
    private Long deviceId;

    /**
     * 巡检标准ID
     */
    @ApiModelProperty(value = "巡检标准ID",notes ="巡检标准ID",example = "1")
    @Column(name = "standard",length = 20)
    private Long standard;

    /**
     * 巡检记录ID
     */
    @ApiModelProperty(value = "巡检记录ID",notes ="巡检记录ID",example = "1")
    @Column(name = "record_id",length = 20)
    private Long recordId;

    /**
     * 巡检项ID
     */
    @ApiModelProperty(value = "巡检项ID",notes ="巡检项ID",example = "1")
    @Column(name = "standard_item_id",length = 20)
    private Long standardItemId;


    /**
     * 记录结果
     */
    @ApiModelProperty(value = "记录结果 是否有异常 数值等",notes ="记录结果 是否有异常 数值等",example = "1")
    @Column(name = "record_result",length = 20)
    private String recordResult;


    /**
     * 异常情况描述
     */
    @ApiModelProperty(value = "异常情况描述",notes ="异常情况描述",example = "1")
    @Column(name = "abnormal_desc",length = 20)
    private String abnormalDesc;


    /**
     *  备注
     */
    @ApiModelProperty(value = "备注",notes ="备注",example = "1")
    @Column(name = "remark",length = 50)
    private String remark;

}
