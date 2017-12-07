package tech.yozo.factoryrp.service.Impl;

import tech.yozo.factoryrp.entity.TroubleRecord;
import tech.yozo.factoryrp.enums.TroubleLevelEnum;
import tech.yozo.factoryrp.enums.TroubleStatusEnum;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.repository.TroubleRecordRepository;
import tech.yozo.factoryrp.service.TroubleRecordService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.req.AddTroubleRecordReq;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.vo.req.TroubleListReq;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import tech.yozo.factoryrp.vo.resp.device.trouble.SimpleTroubleRecordVo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chenxiang
 * @create 2017-12-03 下午10:58
 **/
@Service
public class TroubleRecordServiceImpl implements TroubleRecordService {

    @Autowired
    private TroubleRecordRepository troubleRecordRepository;


    @Override
    public void addTroubleRecord(AddTroubleRecordReq param,Long corporateIdentify,AuthUser user) {
        TroubleRecord troubleRecord = new TroubleRecord();
        BeanUtils.copyProperties(param,troubleRecord);
        troubleRecord.setCorporateIdentify(corporateIdentify);
        troubleRecord.setCreateUser(user.getUserName());
        troubleRecord.setCreateUserId(user.getUserId());
        troubleRecord.setStatus(TroubleStatusEnum.NEED_REPAIR.getCode());
        troubleRecord.setOrderNo("WX"+new Date().getTime());
        troubleRecordRepository.save(troubleRecord);
    }

    @Override
    public Pagination<SimpleTroubleRecordVo> findByPage(TroubleListReq param) {
        Integer currentPage = param.getCurrentPage();
        Integer itemsPerPage = param.getItemsPerPage();
        if(null==currentPage){
            currentPage=0;
        }
        if (null==itemsPerPage){
            itemsPerPage=10;
        }
        if (currentPage > 0) {
            currentPage-=1;
        }
        Pageable p = new PageRequest(currentPage, itemsPerPage);
        Page<TroubleRecord> page = troubleRecordRepository.findAll(new Specification<TroubleRecord>() {
            @Override
            public Predicate toPredicate(Root<TroubleRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> conList = new ArrayList<>();
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
                return criteriaBuilder.equal(root.get("deviceId").as(Long.class),param.getDeviceId());
            }
        },p);
        Pagination<SimpleTroubleRecordVo> res = new Pagination(currentPage+1,itemsPerPage,page.getTotalElements());
        if (page.hasContent()){
            List<SimpleTroubleRecordVo> list = new ArrayList<>();
            page.getContent().forEach(troubleRecord -> {
                SimpleTroubleRecordVo v = new SimpleTroubleRecordVo();
                BeanUtils.copyProperties(troubleRecord,v);
                v.setTroubleLevel(TroubleLevelEnum.getByCode(troubleRecord.getTroubleLevel()).getName());
                v.setStatus(TroubleStatusEnum.getByCode(troubleRecord.getStatus()).getName());
                list.add(v);
            });
            res.setList(list);
        }
        return res;
    }

    @Override
    public void batchDelete(List<Long> ids) {
        List<TroubleRecord> oldList = troubleRecordRepository.findAll(ids);
        if (!CheckParam.isNull(oldList)){
            troubleRecordRepository.deleteInBatch(oldList);
        }
    }
}
