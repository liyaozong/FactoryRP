package tech.yozo.factoryrp.service.Impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tech.yozo.factoryrp.entity.*;
import tech.yozo.factoryrp.enums.RepairStatusEnum;
import tech.yozo.factoryrp.enums.TroubleDealPhaseEnum;
import tech.yozo.factoryrp.enums.TroubleLevelEnum;
import tech.yozo.factoryrp.enums.TroubleStatusEnum;
import tech.yozo.factoryrp.enums.device.DeviceStatusEnum;
import tech.yozo.factoryrp.enums.process.DeviceProcessPhaseEnum;
import tech.yozo.factoryrp.enums.process.DeviceProcessTypeEnum;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.repository.*;
import tech.yozo.factoryrp.service.ProcessService;
import tech.yozo.factoryrp.service.TroubleRecordService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.DateTimeUtil;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.vo.req.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.vo.resp.IndexSimpleTroubleRecord;
import tech.yozo.factoryrp.vo.resp.IndexTroubleRecordCountVo;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import tech.yozo.factoryrp.vo.resp.device.trouble.*;
import tech.yozo.factoryrp.vo.resp.process.DeviceProcessDetailWarpResp;
import tech.yozo.factoryrp.vo.resp.process.DeviceProcessHandlerResp;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chenxiang
 * @create 2017-12-03 下午10:58
 **/
@Service
public class TroubleRecordServiceImpl implements TroubleRecordService {

    @Autowired
    private TroubleRecordRepository troubleRecordRepository;
    @Autowired
    private RepairRecordRepository repairRecordRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private WorkTimeRepository workTimeRepository;
    @Autowired
    private RepairRecordSparePartRelRepository repairRecordSparePartRelRepository;
    @Autowired
    private TroubleRecordUserRelRepository troubleRecordUserRelRepository;
    @Autowired
    private ProcessService processService;
    @Autowired
    private RepairGroupRepository repairGroupRepository;
    @Autowired
    private DeviceTroubleTypeRepository deviceTroubleTypeRepository;
    @Resource
    private DeviceTypeRepository deviceTypeRepository;
    @Autowired
    private DeviceInfoRepository deviceInfoRepository;

    private static Logger logger = LoggerFactory.getLogger(TroubleRecordServiceImpl.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
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

        DeviceInfo deviceInfo = deviceInfoRepository.getOne(param.getDeviceId());
        if (null == deviceInfo){
            BussinessException biz = new BussinessException("10001","设备信息不存在");
            throw biz;
        }
        //查询设备对应的使用部门所设置的审核流程
        List<DeviceProcessDetailWarpResp> listPd = processService.queryProcessAduitInfo(DeviceProcessTypeEnum.DEVICE_PROCESS_MALFUNCTION_REPAIR.getCode(),
                DeviceProcessPhaseEnum.DEVICE_PROCESS_PHASE_APPLICATION_APPROVAL.getCode(),3l,deviceInfo.getUseDept(),corporateIdentify);
        if (null==listPd || listPd.size()<=0){
            BussinessException biz = new BussinessException("10001","请先设置设备对应部门的故障报修审核流程");
            throw biz;
        }

        troubleRecord = troubleRecordRepository.save(troubleRecord);
        Long trId = troubleRecord.getId();
        Integer trStatus = troubleRecord.getStatus();
        //生成对应的审核人员记录
        List<TroubleRecordUserRel> troubleRecordUserRels = new ArrayList<>();
        listPd.stream().forEach(deviceProcessDetailWarpResp -> {
            List<DeviceProcessHandlerResp> users = deviceProcessDetailWarpResp.getHandlerList();
            if (!CheckParam.isNull(users)){
                users.stream().forEach(au->{
                    TroubleRecordUserRel troubleRecordUserRel = new TroubleRecordUserRel();
                    troubleRecordUserRel.setTroubleRecordId(trId);
                    troubleRecordUserRel.setDealPhase(TroubleDealPhaseEnum.WAIT_AUDIT.getCode());
                    troubleRecordUserRel.setDealStatus(trStatus);
                    troubleRecordUserRel.setCorporateIdentify(corporateIdentify);
                    troubleRecordUserRel.setDealStep(deviceProcessDetailWarpResp.getProcessStep());
                    troubleRecordUserRel.setDealUserId(au.getUserId());
                    troubleRecordUserRel.setDealUserName(au.getName());
                    troubleRecordUserRel.setExecuteType(deviceProcessDetailWarpResp.getHandleDemandType());
                    if (deviceProcessDetailWarpResp.getProcessStep()==1){
                        //第一步设置成处理中
                        troubleRecordUserRel.setDealStepStatus(0);
                    }else {
                        //其它步骤设置成等待处理
                        troubleRecordUserRel.setDealStepStatus(1);
                    }
                    troubleRecordUserRels.add(troubleRecordUserRel);
                });
            }
        });
        if (null==troubleRecordUserRels || troubleRecordUserRels.size()<=0){
            BussinessException biz = new BussinessException("10001","请先设置设备对应部门的故障报修审核流程对应的审核人员");
            throw biz;
        }
        troubleRecordUserRelRepository.save(troubleRecordUserRels);

    }

