package tech.yozo.factoryrp.vo.resp.process;

import lombok.Data;

import java.io.Serializable;

/**
 * 流程处理返回VO
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/16
 * @description
 */
@Data
public class DeviceProcessHandlerResp implements Serializable{


    /**
     * 用户编号
     */
    private Long userId;


    /**
     * 处理人名称
     */
    private String name;

}
