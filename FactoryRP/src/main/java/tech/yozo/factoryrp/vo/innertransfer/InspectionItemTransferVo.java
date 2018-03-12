package tech.yozo.factoryrp.vo.innertransfer;

import lombok.Data;

import java.io.Serializable;

/**
 * 内部巡检计划巡检项目传输对象
 * @author created by Singer email:313402703@qq.com
 * @time 2018/3/7
 * @description
 */
@Data
public class InspectionItemTransferVo implements Serializable {

    /**
     * 如果inspectionStatus字段返回1该字段会被返回，执行的时候需要这个字段
     */
    private Long executeDetailId;

    /**
     * 巡检项目是否执行了 是否执行了提交 1执行2未执行
     */
    private Integer executeStatus;


    /**
     * 记录结果 是否有异常 数值等
     */
    private String recordResult;

    /**
     * 异常情况描述 1无异常2有异常
     */
    private String abnormalDesc;

    /**
     *  备注
     */
    private String remark;
}
