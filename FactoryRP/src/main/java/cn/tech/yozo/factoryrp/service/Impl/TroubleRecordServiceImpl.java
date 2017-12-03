package cn.tech.yozo.factoryrp.service.Impl;

import cn.tech.yozo.factoryrp.entity.TroubleRecord;
import cn.tech.yozo.factoryrp.enums.TroubleStatusEnum;
import cn.tech.yozo.factoryrp.repository.TroubleRecordRepository;
import cn.tech.yozo.factoryrp.service.TroubleRecordService;
import cn.tech.yozo.factoryrp.vo.req.AddTroubleRecordReq;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author chenxiang
 * @create 2017-12-03 下午10:58
 **/
@Service
public class TroubleRecordServiceImpl implements TroubleRecordService{

    @Autowired
    private TroubleRecordRepository troubleRecordRepository;


    @Override
    public void addTroubleRecord(AddTroubleRecordReq param,Long corporateIdentify,String createUser) {
        TroubleRecord troubleRecord = new TroubleRecord();
        BeanUtils.copyProperties(param,troubleRecord);
        troubleRecord.setCorporateIdentify(corporateIdentify);
        troubleRecord.setCreateUser(createUser);
        troubleRecord.setStatus(TroubleStatusEnum.NEED_REPAIR.getCode());
        troubleRecordRepository.save(troubleRecord);
    }
}
