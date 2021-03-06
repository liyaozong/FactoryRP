package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.vo.req.CorporateReq;
import tech.yozo.factoryrp.vo.resp.corporate.CorporateResp;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/20
 * @description 企业相关服务
 */
public interface CorporateService {


    /**
     * 新增企业
     * @param corporateReq
     * @return
     */
    CorporateResp addCorporate(CorporateReq corporateReq);

}
