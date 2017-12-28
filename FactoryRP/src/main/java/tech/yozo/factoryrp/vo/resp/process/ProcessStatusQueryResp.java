package tech.yozo.factoryrp.vo.resp.process;

import lombok.Data;

import java.io.Serializable;

/**
 * 流程实例状态查询返回Vo
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/28
 * @description
 */
@Data
public class ProcessStatusQueryResp implements Serializable {


    /**
     * 流程实例id
     */
    private Long id;

    /**
     * 流程实例状态
     */
    private Integer processInstanceStatus;


    /**
     * 流程过程状态
     */
    private Integer processDetailStatus;

}
