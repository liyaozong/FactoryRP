package tech.yozo.factoryrp.vo.innertransfer;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 巡检记录时间转换内部VO 用于返回前端选择的本周 上周 本月 上月 本年 上年 开始时间和结束时间
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/12
 * @description
 */
@Data
public class InspectionRecordTimeQueryTransferVo implements Serializable {


    /**
     * 开始时间
     */
    private Date beginTime;

    /**
     * 结束时间
     */
    private Date endTime;

}
