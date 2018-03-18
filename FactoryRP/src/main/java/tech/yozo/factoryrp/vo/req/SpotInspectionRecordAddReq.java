package tech.yozo.factoryrp.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tech.yozo.factoryrp.entity.SpotInspectionRecordDetail;
import tech.yozo.factoryrp.vo.base.ApiRequest;

import javax.persistence.Column;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 巡检记录新增请求对象
 */
@ApiModel
@Data
public class SpotInspectionRecordAddReq extends ApiRequest implements Serializable {

    /**
     * 巡检计划名称
     */
    @ApiModelProperty(value = "巡检标准ID",notes ="巡检标准ID",example = "1")
    @Column(name = "standard",length = 20)
    private Long standard;


    /**
     * 巡检计划名称
     */
    @ApiModelProperty(value = "巡检计划名称",notes ="巡检计划名称",example = "1")
    @Column(name = "plan_name",length = 50)
    private String planName;


    /**
     * 巡检计划ID
     */
    @ApiModelProperty(value = "巡检计划ID",notes ="巡检计划ID",example = "1")
    @Column(name = "plan_id",length = 50)
    private Long planId;

    /**
     * 巡检时间
     */
    @ApiModelProperty(value = "巡检时间",notes ="巡检时间",example = "1")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "execute_time")
    private String executeTime;


    /**
     * 执行者
     */
    @ApiModelProperty(value = "执行者",notes ="执行者",example = "1")
    @Column(name = "executor",length = 50)
    private Long executor;


    /**
     * 计划时间
     */
    @ApiModelProperty(value = "计划时间",notes ="计划时间",example = "1")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @Column(name = "plan_time")
    private Date planTime;


    /**
     * 循环周期类型 周，年，天，小时，月等
     */
    @ApiModelProperty(value = "循环周期类型",notes ="循环周期类型" )
    @Column(name = "recycle_period_type",length = 20)
    private String recyclePeriodType;


    /**
     * 循环周期
     */
    @ApiModelProperty(value = "循环周期",notes ="循环周期" )
    @Column(name = "recycle_period",length = 20)
    private Integer recyclePeriod;

    /**
     * 巡检记录详情集合
     */
    @ApiModelProperty(value = "巡检记录详情集合",notes ="巡检记录详情集合",example = "[1,2,3]")
    private List<SpotInspectionRecordDetail> detailList;


}
