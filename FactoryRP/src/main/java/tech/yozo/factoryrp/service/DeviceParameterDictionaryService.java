package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.entity.DeviceParameterDictionary;
import tech.yozo.factoryrp.vo.req.DeviceParameterDicReq;

import java.util.List;

/**
 * 设备参数字典Service
 * @author created by Singer email:313402703@qq.com
 * @time 2017/12/11
 * @description
 */
public interface DeviceParameterDictionaryService {


    /**
     * 添加设备字典
     * @param deviceParameterDicReq
     * @param corporateIdentify
     */
    void update(DeviceParameterDicReq deviceParameterDicReq,Long corporateIdentify);

    /**
     * 添加设备字典
     * @param deviceParameterDicReq
     * @param corporateIdentify
     */
    void add(DeviceParameterDicReq deviceParameterDicReq,Long corporateIdentify);


    /**
     * 根据code查询设备参数列表
     * @param code
     * @return
     */
    List<DeviceParameterDictionary> findByCode(String code,Long corporateIdentify);

}
