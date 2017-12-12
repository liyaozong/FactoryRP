package tech.yozo.factoryrp.service.Impl;

import org.springframework.data.domain.Sort;
import tech.yozo.factoryrp.entity.DeviceInfo;
import tech.yozo.factoryrp.entity.TroubleRecord;
import tech.yozo.factoryrp.enums.TroubleLevelEnum;
import tech.yozo.factoryrp.enums.TroubleStatusEnum;
import tech.yozo.factoryrp.exception.BussinessException;
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
import tech.yozo.factoryrp.vo.req.WorkOrderListReq;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import tech.yozo.factoryrp.vo.resp.device.trouble.SimpleTroubleRecordVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WaitAuditWorkOrderVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkOrderCountVo;

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
        DeviceInfo d = new DeviceInfo();
        d.setId(param.getDeviceId());
        troubleRecord.setDeviceInfo(d);
        troubleRecord.setCorporateIdentify(corporateIdentify);
        troubleRecord.setCreateUser(user.getUserName());
        troubleRecord.setCreateUserId(user.getUserId());
        troubleRecord.setStatus(TroubleStatusEnum.WAIT_AUDIT.getCode());
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
                DeviceInfo d = new DeviceInfo();
                d.setId(param.getDeviceId());
                return criteriaBuilder.equal(root.get("deviceInfo").as(DeviceInfo.class),d);
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

    @Override
    public Pagination<WaitAuditWorkOrderVo> findWorkOrderByPage(WorkOrderListReq param, Long corporateIdentify, Integer status,AuthUser user) {

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
        Pageable p = new PageRequest(currentPage, itemsPerPage,new Sort(Sort.Direction.DESC,"createTime"));
        Page<TroubleRecord> page = null;
        if (null!=user && status == TroubleStatusEnum.REPAIRING.getCode()){
            page = troubleRecordRepository.findByStatusAndRepairUserId(status,user.getUserId(),p);
        }else if ((null!=user && status == TroubleStatusEnum.REPAIRING.getCode())){
            page = troubleRecordRepository.findByStatusAndValidateUserId(status,user.getUserId(),p);
        }else {
            page= troubleRecordRepository.findByStatusAndCorporateIdentify(status,corporateIdentify,p);
        }
        Pagination<WaitAuditWorkOrderVo> res = new Pagination(currentPage+1,itemsPerPage,page.getTotalElements());
        if (page.hasContent()){
            List<WaitAuditWorkOrderVo> list = new ArrayList<>();
            page.getContent().forEach(troubleRecord -> {
                WaitAuditWorkOrderVo v = new WaitAuditWorkOrderVo();
                v.setId(troubleRecord.getId());
                v.setName(troubleRecord.getDeviceInfo().getName());
                v.setSpecification(troubleRecord.getDeviceInfo().getSpecification());
                v.setCode(troubleRecord.getDeviceInfo().getCode());
                v.setHappenTime(troubleRecord.getHappenTime());
                v.setOrderNo(troubleRecord.getOrderNo());
                v.setTroubleLevel(TroubleLevelEnum.getByCode(troubleRecord.getTroubleLevel()).getName());
                v.setStatus(TroubleStatusEnum.getByCode(troubleRecord.getStatus()).getName());
                v.setRepairUser(troubleRecord.getRepairUserName());
                list.add(v);
            });
            res.setList(list);
        }
        return res;
    }

    @Override
    public WorkOrderCountVo getCount(Long corporateIdentify, AuthUser user) {
        Long waitAudtiCount = troubleRecordRepository.count(new Specification<TroubleRecord>() {
            @Override
            public Predicate toPredicate(Root<TroubleRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> listCon = new ArrayList<>();
                listCon.add(criteriaBuilder.equal(root.get("status").as(Long.class),TroubleStatusEnum.WAIT_AUDIT.getCode()));
                listCon.add(criteriaBuilder.equal(root.get("corporateIdentify").as(Long.class),corporateIdentify));
                Predicate[] predicates = new Predicate[listCon.size()];
                predicates = listCon.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        });
        Long waitRepairCount = troubleRecordRepository.count(new Specification<TroubleRecord>() {
            @Override
            public Predicate toPredicate(Root<TroubleRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> listCon = new ArrayList<>();
                listCon.add(criteriaBuilder.equal(root.get("status").as(Long.class),TroubleStatusEnum.NEED_REPAIR.getCode()));
                listCon.add(criteriaBuilder.equal(root.get("corporateIdentify").as(Long.class),corporateIdentify));
                Predicate[] predicates = new Predicate[listCon.size()];
                predicates = listCon.toArray(predicates);
                return criteriaBuilder.and(predicates);            }
        });
        Long repairingCount = troubleRecordRepository.count(new Specification<TroubleRecord>() {
            @Override
            public Predicate toPredicate(Root<TroubleRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> listCon = new ArrayList<>();
                listCon.add(criteriaBuilder.equal(root.get("status").as(Long.class),TroubleStatusEnum.REPAIRING.getCode()));
                listCon.add(criteriaBuilder.equal(root.get("repairUserId").as(Long.class),user.getUserId()));
                Predicate[] predicates = new Predicate[listCon.size()];
                predicates = listCon.toArray(predicates);
                return criteriaBuilder.and(predicates);            }
        });

        Long waitValidateCount = troubleRecordRepository.count(new Specification<TroubleRecord>() {
            @Override
            public Predicate toPredicate(Root<TroubleRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> listCon = new ArrayList<>();
                listCon.add(criteriaBuilder.equal(root.get("status").as(Long.class),TroubleStatusEnum.REPAIRED.getCode()));
                listCon.add(criteriaBuilder.equal(root.get("validateUserId").as(Long.class),user.getUserId()));
                Predicate[] predicates = new Predicate[listCon.size()];
                predicates = listCon.toArray(predicates);
                return criteriaBuilder.and(predicates);            }
        });
        WorkOrderCountVo vo = new WorkOrderCountVo();
        vo.setAllMyOrderNum(waitAudtiCount+waitRepairCount+repairingCount);
        vo.setRepairingNum(repairingCount);
        vo.setWaitAuditNum(waitAudtiCount);
        vo.setWaitRepairNum(waitRepairCount);
        vo.setWaitValidateNum(waitValidateCount);
        return vo;
    }

    @Override
    public void obtainOrder(Long id, AuthUser user) {
        TroubleRecord old = troubleRecordRepository.findOne(id);
        if (null!=old && old.getStatus() == TroubleStatusEnum.NEED_REPAIR.getCode()
                && (null ==old.getRepairUserId() || 0 ==old.getRepairUserId())){
            old.setRepairUserId(user.getUserId());
            old.setRepairUserName(user.getUserName());
            old.setUpdateTime(new Date());
            troubleRecordRepository.save(old);
        }else{
            BussinessException biz = new BussinessException("10000","工单不存在或状态不正确");
            throw biz;
        }
    }

    @Override
    public void cancelOrder(Long id, AuthUser user) {
        TroubleRecord old = troubleRecordRepository.findOne(id);
        if (null!=old && old.getStatus() == TroubleStatusEnum.NEED_REPAIR.getCode()){
            if (old.getRepairUserId() != user.getUserId()){
                BussinessException biz = new BussinessException("10001","不是本人工单，无权限操作");
                throw biz;
            }
            old.setRepairUserId(null);
            old.setRepairUserName(null);
            old.setUpdateTime(new Date());
            troubleRecordRepository.save(old);
        }else{
            BussinessException biz = new BussinessException("10000","工单不存在或状态不正确");
            throw biz;
        }
    }

    @Override
    public void startRepair(Long id, AuthUser user) {
        TroubleRecord old = troubleRecordRepository.findOne(id);
        if (null!=old && old.getStatus() == TroubleStatusEnum.NEED_REPAIR.getCode()){
            if (old.getRepairUserId() != user.getUserId()){
                BussinessException biz = new BussinessException("10001","不是本人工单，无权限操作");
                throw biz;
            }
            old.setStatus(TroubleStatusEnum.REPAIRING.getCode());
            old.setUpdateTime(new Date());
            troubleRecordRepository.save(old);
            //TODO 创建维修单
        }else{
            BussinessException biz = new BussinessException("10000","工单不存在或状态不正确");
            throw biz;
        }
    }
}
