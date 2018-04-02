package tech.yozo.factoryrp.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.validation.IsDateStr;

import java.io.Serializable;
import java.util.Date;

/**
 * 巡检记录分页查询请求对象
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/12
 * @description
 */
@ApiModel
@Data
public class SpotInspectionRecordPageQueryReq implements Serializable {

    /**
     * 异常或者漏检情况查询条件 1 异常 2异常或者漏检 3漏检
     */
    @ApiModelProperty(value = "异常或者漏检情况查询条件 1 异常 2异常或者漏检 3漏检",notes ="异常或者漏检情况 1 异常 2异常或者漏检 3漏检" )
    private Integer abnormalOrMissCondition;

    /**
     * 执行时间查询条件 1 本周记录 2上周记录 3本月记录 4上月记录 5本年记录 6上年记录 7更早记录
     */
    /*@ApiModelProperty(value = "执行时间查询条件 1 本周记录 2上周记录 3本月记录 4上月记录 5本年记录 6上年记录 7更早记录",notes ="执行时间查询条件 1 本周记录 2上周记录 3本月记录 4上月记录 5本年记录 6上年记录 7更早记录" )
    private Integer executeTimeCondition;*/

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "开始时间",notes ="开始时间" ,example = "2018-01-23 18:42:30")
    @IsDateStr(message = "必须为日期格式(yyyy-MM-dd HH:mm:ss)的字符串")
    private String beginTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "结束时间",notes ="结束时间" ,example = "2018-01-23 18:42:30")
    @IsDateStr(message = "必须为日期格式(yyyy-MM-dd HH:mm:ss)的字符串")
    private String endTime;

    /**
     * 部门ID
     */
    @ApiModelProperty(value = "部门ID",notes ="部门ID" )
    private Long departmentId;

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
