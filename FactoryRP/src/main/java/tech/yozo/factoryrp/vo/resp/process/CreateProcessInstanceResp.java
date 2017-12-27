package tech.yozo.factoryrp.vo.resp.process;

import lombok.Data;

import java.io.Serializable;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
@Data
public class CreateProcessInstanceResp implements Serializable{


    /**
     * 返回的流程实例id
     */
    private Long id;

}
