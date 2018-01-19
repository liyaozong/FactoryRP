package tech.yozo.factoryrp.vo.resp.process;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 流程详情包装Vo
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/16
 * @description
 */
@Data
public class DeviceProcessDetailWarpResp implements Serializable {

    /**
     * 处理人集合
     */
    private List<DeviceProcessHandlerResp> handlerList;

    /**
     * 流程步骤 对于同一个流程，依次递增
     */
    private Integer processStep;

    /**
     * 审核类型 标识是指定审核人还是指定审核组 1审核人2审核组
     */
    private Integer auditType;

    /**
     * 处理要求类型 1单人签署生效2多人签署生效
     */
    private Integer handleDemandType;



}
