package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.vo.req.AddTroubleRecordReq;
import tech.yozo.factoryrp.vo.req.TroubleListReq;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import tech.yozo.factoryrp.vo.resp.device.trouble.SimpleTroubleRecordVo;

/**
 * @author chenxiang
 * @create 2017-12-03 下午10:58
 **/
public interface TroubleRecordService {

    /**
     * 新增故障
     * @param param
     * @param corporateIdentify
     */
    public void addTroubleRecord(AddTroubleRecordReq param,Long corporateIdentify,AuthUser user);


    /**
     * 分页查询故障列表
     * @param param
     * @return
     */
    public Pagination<SimpleTroubleRecordVo> findByPage(TroubleListReq param);
}
