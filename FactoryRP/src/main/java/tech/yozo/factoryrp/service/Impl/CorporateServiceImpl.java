package tech.yozo.factoryrp.service.Impl;

import tech.yozo.factoryrp.entity.Corporate;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.repository.CorporateRepository;
import tech.yozo.factoryrp.service.CorporateService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.utils.UUIDSequenceWorker;
import tech.yozo.factoryrp.vo.req.CorporateReq;
import tech.yozo.factoryrp.vo.resp.corporate.CorporateResp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/20
 * @description
 */
@Service
public class CorporateServiceImpl implements CorporateService {

    @Resource
    private CorporateRepository corporateRepository;

    /**
     * 新增企业
     * @param corporateReq
     * @return
     */
    @Override
    public CorporateResp addCorporate(CorporateReq corporateReq) {

        Corporate corporate = corporateRepository.findByCorporateName(corporateReq.getCorporateName());

        if(!CheckParam.isNull(corporate)){
            throw new BussinessException(ErrorCode.CORPORATENAME__REPETED_ERROR.getCode(),ErrorCode.CORPORATENAME__REPETED_ERROR.getMessage());
        }

        corporate = new Corporate();
        corporate.setCorporateIdentify(UUIDSequenceWorker.uniqueSequenceId());
        corporate.setCorporateName(corporateReq.getCorporateName());
        corporate.setEnableStatus(Integer.valueOf(corporateReq.getEnableStatus()));

        corporateRepository.save(corporate);

        CorporateResp corporateResp = new CorporateResp();
        corporateResp.setCorporateIdentify(String.valueOf(corporate.getCorporateIdentify()));
        corporateResp.setEnableStatus(String.valueOf(corporate.getEnableStatus()));
        corporateResp.setCorporateName(corporate.getCorporateName());

        return corporateResp;
    }
}
