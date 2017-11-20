package cn.tech.yozo.factoryrp.service;

import cn.tech.yozo.factoryrp.vo.req.CorporateReq;
import cn.tech.yozo.factoryrp.vo.resp.CorporateResp;

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
