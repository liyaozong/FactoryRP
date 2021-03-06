package tech.yozo.factoryrp.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 巡检项目实体
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/22
 * @description
 */
@Table(name = "spot_inspection_items")
@Entity
@Data
public class SpotInspectionItems extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 2397845773563945449L;


    /**
     * 巡检项目名称
     */
    @ApiModelProperty(value = "巡检项目名称",notes ="巡检项目名称" )
    @Column(name = "name",length = 20)
    private String name;


    /**
     * 记录方式
     */
    @ApiModelProperty(value = "记录方式",notes ="记录方式" )
    @Column(name = "record_type",length = 20)
    private String recordType;


    /**
     * 记录方式验证正则规则
     */
    @ApiModelProperty(value = "记录方式验证正则规则",notes ="记录方式验证正则规则" )
    @Column(name = "vaildate_regular",length = 50)
    private String vaildateRegular;


    /**
     * 关联巡检标准的ID
     */
    @ApiModelProperty(value = "关联巡检标准的ID",notes ="关联巡检标准的ID" )
    @Column(name = "standard",length = 20)
    private Long standard;


    /**
     * 上限值
     */
    @ApiModelProperty(value = "上限值",notes ="上限值" )
    @Column(name = "upper_limit",length = 20)
    private Integer upperLimit;

    /**
     * 下限值
     */
    @ApiModelProperty(value = "下限值",notes ="下限值" )
    @Column(name = "lower_limit",length = 20)
    private Integer lowerLimit;

    /**
     * 设备部位
     */
    @ApiModelProperty(value = "设备部位",notes ="设备部位" )
    @Column(name = "device_place")
    private String devicePlace;

    /**
     * 点检方法
     */
    @ApiModelProperty(value = "点检方法",notes ="设备部位" )
    @Column(name = "spot_inspection_way")
    private String spotInspectionWay;

}
