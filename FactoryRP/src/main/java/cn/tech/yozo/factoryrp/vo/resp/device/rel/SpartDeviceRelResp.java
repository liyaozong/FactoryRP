package cn.tech.yozo.factoryrp.vo.resp.device.rel;

import cn.tech.yozo.factoryrp.vo.resp.sparepars.SparePartsResp;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chenxiang
 * @create 2017-12-02 下午1:18
 **/
@Data
public class SpartDeviceRelResp extends SparePartsResp implements Serializable{
    @ApiModelProperty(value = "关联信息主键")
    private Long relId;
}
