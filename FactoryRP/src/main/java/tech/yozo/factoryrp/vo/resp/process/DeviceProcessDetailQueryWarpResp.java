package tech.yozo.factoryrp.vo.resp.process;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 流程细节查询包装VO
 * @author created by Singer email:313402703@qq.com
 * @time 2018/1/19
 * @description
 */
@ApiModel
@Data
public class DeviceProcessDetailQueryWarpResp implements Serializable{

    private static final long serialVersionUID = 1691076112254991614L;

    /**
     * 流程id
     */
    @ApiModelProperty(value = "流程id",required = true,notes = "流程id",example = "id")
    private Long id;

    /**
     * 流程名称
     */
    @ApiModelProperty(value = "流程名称",required = true,notes = "流程名称",example = "processName")
    private String processName;

    /**
     * 流程细节集合
     */
    @ApiModelProperty(value = "流程细节集合",required = true,notes = "流程细节集合",example = "processDetailList")
    private List<DeviceProcessDetailQueryResp> processDetailList;

}
