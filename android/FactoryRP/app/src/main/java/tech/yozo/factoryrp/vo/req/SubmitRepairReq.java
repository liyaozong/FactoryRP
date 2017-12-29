package tech.yozo.factoryrp.vo.req;

//import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import tech.yozo.factoryrp.vo.resp.device.trouble.UsedSparePartsVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkTimeVo;

import java.io.Serializable;
import java.util.List;

/**
 * 完成维修提交工单参数
 * @author chenxiang
 * @create 2017-12-19 下午3:20
 **/
@Data
public class SubmitRepairReq implements Serializable{
//    @ApiModelProperty(value = "故障信息主键")
    private Long troubleRecordId;

//    @ApiModelProperty(value = "工作量")
    private List<WorkTimeVo> workTimes;

//    @ApiModelProperty(value = "更换配件")
    private List<UsedSparePartsVo> replaceSpares;
}
