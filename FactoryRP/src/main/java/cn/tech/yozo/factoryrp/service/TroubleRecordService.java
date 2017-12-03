package cn.tech.yozo.factoryrp.service;

import cn.tech.yozo.factoryrp.vo.req.AddTroubleRecordReq;

/**
 * @author chenxiang
 * @create 2017-12-03 下午10:58
 **/
public interface TroubleRecordService {

    /**
     * 新增故障
     * @param param
     * @param corporateIdentify
     * @param createUser
     */
    public void addTroubleRecord(AddTroubleRecordReq param,Long corporateIdentify,String createUser);
}
