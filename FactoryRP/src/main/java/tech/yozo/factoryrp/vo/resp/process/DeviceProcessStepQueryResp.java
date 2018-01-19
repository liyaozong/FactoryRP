package tech.yozo.factoryrp.vo.resp.process;

import lombok.Data;

import java.io.Serializable;

/**
 * 流程步骤查询返回Resp
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/19
 * @description
 */
@Data
public class DeviceProcessStepQueryResp implements Serializable {

    /**
     * 当前步骤的ID
     */
    private Long currentProcessStep;

    /**
     * 下一步骤的ID
     */
    private Long nextProcessStep;

}
