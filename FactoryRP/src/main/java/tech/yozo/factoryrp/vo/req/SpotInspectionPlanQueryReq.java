package tech.yozo.factoryrp.vo.req;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.base.ApiRequest;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * 巡检计划分页查询VO
 * @author created by Singer email:313402703@qq.com
 * @time 2018/2/1
 * @description
 */
@ApiModel
@Data
public class SpotInspectionPlanQueryReq extends ApiRequest implements Serializable {


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
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID",notes ="部门ID" )
    private Long departmentId;


    /**
     * 下次执行时间
     */
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "下次执行时间",notes ="下次执行时间" )
    private String nextExecuteTime;


    /**
     * 循环周期类型
     */
    @ApiModelProperty(value = "循环周期类型",notes ="循环周期类型" )
    private String recyclePeriodType;


    /**
     * 巡检记录详情集合
     */
    @ApiModelProperty(value = "巡检记录详情集合",notes ="巡检记录详情集合",example = "List")
    List<SpotInspectionRecordDetailAddReq> detailList;
}
