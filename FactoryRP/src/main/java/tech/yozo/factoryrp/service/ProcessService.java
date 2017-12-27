package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.vo.req.DeviceProcessAddReq;
import tech.yozo.factoryrp.vo.resp.process.DeviceProcessAddResp;

/**
 * 流程相关服务
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/27
 * @description
 */
public interface ProcessService {


    /**
     * 新增流程
     * @param deviceProcessAddReq
     * @param corporateIdentify
     * @return
     */
    DeviceProcessAddResp addDeviceProcess(DeviceProcessAddReq deviceProcessAddReq, Long corporateIdentify);

}