    @Override
    public Pagination<SimpleTroubleRecordVo> findByPage(TroubleListReq param) {

        logger.info(">>>>>>>>>>>>>>>>>>设备对应的故障列表请求参数<<<<<<<<<<<<<<<<<<<<<<<<<"+ JSON.toJSONString(param));

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

        /*DeviceInfo deviceInfo = deviceInfoRepository.findOne(param.getDeviceId());

        if(CheckParam.isNull(deviceInfo)){
            throw new BussinessException(ErrorCode.NO_DEVICEINFO_ERROR.getCode(),
                    ErrorCode.NO_DEVICEINFO_ERROR.getMessage());
        }*/

        Pageable p = new PageRequest(currentPage, itemsPerPage);
        Page<TroubleRecord> page = troubleRecordRepository.findAll(new Specification<TroubleRecord>() {
            @Override
            public Predicate toPredicate(Root<TroubleRecord> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> conList = new ArrayList<>();
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));

                //if(!CheckParam.isNull(deviceInfo)){
                    DeviceInfo d = new DeviceInfo();
                    d.setId(param.getDeviceId());
                    return criteriaBuilder.equal(root.get("deviceInfo").as(DeviceInfo.class),d);
                    //return criteriaBuilder.equal(root.get("deviceInfo").as(DeviceInfo.class),deviceInfo);
               // }else{
               //     return null;
               // }
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
        }else if ((null!=user && status == TroubleStatusEnum.REPAIRED.getCode())){
            Page<TroubleRecordUserRel> pageRel = troubleRecordUserRelRepository.
                    findByDealUserIdAndDealStepStatusAndCorporateIdentifyAndDealStatusAndDealPhase(user.getUserId(),0,corporateIdentify,TroubleStatusEnum.REPAIRED.getCode(),2,p);
            if (pageRel.hasContent()){
                List<Long> troubleRecordIds = new ArrayList<>();
                pageRel.getContent().stream().forEach(troubleRecordUserRel -> {
                    troubleRecordIds.add(troubleRecordUserRel.getTroubleRecordId());
                });
                page = troubleRecordRepository.findByIdIn(troubleRecordIds,p);
            }
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
                if (null!=troubleRecord.getTroubleLevel()){
                    if (null!=TroubleLevelEnum.getByCode(troubleRecord.getTroubleLevel())){
                        v.setTroubleLevel(TroubleLevelEnum.getByCode(troubleRecord.getTroubleLevel()).getName());
                    }
                }
                if (null!=troubleRecord.getStatus()){
                    if (null!=TroubleStatusEnum.getByCode(troubleRecord.getStatus())){
                        v.setStatus(TroubleStatusEnum.getByCode(troubleRecord.getStatus()).getName());
                    }
                }
                v.setRepairUser(troubleRecord.getRepairUserName());
                list.add(v);
            });
            res.setList(list);
        }
        return res;
    }

    @Override
    public Pagination<WorkOrderWebListVo> findWaitAuditWorkOrder(WorkOrderListReq req, Long corporateIdentify, AuthUser user) {
        Integer currentPage = req.getCurrentPage();
        Integer itemsPerPage = req.getItemsPerPage();
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
        Page<TroubleRecordUserRel> page = troubleRecordUserRelRepository.
                findByDealUserIdAndDealStepStatusAndCorporateIdentifyAndDealStatusAndDealPhase(user.getUserId(),0,corporateIdentify,TroubleStatusEnum.WAIT_AUDIT.getCode(),1,p);
        Pagination<WorkOrderWebListVo> res = new Pagination(currentPage+1,itemsPerPage,page.getTotalElements());
        if (page.hasContent()){
            List<Long> troubleRecordIds = new ArrayList<>();
            page.getContent().stream().forEach(troubleRecordUserRel -> {
                troubleRecordIds.add(troubleRecordUserRel.getTroubleRecordId());
            });

            if (null!=troubleRecordIds && troubleRecordIds.size()>0){
                List<TroubleRecord> tl =troubleRecordRepository.findAll(troubleRecordIds);
                if (null!=tl && tl.size()>0){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    List<WorkOrderWebListVo> list = new ArrayList<>();
                    tl.stream().forEach(troubleRecord -> {
                        WorkOrderWebListVo v = new WorkOrderWebListVo();
                        v.setId(troubleRecord.getId());
                        DeviceInfo deviceInfo = troubleRecord.getDeviceInfo();
                        if (null!=deviceInfo){
                            v.setDeviceName(deviceInfo.getName());
                            v.setSpecification(deviceInfo.getSpecification());
                            v.setDeviceCode(deviceInfo.getCode());
                            if (null!=deviceInfo.getUseDept() && 0!=deviceInfo.getUseDept()){
                                Department department =  departmentRepository.findOne(deviceInfo.getUseDept());
                                if (null!=department){
                                    v.setUseDept(department.getName());
                                }
                            }
                        }
                        if (null!=troubleRecord.getHappenTime()){
                            v.setHappenTime(sdf.format(troubleRecord.getHappenTime()));
                        }
                        if (null!=troubleRecord.getCreateTime()){
                            v.setHappenTime(sdf.format(troubleRecord.getCreateTime()));
                        }

                        v.setOrderNo(troubleRecord.getOrderNo());
                        if (null!=troubleRecord.getTroubleLevel()){
                            if (null!=TroubleLevelEnum.getByCode(troubleRecord.getTroubleLevel())){
                                v.setTroubleLevel(TroubleLevelEnum.getByCode(troubleRecord.getTroubleLevel()).getName());
                            }
                        }
                        if (null!=troubleRecord.getStatus()){
                            if (null!=TroubleStatusEnum.getByCode(troubleRecord.getStatus())){
                                v.setStatus(TroubleStatusEnum.getByCode(troubleRecord.getStatus()).getName());
                            }
                        }
                        if (null!=troubleRecord.getRepairGroupId() && 0!=troubleRecord.getRepairGroupId()){
                            RepairGroup repairGroup = repairGroupRepository.getOne(troubleRecord.getRepairGroupId());
                            if (null!=repairGroup){
                                v.setRepairGroup(repairGroup.getName());
                            }
                        }
                        if (null!=troubleRecord.getTroubleType() && 0!=troubleRecord.getTroubleType()){
                           DeviceTroubleType deviceTroubleType = deviceTroubleTypeRepository.getOne(troubleRecord.getTroubleType());
                           if (null!=deviceTroubleType){
                               v.setTroubleType(deviceTroubleType.getName());
                           }
                        }
                        TroubleLevelEnum tle = TroubleLevelEnum.getByCode(troubleRecord.getTroubleLevel());
                        if (null!=tle){
                            v.setTroubleLevel(tle.getName());
                        }
                        v.setRemark(troubleRecord.getRemark());
                        list.add(v);
                    });
                    res.setList(list);
                }
            }
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
    public void allocateWorker(AllocateWorkerReq param) {
        TroubleRecord old = troubleRecordRepository.findOne(param.getTroubleRecordId());
        if (null!=old && old.getStatus() == TroubleStatusEnum.NEED_REPAIR.getCode()){
            old.setRepairUserId(param.getRepairUserId());
            old.setRepairUserName(param.getRepairUserName());
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
            if (Long.compare(old.getRepairUserId(),user.getUserId())!=0){
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
    @Transactional(rollbackFor = Exception.class)
    public void startRepair(StartRepairReq param, AuthUser user) {
        TroubleRecord old = troubleRecordRepository.findOne(param.getTroubleRecordId());
        if (null!=old && old.getStatus() == TroubleStatusEnum.NEED_REPAIR.getCode()){
            if (Long.compare(old.getRepairUserId(),user.getUserId())!=0){
                BussinessException biz = new BussinessException("10001","不是本人工单，无权限操作");
                throw biz;
            }
            old.setStatus(TroubleStatusEnum.REPAIRING.getCode());
            old.setUpdateTime(new Date());
            if (null!=param.getRepairGroupId()){
                old.setRepairGroupId(param.getRepairGroupId());
            }
            troubleRecordRepository.save(old);
            //创建维修单
            RepairRecord repairRecord = new RepairRecord();
            repairRecord.setTroubleRecordId(old.getId());
            repairRecord.setCorporateIdentify(old.getCorporateIdentify());
            if (CheckParam.isNull(param.getStartTime())){
                repairRecord.setStartTime(new Date());
            }else {
                repairRecord.setStartTime(DateTimeUtil.strToDate(param.getStartTime()));
            }
            repairRecord.setRepairStatus(param.getRepairStatus());
            if (null!=param.getTroubleTypeId()){
                DeviceTroubleType dtt = new DeviceTroubleType();
                dtt.setId(param.getTroubleTypeId());
                repairRecord.setTroubleType(dtt);
            }
            repairRecord.setTroubleReason(param.getTroubleReason());
            repairRecord.setRepairLevel(param.getRepairLevel());
            repairRecord.setRepairAmount(param.getRepairAmount());
            repairRecord.setWorkRemark(param.getWorkRemark());
            repairRecord.setRepairType(1);
            repairRecord.setStoped(param.getStoped());
            repairRecord.setStopedHour(param.getStopedHour());
            repairRecord.setCostHour(param.getCostHour());
            repairRecordRepository.save(repairRecord);
        }else{
            BussinessException biz = new BussinessException("10000","工单不存在或状态不正确");
            throw biz;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void endRepair(EndRepairReq param, AuthUser user) {
        TroubleRecord old = troubleRecordRepository.findOne(param.getTroubleRecordId());
        if (null!=old && old.getStatus() == TroubleStatusEnum.REPAIRING.getCode()){
            if (Long.compare(old.getRepairUserId(),user.getUserId())!=0){
                BussinessException biz = new BussinessException("10001","不是本人工单，无权限操作");
                throw biz;
            }
            if (null!=param.getRepairGroupId() && old.getRepairGroupId() != param.getRepairGroupId()){
                old.setRepairGroupId(param.getRepairGroupId());
                old.setUpdateTime(new Date());
                troubleRecordRepository.save(old);
            }
            //更新维修单
            RepairRecord repairRecord = repairRecordRepository.findByTroubleRecordId(param.getTroubleRecordId());
            if (null == repairRecord){
                BussinessException biz = new BussinessException("10000","维修单不存在");
                throw biz;
            }
            repairRecord.setRepairStatus(RepairStatusEnum.DONE.getCode());
            if (null!=param.getTroubleTypeId()){
                DeviceTroubleType dtt = new DeviceTroubleType();
                dtt.setId(param.getTroubleTypeId());
                repairRecord.setTroubleType(dtt);
            }
            repairRecord.setTroubleReason(param.getTroubleReason());
            repairRecord.setRepairLevel(param.getRepairLevel());
            repairRecord.setRepairAmount(param.getRepairAmount());
            repairRecord.setWorkRemark(param.getWorkRemark());
            repairRecord.setStoped(param.getStoped());
            repairRecord.setStopedHour(param.getStopedHour());
            repairRecord.setCostHour(param.getCostHour());
            if (CheckParam.isNull(param.getEndTime())){
                repairRecord.setEndTime(new Date());
            }else {
                repairRecord.setEndTime(DateTimeUtil.strToDate(param.getEndTime()));
            }
            repairRecord.setUpdateTime(new Date());
            repairRecordRepository.save(repairRecord);
        }else{
            BussinessException biz = new BussinessException("10000","工单不存在或状态不正确");
            throw biz;
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitRepair(SubmitRepairReq param, AuthUser user) {
        TroubleRecord old = troubleRecordRepository.findOne(param.getTroubleRecordId());
        if (null!=old && old.getStatus() == TroubleStatusEnum.REPAIRING.getCode()){
            if (Long.compare(old.getRepairUserId(),user.getUserId())!=0){
                BussinessException biz = new BussinessException("10001","不是本人工单，无权限操作");
                throw biz;
            }
            old.setStatus(TroubleStatusEnum.REPAIRED.getCode());
            old.setUpdateTime(new Date());
            troubleRecordRepository.save(old);
            RepairRecord repairRecord = repairRecordRepository.findByTroubleRecordId(param.getTroubleRecordId());
            if (null!=repairRecord){
               List<WorkTimeVo> workTimeVos = param.getWorkTimes();
               if(!CheckParam.isNull(workTimeVos)){
                   List<WorkTime> needSave = new ArrayList<>();
                   workTimeVos.stream().forEach(workTimeVo -> {
                       WorkTime wt = new WorkTime();
                       wt.setCostHour(workTimeVo.getCostHour());
                       wt.setEndTime(DateTimeUtil.strToDate(workTimeVo.getEndTime()));
                       wt.setStartTime(DateTimeUtil.strToDate(workTimeVo.getStartTime()));
                       wt.setRepairRecordId(repairRecord.getId());
                       wt.setRepairUserId(workTimeVo.getRepairUserId());
                       wt.setRepairUserName(workTimeVo.getRepairUserName());
                       wt.setCorporateIdentify(user.getCorporateIdentify());
                       wt.setType(1);
                       needSave.add(wt);
                   });
                   //保存工时信息
                   List<WorkTime> olds = workTimeRepository.findByRepairRecordIdAndType(repairRecord.getId(),1);
                   if (!CheckParam.isNull(olds)){
                       //清空老数据
                       workTimeRepository.delete(olds);
                   }
                   if (!CheckParam.isNull(needSave)){
                       //保存新数据
                       workTimeRepository.save(needSave);
                   }
               }
               List<UsedSparePartsVo> repairRecordSparePartRels = param.getReplaceSpares();
               if (!CheckParam.isNull(repairRecordSparePartRels)){
                   //保存更换配件信息
                   List<RepairRecordSparePartRel> needSave = new ArrayList<>();
                   repairRecordSparePartRels.stream().forEach(rr->{
                       RepairRecordSparePartRel rrp = new RepairRecordSparePartRel();
                       rrp.setRepairRecordId(repairRecord.getId());
                       rrp.setName(rr.getName());
                       rrp.setCode(rr.getCode());
                       rrp.setSpecificationsAndodels(rr.getSpecificationsAndodels());
                       rrp.setInventoryUpperLimit(rr.getInventoryUpperLimit());
                       rrp.setOldOrderNum(rr.getOldOrderNum());
                       rrp.setNewOrderNum(rr.getNewOrderNum());
                       rrp.setAmount(rr.getAmount());
                       rrp.setCorporateIdentify(user.getCorporateIdentify());
                       rrp.setType(1);
                       needSave.add(rrp);
                   });
                   List<RepairRecordSparePartRel> ols = repairRecordSparePartRelRepository.findByRepairRecordIdAndType(repairRecord.getId(),1);
                   if (!CheckParam.isNull(ols)){
                       //清空老数据
                       repairRecordSparePartRelRepository.delete(ols);
                   }
                   if (!CheckParam.isNull(needSave)){
                       //保存新数据
                       repairRecordSparePartRelRepository.save(needSave);
                   }
               }

               //开启验证流程
                //查询设备对应的使用部门所设置的验证流程
                List<DeviceProcessDetailWarpResp> listPd = processService.queryProcessAduitInfo(DeviceProcessTypeEnum.DEVICE_PROCESS_MALFUNCTION_REPAIR.getCode(),
                        DeviceProcessPhaseEnum.DEVICE_PROCESS_REPAIRED_ENDED_VERIFYING.getCode(),3l,old.getDeviceInfo().getUseDept(),user.getCorporateIdentify());
                if (null==listPd || listPd.size()<=0){
                    BussinessException biz = new BussinessException("10001","请先设置设备对应部门的故障验证流程");
                    throw biz;
                }
                //生成对应的审核人员记录
                List<TroubleRecordUserRel> troubleRecordUserRels = new ArrayList<>();
                listPd.stream().forEach(deviceProcessDetailWarpResp -> {
                    List<DeviceProcessHandlerResp> users = deviceProcessDetailWarpResp.getHandlerList();
                    if (!CheckParam.isNull(users)){
                        users.stream().forEach(au->{
                            TroubleRecordUserRel troubleRecordUserRel = new TroubleRecordUserRel();
                            troubleRecordUserRel.setTroubleRecordId(old.getId());
                            troubleRecordUserRel.setDealPhase(TroubleDealPhaseEnum.WAIT_VALIDATE.getCode());
                            troubleRecordUserRel.setDealStatus(TroubleStatusEnum.REPAIRED.getCode());
                            troubleRecordUserRel.setCorporateIdentify(user.getCorporateIdentify());
                            troubleRecordUserRel.setDealStep(deviceProcessDetailWarpResp.getProcessStep());
                            troubleRecordUserRel.setDealUserId(au.getUserId());
                            troubleRecordUserRel.setDealUserName(au.getName());
                            troubleRecordUserRel.setExecuteType(deviceProcessDetailWarpResp.getHandleDemandType());
                            if (deviceProcessDetailWarpResp.getProcessStep()==1){
                                //第一步设置成处理中
                                troubleRecordUserRel.setDealStepStatus(0);
                            }else {
                                //其它步骤设置成等待处理
                                troubleRecordUserRel.setDealStepStatus(1);
                            }
                            troubleRecordUserRels.add(troubleRecordUserRel);
                        });
                    }
                });
                if (null==troubleRecordUserRels || troubleRecordUserRels.size()<=0){
                    BussinessException biz = new BussinessException("10001","请先设置设备对应部门的故障报修审核流程对应的审核人员");
                    throw biz;
                }
                troubleRecordUserRelRepository.save(troubleRecordUserRels);

            }else {
                BussinessException biz = new BussinessException("10000","工单不存在");
                throw biz;
            }
        }else{
            BussinessException biz = new BussinessException("10000","工单不存在或状态不正确");
            throw biz;
        }
    }

    @Override
    public WorkOrderDetailVo getDetail(Long id, AuthUser user) {
        WorkOrderDetailVo vo = new WorkOrderDetailVo();
        TroubleRecord old = troubleRecordRepository.findOne(id);
        if (null!=old ){
            vo.setTroubleRecordId(old.getId());
            //设备信息
            DeviceInfo deviceInfo = old.getDeviceInfo();
            if (null!=deviceInfo){
                vo.setDeviceId(deviceInfo.getId());
                vo.setDeviceName(deviceInfo.getName());
                vo.setSpecification(deviceInfo.getSpecification());
                vo.setDeviceCode(deviceInfo.getCode());
                vo.setInstallationAddress(deviceInfo.getInstallationAddress());
                if (null!=deviceInfo.getUseDept()){
                    Department department =  departmentRepository.findOne(deviceInfo.getUseDept());
                    if (null!=department){
                        vo.setUseDept(department.getName());
                    }
                }
            }
            //故障信息
            vo.setHappenTime(old.getHappenTime());
            vo.setOrderNo(old.getOrderNo());
            vo.setDeviceUser(old.getDeviceUser());
            vo.setPhone(old.getPhone());
            vo.setCreateUser(old.getCreateUser());
            vo.setRepairUserName(old.getRepairUserName());
            vo.setRemark(old.getRemark());
            vo.setRepairGroupId(old.getRepairGroupId());
            //维修单信息
           RepairRecord repairRecord = repairRecordRepository.findByTroubleRecordId(id);
           if (null!=repairRecord){
               vo.setRepairStatus(repairRecord.getRepairStatus());
               if (null!=vo.getRepairStatus()){
                   vo.setRepairStatusString(RepairStatusEnum.getByCode(repairRecord.getRepairStatus()).getName());
               }
               DeviceTroubleType troubleType = repairRecord.getTroubleType();
               if (null!=troubleType){
                   vo.setTroubleType(troubleType.getName());
               }
               vo.setTroubleReason(repairRecord.getTroubleReason());
               vo.setRepairLevel(repairRecord.getRepairLevel());
               vo.setStoped(repairRecord.getStoped());
               vo.setStopedHour(repairRecord.getStopedHour());
               vo.setRepairAmount(repairRecord.getRepairAmount());
               vo.setWorkRemark(repairRecord.getWorkRemark());
               vo.setStartTime(DateTimeUtil.dateToStr(repairRecord.getStartTime(),""));
               vo.setEndTime(DateTimeUtil.dateToStr(repairRecord.getEndTime(),""));
               vo.setCostHour(repairRecord.getCostHour());

               //工作量
               List<WorkTimeVo> workTimes = new ArrayList<>();
               List<WorkTime> workTimeList= workTimeRepository.findByRepairRecordIdAndType(repairRecord.getId(),1);
               if (!CheckParam.isNull(workTimeList)){
                   workTimeList.stream().forEach(workTime -> {
                       WorkTimeVo workTimeVo = new WorkTimeVo();
                       workTimeVo.setCostHour(workTime.getCostHour());
                       workTimeVo.setStartTime(DateTimeUtil.dateToStr(workTime.getStartTime(),""));
                       workTimeVo.setEndTime(DateTimeUtil.dateToStr(workTime.getEndTime(),""));
                       workTimeVo.setRepairUserName(workTime.getRepairUserName());
                       workTimes.add(workTimeVo);
                   });
               }
               vo.setWorkTimes(workTimes);

               //更换配件信息
               List<UsedSparePartsVo> replaceSpares = new ArrayList<>();
               List<RepairRecordSparePartRel> ols = repairRecordSparePartRelRepository.findByRepairRecordIdAndType(repairRecord.getId(),1);
               if (!CheckParam.isNull(ols)){
                   ols.stream().forEach(rr->{
                       UsedSparePartsVo usedSparePartsVo = new UsedSparePartsVo();
                       usedSparePartsVo.setAmount(rr.getAmount());
                       usedSparePartsVo.setCode(rr.getCode());
                       usedSparePartsVo.setInventoryUpperLimit(rr.getInventoryUpperLimit());
                       usedSparePartsVo.setName(rr.getName());
                       usedSparePartsVo.setOldOrderNum(rr.getOldOrderNum());
                       usedSparePartsVo.setNewOrderNum(rr.getNewOrderNum());
                       usedSparePartsVo.setSpecificationsAndodels(rr.getSpecificationsAndodels());
                       replaceSpares.add(usedSparePartsVo);
                   });
               }
               vo.setReplaceSpares(replaceSpares);
           }
        }else{
            BussinessException biz = new BussinessException("10000","工单不存在");
            throw biz;
        }
        return vo;
    }

    @Override
    public SingleTroubleDetail getDetailById(Long id) {
        SingleTroubleDetail vo = new SingleTroubleDetail();
        TroubleRecord old = troubleRecordRepository.findOne(id);
        if (null!=old ){
            vo.setTroubleRecordId(old.getId());
            //设备信息
            DeviceInfo deviceInfo = old.getDeviceInfo();
            if (null!=deviceInfo){
                vo.setDeviceName(deviceInfo.getName());
                vo.setSpecification(deviceInfo.getSpecification());
                vo.setDeviceCode(deviceInfo.getCode());
                vo.setInstallationAddress(deviceInfo.getInstallationAddress());
                if (null!=deviceInfo.getDeviceType()){
                  DeviceType deviceType = deviceTypeRepository.getOne(deviceInfo.getDeviceType());
                  if (null!=deviceType){
                      vo.setDeviceType(deviceType.getName());
                  }
                }
                if (null!=deviceInfo.getUseDept()){
                    Department department =  departmentRepository.findOne(deviceInfo.getUseDept());
                    if (null!=department){
                        vo.setUseDept(department.getName());
                    }
                }
            }
            //故障信息
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            vo.setHappenTime(sdf.format(old.getHappenTime()));
            vo.setOrderNo(old.getOrderNo());
            vo.setDeviceUser(old.getDeviceUser());
            vo.setPhone(old.getPhone());
            vo.setCreateUser(old.getCreateUser());
            vo.setRemark(old.getRemark());
            vo.setCreateTime(sdf.format(old.getCreateTime()));
            //维修单信息
            if (null!=old.getTroubleType() && 0!=old.getTroubleType()){
                DeviceTroubleType troubleType = deviceTroubleTypeRepository.getOne(old.getTroubleType());
                if (null!=troubleType){
                    vo.setTroubleType(troubleType.getName());
                }
            }
            TroubleLevelEnum troubleLevelEnum = TroubleLevelEnum.getByCode(old.getTroubleLevel());
            if (null!=troubleLevelEnum){
                vo.setTroubleLevel(troubleLevelEnum.getName());
            }
            vo.setRemark(old.getRemark());
            DeviceStatusEnum deviceStatusEnum = DeviceStatusEnum.getByCode(old.getDeviceStatus());
            if (null!=deviceStatusEnum){
                vo.setDeveiceStatus(deviceStatusEnum.getName());
            }
        }else{
            BussinessException biz = new BussinessException("10000","工单不存在");
            throw biz;
        }
        return vo;
    }

    @Override
    public void validate(ValidateRepairReq param, AuthUser user) {
        TroubleRecord old = troubleRecordRepository.findOne(param.getTroubleRecordId());
        if (null!=old && old.getStatus() == TroubleStatusEnum.REPAIRED.getCode()){

            //推动流程进入下一步
            TroubleRecordUserRel nowStep =troubleRecordUserRelRepository.findByTroubleRecordIdAndCorporateIdentifyAndDealStepStatusAndDealUserIdAndDealPhase(old.getId(),user.getCorporateIdentify(),
                    0,user.getUserId(),2);
            if (null==nowStep){
                BussinessException biz = new BussinessException("10001","没有找到当前操作员验证流程数据，不允许验证");
                throw biz;
            }
            Integer nowStepNum = nowStep.getDealStep();
            Integer executeType = nowStep.getExecuteType();//执行类型，1:一人处理;2:全部处理

            List<TroubleRecordUserRel> nowStepRels = troubleRecordUserRelRepository.findByTroubleRecordIdAndCorporateIdentifyAndDealStepAndDealPhase(old.getId(),user.getCorporateIdentify(),nowStepNum,2);
            List<TroubleRecordUserRel> nextStepRels = troubleRecordUserRelRepository.findByTroubleRecordIdAndCorporateIdentifyAndDealStepAndDealPhase(old.getId(),user.getCorporateIdentify(),nowStepNum+1,2);
            if (CollectionUtils.isEmpty(nowStepRels)){
                BussinessException biz = new BussinessException("10001","无有效验证流程数据，不允许验证");
                throw biz;
            }
            if (1==executeType){
                //一个人执行
                if (param.getRepaired()==1){
                    //已经修复
                    nowStepRels.stream().forEach(troubleRecordUserRel -> {
                        //结束当前步骤所有流程
                        troubleRecordUserRel.setDealStepStatus(2);
                        troubleRecordUserRel.setDealStatus(TroubleStatusEnum.VALIDATED.getCode());
                        troubleRecordUserRel.setUpdateTime(new Date());
                    });
                    troubleRecordUserRelRepository.save(nowStepRels);
                    //启动下一步
                    if (CollectionUtils.isEmpty(nextStepRels)){
                        //没有下一步，直接结束流程，订单变成验证完成
                        old.setStatus(TroubleStatusEnum.VALIDATED.getCode());
                        old.setUpdateTime(new Date());
                        old.setRepaired(param.getRepaired());
                        old.setSuggest(param.getSuggest());
                        old.setStarLevel(param.getStarLevel());
                        troubleRecordRepository.save(old);
                    }else {
                        //将下一步流程设置为处理中
                        nextStepRels.stream().forEach(troubleRecordUserRel -> {
                            troubleRecordUserRel.setDealStepStatus(0);
                            troubleRecordUserRel.setUpdateTime(new Date());
                        });
                        troubleRecordUserRelRepository.save(nextStepRels);
                    }
                }else if (param.getRepaired() == 0){
                    //未修复
                    List<TroubleRecordUserRel> all = troubleRecordUserRelRepository.findByTroubleRecordIdAndCorporateIdentifyAndDealPhase(old.getId(),user.getCorporateIdentify(),2);
                    all.stream().forEach(troubleRecordUserRel -> {
                        //结束所有流程
                        troubleRecordUserRel.setDealStepStatus(2);
                        troubleRecordUserRel.setDealStatus(TroubleStatusEnum.VALIDATE_FAIL.getCode());
                        troubleRecordUserRel.setUpdateTime(new Date());
                    });
                    troubleRecordUserRelRepository.save(all);
                    //直接结束流程，订单变成验证完成
                    old.setStatus(TroubleStatusEnum.VALIDATE_FAIL.getCode());
                    old.setUpdateTime(new Date());
                    old.setRepaired(param.getRepaired());
                    old.setSuggest(param.getSuggest());
                    old.setStarLevel(param.getStarLevel());
                    troubleRecordRepository.save(old);
                }
            }else {
                //多人审核,当前步骤必须全部处理结束
                nowStep.setUpdateTime(new Date());
                nowStep.setDealStepStatus(2);
                if (param.getRepaired() == 1){
                    nowStep.setDealStatus(TroubleStatusEnum.VALIDATED.getCode());
                }else {
                    nowStep.setDealStatus(TroubleStatusEnum.VALIDATE_FAIL.getCode());
                }
                troubleRecordUserRelRepository.save(nowStep);

                nowStepRels = troubleRecordUserRelRepository.findByTroubleRecordIdAndCorporateIdentifyAndDealStepAndDealPhase(old.getId(),user.getCorporateIdentify(),nowStepNum,2);
                Integer allDealEnd = 0;
                Integer allPass = 0;
                for (TroubleRecordUserRel t : nowStepRels){
                    if (t.getDealStepStatus()==2){
                        allDealEnd +=1;
                        if (t.getDealStatus()==TroubleStatusEnum.VALIDATED.getCode()){
                            allPass +=1;
                        }
                    }
                }
                if (allDealEnd == nowStepRels.size()){
                    //当前步骤已经全部处理完成，判断处理结果
                    if (allPass == nowStepRels.size()){
                        //全部通过，开启下一步
                        //启动下一步
                        if (CollectionUtils.isEmpty(nextStepRels)){
                            //没有下一步，直接结束流程，订单变成验证结束
                            old.setStatus(TroubleStatusEnum.VALIDATED.getCode());
                            old.setUpdateTime(new Date());
                            old.setRepaired(param.getRepaired());
                            old.setSuggest(param.getSuggest());
                            old.setStarLevel(param.getStarLevel());
                            troubleRecordRepository.save(old);
                        }else {
                            //将下一步流程设置为处理中
                            nextStepRels.stream().forEach(troubleRecordUserRel -> {
                                troubleRecordUserRel.setDealStepStatus(0);
                                troubleRecordUserRel.setUpdateTime(new Date());
                            });
                            troubleRecordUserRelRepository.save(nextStepRels);
                        }
                    }else {
                        //有人拒绝，直接结束流程
                        List<TroubleRecordUserRel> all = troubleRecordUserRelRepository.findByTroubleRecordIdAndCorporateIdentifyAndDealPhase(old.getId(),user.getCorporateIdentify(),2);
                        all.stream().forEach(troubleRecordUserRel -> {
                            //结束所有流程
                            troubleRecordUserRel.setDealStepStatus(2);
                            troubleRecordUserRel.setDealStatus(TroubleStatusEnum.VALIDATE_FAIL.getCode());
                            troubleRecordUserRel.setUpdateTime(new Date());
                        });
                        troubleRecordUserRelRepository.save(all);
                        //直接结束流程，订单变成拒绝
                        old.setStatus(TroubleStatusEnum.VALIDATE_FAIL.getCode());
                        old.setUpdateTime(new Date());
                        old.setRepaired(param.getRepaired());
                        old.setSuggest(param.getSuggest());
                        old.setStarLevel(param.getStarLevel());
                        troubleRecordRepository.save(old);
                    }
                }
            }
        }else{
            BussinessException biz = new BussinessException("10000","工单不存在或状态不正确");
            throw biz;
        }
    }

    @Override
    public void audit(AuditWorkNumReq param, AuthUser user) {
        TroubleRecord old = troubleRecordRepository.findOne(param.getTroubleRecordId());
        if (null!=old && old.getStatus() == TroubleStatusEnum.WAIT_AUDIT.getCode()){
            //推动流程进入下一步
            TroubleRecordUserRel nowStep =troubleRecordUserRelRepository.findByTroubleRecordIdAndCorporateIdentifyAndDealStepStatusAndDealUserIdAndDealPhase(old.getId(),user.getCorporateIdentify(),
                    0,user.getUserId(),1);
           if (null==nowStep){
               BussinessException biz = new BussinessException("10001","没有找到当前操作员审核流程数据，不允许审核");
               throw biz;
           }
           Integer nowStepNum = nowStep.getDealStep();
           Integer executeType = nowStep.getExecuteType();//执行类型，1:一人处理;2:全部处理

           List<TroubleRecordUserRel> nowStepRels = troubleRecordUserRelRepository.findByTroubleRecordIdAndCorporateIdentifyAndDealStepAndDealPhase(old.getId(),user.getCorporateIdentify(),nowStepNum,1);
           List<TroubleRecordUserRel> nextStepRels = troubleRecordUserRelRepository.findByTroubleRecordIdAndCorporateIdentifyAndDealStepAndDealPhase(old.getId(),user.getCorporateIdentify(),nowStepNum+1,1);
           if (CollectionUtils.isEmpty(nowStepRels)){
               BussinessException biz = new BussinessException("10001","无有效审核流程数据，不允许审核");
               throw biz;
           }
           if (1==executeType){
               //一个人执行
               if (param.getDealStatus()==1){
                   //审核通过
                   nowStepRels.stream().forEach(troubleRecordUserRel -> {
                       //结束当前步骤所有流程
                       troubleRecordUserRel.setDealStepStatus(2);
                       troubleRecordUserRel.setDealStatus(TroubleStatusEnum.NEED_REPAIR.getCode());
                       troubleRecordUserRel.setUpdateTime(new Date());
                   });
                   troubleRecordUserRelRepository.save(nowStepRels);
                   //启动下一步
                   if (CollectionUtils.isEmpty(nextStepRels)){
                       //没有下一步，直接结束流程，订单变成等待维修
                       old.setStatus(TroubleStatusEnum.NEED_REPAIR.getCode());
                       old.setSuggest(param.getDealSuggest());
                       old.setUpdateTime(new Date());
                       troubleRecordRepository.save(old);
                   }else {
                       //将下一步流程设置为处理中
                       nextStepRels.stream().forEach(troubleRecordUserRel -> {
                           troubleRecordUserRel.setDealStepStatus(0);
                           troubleRecordUserRel.setUpdateTime(new Date());
                       });
                       troubleRecordUserRelRepository.save(nextStepRels);
                   }
               }else if (param.getDealStatus() == 2){
                   //审核拒绝
                   List<TroubleRecordUserRel> all = troubleRecordUserRelRepository.findByTroubleRecordIdAndCorporateIdentifyAndDealPhase(old.getId(),user.getCorporateIdentify(),1);
                   all.stream().forEach(troubleRecordUserRel -> {
                       //结束所有流程
                       troubleRecordUserRel.setDealStepStatus(2);
                       troubleRecordUserRel.setDealStatus(TroubleStatusEnum.REFUSED.getCode());
                       troubleRecordUserRel.setUpdateTime(new Date());
                   });
                   troubleRecordUserRelRepository.save(all);
                       //直接结束流程，订单变成拒绝
                       old.setStatus(TroubleStatusEnum.REFUSED.getCode());
                       old.setSuggest(param.getDealSuggest());
                       old.setUpdateTime(new Date());
                       troubleRecordRepository.save(old);
               }
           }else {
               //多人审核,当前步骤必须全部处理结束
               nowStep.setUpdateTime(new Date());
               nowStep.setDealStepStatus(2);
               if (param.getDealStatus() == 2){
                   nowStep.setDealStatus(TroubleStatusEnum.REFUSED.getCode());
               }else {
                   nowStep.setDealStatus(TroubleStatusEnum.NEED_REPAIR.getCode());
               }
               troubleRecordUserRelRepository.save(nowStep);

               nowStepRels = troubleRecordUserRelRepository.findByTroubleRecordIdAndCorporateIdentifyAndDealStepAndDealPhase(old.getId(),user.getCorporateIdentify(),nowStepNum,1);
               Integer allDealEnd = 0;
               Integer allPass = 0;
               for (TroubleRecordUserRel t : nowStepRels){
                   if (t.getDealStepStatus()==2){
                       allDealEnd +=1;
                       if (t.getDealStatus()==TroubleStatusEnum.NEED_REPAIR.getCode()){
                           allPass +=1;
                   }
                   }
               }
               if (allDealEnd == nowStepRels.size()){
                   //当前步骤已经全部处理完成，判断处理结果
                   if (allPass == nowStepRels.size()){
                       //全部通过，开启下一步
                       //启动下一步
                       if (CollectionUtils.isEmpty(nextStepRels)){
                           //没有下一步，直接结束流程，订单变成等待维修
                           old.setStatus(TroubleStatusEnum.NEED_REPAIR.getCode());
                           old.setSuggest(param.getDealSuggest());
                           old.setUpdateTime(new Date());
                           troubleRecordRepository.save(old);
                       }else {
                           //将下一步流程设置为处理中
                           nextStepRels.stream().forEach(troubleRecordUserRel -> {
                               troubleRecordUserRel.setDealStepStatus(0);
                               troubleRecordUserRel.setUpdateTime(new Date());
                           });
                           troubleRecordUserRelRepository.save(nextStepRels);
                       }
                   }else {
                       //有人拒绝，直接结束流程
                       List<TroubleRecordUserRel> all = troubleRecordUserRelRepository.findByTroubleRecordIdAndCorporateIdentifyAndDealPhase(old.getId(),user.getCorporateIdentify(),1);
                       all.stream().forEach(troubleRecordUserRel -> {
                           //结束所有流程
                           troubleRecordUserRel.setDealStepStatus(2);
                           troubleRecordUserRel.setDealStatus(TroubleStatusEnum.REFUSED.getCode());
                           troubleRecordUserRel.setUpdateTime(new Date());
                       });
                       troubleRecordUserRelRepository.save(all);
                       //直接结束流程，订单变成拒绝
                       old.setStatus(TroubleStatusEnum.REFUSED.getCode());
                       old.setSuggest(param.getDealSuggest());
                       old.setUpdateTime(new Date());
                       troubleRecordRepository.save(old);
                   }
               }
           }
        }else{
            BussinessException biz = new BussinessException("10000","工单不存在或状态不正确");
            throw biz;
        }
    }

    @Override
    public Pagination<SimpleRepairRecordVo> findRepairRecordByPage(TroubleListReq param) {
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
        Page<Object[]> page = troubleRecordRepository.getByPage(param.getDeviceId(),p);
        Pagination<SimpleRepairRecordVo> res = new Pagination(currentPage+1,itemsPerPage,page.getTotalElements());
        List<SimpleRepairRecordVo> list = new ArrayList<>();
        res.setList(list);
        if (page.hasContent()){
            page.getContent().stream().forEach(m -> {
                SimpleRepairRecordVo v = new SimpleRepairRecordVo();
                v.setId(((BigInteger)m[0]).longValue());
                v.setOrderNo(String.valueOf(m[1]));
                v.setCreateUser(String.valueOf(m[2]));
                v.setCreateTime(String.valueOf(m[3]));
                v.setEndTime(String.valueOf(m[4]));
                v.setRepairLevel(String.valueOf(m[5]));
                v.setRepairGroupName(String.valueOf(m[6]));
                v.setRepairUserName(String.valueOf(m[7]));
                v.setRemark(String.valueOf(m[8]));
                v.setWorkRemark(String.valueOf(m[9]));
                v.setAmount(String.valueOf(m[10]));
                list.add(v);
            });
        }
        return res;
    }

    @Override
    public IndexTroubleRecordCountVo getIndexTroubleCount() {
        Object[] rs = troubleRecordRepository.getTroubleCount();
        IndexTroubleRecordCountVo vo = new IndexTroubleRecordCountVo();
        if (null !=rs && rs.length>0){
            Object[] r1 = (Object[]) rs[0];
            vo.setCountDevice(Integer.parseInt(String.valueOf(r1[0])));
            vo.setCountTrouble(Integer.parseInt(String.valueOf(r1[1])));
            vo.setCountRepairing(Integer.parseInt(String.valueOf(r1[2])));
        }
        List<Object[]> list = troubleRecordRepository.getCountList();
        List<IndexSimpleTroubleRecord> li = new ArrayList<>();
        vo.setTroubleRecords(li);
        if (null != list && list.size()>0){
            list.stream().forEach(objects -> {
                IndexSimpleTroubleRecord v = new IndexSimpleTroubleRecord();
                v.setDeviceName(String.valueOf(objects[0]));
                v.setStatus(TroubleStatusEnum.getByCode(Integer.parseInt(String.valueOf(objects[1]))).getName());
                li.add(v);
            });
        }
        return vo;
    }
}
