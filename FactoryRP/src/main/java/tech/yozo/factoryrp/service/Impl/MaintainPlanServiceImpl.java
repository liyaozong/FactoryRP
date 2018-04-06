package tech.yozo.factoryrp.service.Impl;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.yozo.factoryrp.entity.*;
import tech.yozo.factoryrp.enums.PlanStatusEnum;
import tech.yozo.factoryrp.enums.device.DeviceParamDicEnum;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.repository.*;
import tech.yozo.factoryrp.service.MaintainPlanService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.DateTimeUtil;
import tech.yozo.factoryrp.utils.ReflectUtil;
import tech.yozo.factoryrp.vo.req.*;
import tech.yozo.factoryrp.vo.resp.*;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import tech.yozo.factoryrp.vo.resp.device.trouble.UsedSparePartsVo;
import tech.yozo.factoryrp.vo.resp.device.trouble.WorkTimeVo;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author chenxiang
 * @create 2018-03-02 下午2:26
 **/
@Service
public class MaintainPlanServiceImpl implements MaintainPlanService{

    @Autowired
    private MaintainPlanRepository maintainPlanRepository;
    @Autowired
    private DeviceParameterDictionaryRepository deviceParameterDictionaryRepository;
    @Autowired
    private RepairGroupRepository repairGroupRepository;
    @Autowired
    private MaintainRecordRepository maintainRecordRepository;
    @Autowired
    private WorkTimeRepository workTimeRepository;
    @Autowired
    private RepairRecordSparePartRelRepository repairRecordSparePartRelRepository;

    @Override
    public void addMaintainPlan(AddMaintainPlanReq plan, Long corporateIdentify, AuthUser AuthUser) {
        Long id = plan.getId();
        if (null!=id && 0!=id){
            //修改
            MaintainPlan old = maintainPlanRepository.getOne(id);
            if (null != old){
                BeanUtils.copyProperties(plan,old, ReflectUtil.getNullPropertyNames(plan));
                old.setUpdateTime(new Date());
                maintainPlanRepository.save(old);
            }else {
                BussinessException biz = new BussinessException("10000","保养计划不存在,修改失败");
                throw biz;
            }
        }else {
            //新增
            MaintainPlan maintainPlan = new MaintainPlan();
            BeanUtils.copyProperties(plan,maintainPlan);
            maintainPlan.setPlanStatus(PlanStatusEnum.TODAY.getCode());
            maintainPlan.setCorporateIdentify(corporateIdentify);
            maintainPlan.setCreateTime(new Date());
            maintainPlan.setUpdateTime(new Date());
            maintainPlanRepository.save(maintainPlan);
        }
    }

    @Override
    public Pagination<MaintainPlanListVo> findByPage(MaintainPlanListReq param,Long corporateIdentify) {
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


        Page<MaintainPlan> page = maintainPlanRepository.findByCorporateIdentify(corporateIdentify,p);
        Pagination<MaintainPlanListVo> res = new Pagination(currentPage+1,itemsPerPage,page.getTotalElements());
        if (page.hasContent()){
            List<MaintainPlanListVo> list = new ArrayList<>();
            page.getContent().forEach(maintainPlan -> {
                MaintainPlanListVo v = new MaintainPlanListVo();
                BeanUtils.copyProperties(maintainPlan,v);
                switch (maintainPlan.getCycleType()){
                    case 1:
                        v.setCycleType("单次");
                        break;
                    case 2:
                        v.setCycleType("循环多次");
                        break;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                v.setStartTime(sdf.format(maintainPlan.getPlanMaintainTimeStart()));
                v.setLastTime(sdf.format(maintainPlan.getLastMaintainTime()));
                v.setEndTime(sdf.format(maintainPlan.getPlanMaintainTimeEnd()));
                v.setPlanStatus(PlanStatusEnum.getByCode(maintainPlan.getPlanStatus()).getName());
                list.add(v);
            });
            res.setList(list);
        }
        return res;
    }

    @Override
    public Pagination<MaintainPlanListVo> findListByDeviceId(MaintainPlanListForDeviceReq param, Long corporateIdentify) {

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


        Page<MaintainPlan> page = maintainPlanRepository.findByCorporateIdentifyAndDeviceId(corporateIdentify,param.getDeviceId(),p);
        Pagination<MaintainPlanListVo> res = new Pagination(currentPage+1,itemsPerPage,page.getTotalElements());
        if (page.hasContent()){
            List<MaintainPlanListVo> list = new ArrayList<>();
            page.getContent().forEach(maintainPlan -> {
                MaintainPlanListVo v = new MaintainPlanListVo();
                BeanUtils.copyProperties(maintainPlan,v);
                switch (maintainPlan.getCycleType()){
                    case 1:
                        v.setCycleType("单次");
                        break;
                    case 2:
                        v.setCycleType("循环多次");
                        break;
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                v.setStartTime(sdf.format(maintainPlan.getPlanMaintainTimeStart()));
                v.setLastTime(sdf.format(maintainPlan.getLastMaintainTime()));
                v.setEndTime(sdf.format(maintainPlan.getPlanMaintainTimeEnd()));
                v.setPlanStatus(PlanStatusEnum.getByCode(maintainPlan.getPlanStatus()).getName());
                list.add(v);
            });
            res.setList(list);
        }
        return res;
    }

    @Override
    public Pagination<SimpleMaintainPlanVo> findSimpleListByPage(MaintainPlanListForAppReq param, Long corporateIdentify,AuthUser user) {
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
        Page<MaintainPlan> page = maintainPlanRepository.findAll(new Specification<MaintainPlan>() {
            @Override
            public Predicate toPredicate(Root<MaintainPlan> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> cons = new ArrayList<>();
                if (null==param.getPlanType()){
                    param.setPlanType(PlanStatusEnum.TODAY.getCode());
                }
                cons.add(criteriaBuilder.equal(root.get("planStatus").as(Integer.class),param.getPlanType()));
                if (!StringUtils.isEmpty(param.getDeviceName())){
                    cons.add(criteriaBuilder.like(root.get("deviceName").as(String.class),"%"+param.getDeviceName()+"%"));
                }
                if (!StringUtils.isEmpty(param.getDeviceCode())){
                    cons.add(criteriaBuilder.equal(root.get("deviceCode").as(String.class),param.getDeviceCode()));
                }
                if (!StringUtils.isEmpty(param.getDeviceSpec())){
                    cons.add(criteriaBuilder.equal(root.get("deviceSpec").as(String.class),param.getDeviceSpec()));
                }
                if (null!=param.getRepairGroupId()){
                    cons.add(criteriaBuilder.equal(root.get("repairGroupId").as(Long.class),param.getRepairGroupId()));
                }
                cons.add(criteriaBuilder.equal(root.get("corporateIdentify").as(Long.class),corporateIdentify));
                cons.add(criteriaBuilder.equal(root.get("planManagerId").as(Long.class),user.getUserId()));
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createTime")));
                Predicate[] ps = new Predicate[cons.size()];
                ps = cons.toArray(ps);
                return criteriaBuilder.and(ps);
            }
        },p);
        Pagination<SimpleMaintainPlanVo> res = new Pagination<>(currentPage+1,itemsPerPage,page.getTotalElements());
        if (page.hasContent()){
            List<SimpleMaintainPlanVo> rel = new ArrayList<>();
            res.setList(rel);
            List<MaintainPlan> list = page.getContent();
            Map<Integer, String> maintainLevels = getMaintainLevelMap(corporateIdentify);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            list.stream().forEach(maintainPlan -> {
                SimpleMaintainPlanVo vo = new SimpleMaintainPlanVo();
                vo.setId(maintainPlan.getId());
                vo.setDeviceCode(maintainPlan.getDeviceCode());
                vo.setDeviceName(maintainPlan.getDeviceName());
                vo.setDeviceSpec(maintainPlan.getDeviceSpec());
                vo.setMaintainLevel(maintainLevels.get(maintainPlan.getMaintainLevel()));
                vo.setNexMaintainTime(sdf.format(maintainPlan.getPlanMaintainTimeStart())+"至"+sdf.format(maintainPlan.getPlanMaintainTimeEnd()));
                vo.setPlanManagerName(maintainPlan.getPlanManagerName());
                rel.add(vo);
            });
        }
        return res;
    }

    private Map<Integer, String> getMaintainLevelMap(Long corporateIdentify) {
        List<DeviceParameterDictionary> deviceParameterDictionaryList = deviceParameterDictionaryRepository.findByCodeAndCorporateIdentify(DeviceParamDicEnum.DEVICE_PARAM_MAINTENANCE_LEVEL.getCode(), corporateIdentify);
        Map<Integer,String> maintainLevels = new HashMap<>();
        if (null!=deviceParameterDictionaryList && deviceParameterDictionaryList.size()>0){
            deviceParameterDictionaryList.stream().forEach(deviceParameterDictionary -> {
                maintainLevels.put(deviceParameterDictionary.getType(),deviceParameterDictionary.getName());
            });
        }
        return maintainLevels;
    }

    @Override
    public AddMaintainPlanReq getDetailById(Long id) {
        MaintainPlan maintainPlan = maintainPlanRepository.getOne(id);
        AddMaintainPlanReq vo = new AddMaintainPlanReq();
        BeanUtils.copyProperties(maintainPlan,vo);
        return vo;
    }

    @Override
    public MaintainPlanCountVo getCount(Long corporateIdentify,AuthUser user) {
        Long todayCount = maintainPlanRepository.count(new Specification<MaintainPlan>() {
            @Override
            public Predicate toPredicate(Root<MaintainPlan> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> listCon = new ArrayList<>();
                listCon.add(criteriaBuilder.equal(root.get("planStatus").as(Long.class),PlanStatusEnum.TODAY.getCode()));
                listCon.add(criteriaBuilder.equal(root.get("corporateIdentify").as(Long.class),corporateIdentify));
                listCon.add(criteriaBuilder.equal(root.get("planManagerId").as(Long.class),user.getUserId()));
                Predicate[] predicates = new Predicate[listCon.size()];
                predicates = listCon.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        });
        Long tomorrowCount = maintainPlanRepository.count(new Specification<MaintainPlan>() {
            @Override
            public Predicate toPredicate(Root<MaintainPlan> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> listCon = new ArrayList<>();
                listCon.add(criteriaBuilder.equal(root.get("planStatus").as(Long.class),PlanStatusEnum.TOMO.getCode()));
                listCon.add(criteriaBuilder.equal(root.get("corporateIdentify").as(Long.class),corporateIdentify));
                listCon.add(criteriaBuilder.equal(root.get("planManagerId").as(Long.class),user.getUserId()));
                Predicate[] predicates = new Predicate[listCon.size()];
                predicates = listCon.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        });
        Long expiredCount = maintainPlanRepository.count(new Specification<MaintainPlan>() {
            @Override
            public Predicate toPredicate(Root<MaintainPlan> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> listCon = new ArrayList<>();
                listCon.add(criteriaBuilder.equal(root.get("planStatus").as(Long.class),PlanStatusEnum.EXPIRED.getCode()));
                listCon.add(criteriaBuilder.equal(root.get("corporateIdentify").as(Long.class),corporateIdentify));
                listCon.add(criteriaBuilder.equal(root.get("planManagerId").as(Long.class),user.getUserId()));
                Predicate[] predicates = new Predicate[listCon.size()];
                predicates = listCon.toArray(predicates);
                return criteriaBuilder.and(predicates);
            }
        });
        MaintainPlanCountVo vo = new MaintainPlanCountVo();
        vo.setTodayPlanNum(todayCount);
        vo.setTomorrowPlanNum(tomorrowCount);
        vo.setExpiredNum(expiredCount);
        return vo;
    }

    @Override
    public MaintainPlanAppQueryVo getDetail(Long id, AuthUser user) {
        MaintainPlanAppQueryVo vo = new MaintainPlanAppQueryVo();
        //查询保养计划信息
        MaintainPlan maintainPlan = maintainPlanRepository.getOne(id);
        if (null != maintainPlan){
            Map<Integer, String> maintainLevels = getMaintainLevelMap(user.getCorporateIdentify());
            vo.setMaintainPlanId(maintainPlan.getId());
            vo.setDeviceName(maintainPlan.getDeviceName());
            vo.setDeviceCode(maintainPlan.getDeviceCode());
            vo.setDeviceSpec(maintainPlan.getDeviceSpec());
            vo.setDeviceTypeName(maintainPlan.getDeviceTypeName());
            vo.setDeviceUseDeptName(maintainPlan.getDeviceUseDeptName());
            vo.setMaintainLevel(maintainPlan.getMaintainLevel());
            vo.setMaintainLevelString(maintainLevels.get(maintainPlan.getMaintainLevel()));
            vo.setRepairGroupId(maintainPlan.getRepairGroupId());
            RepairGroup repairGroup = repairGroupRepository.getOne(maintainPlan.getRepairGroupId());
            if (null!=repairGroup){
                vo.setRepairGroupName(repairGroup.getName());
            }
            switch (maintainPlan.getCycleType()){
                case 1:
                    vo.setCycleType("单次");
                    break;
                case 2:
                    vo.setCycleType("多次");
                    break;
            }
            vo.setCycleTime(maintainPlan.getCycleTimeValue()+maintainPlan.getCycleTimeUnit());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            vo.setLastTime(sdf.format(maintainPlan.getLastMaintainTime()));
            vo.setNexMaintainTime(sdf.format(maintainPlan.getPlanMaintainTimeStart())+"至"+sdf.format(maintainPlan.getPlanMaintainTimeEnd()));
            vo.setMaintainPart(maintainPlan.getMaintainPart());
            vo.setMaintainStandard(maintainPlan.getMaintainStandard());
            vo.setPlanManagerName(maintainPlan.getPlanManagerName());
            vo.setPlanRemark(maintainPlan.getPlanRemark());
        }else{
            BussinessException biz = new BussinessException("10000","保养计划不存在");
            throw biz;
        }
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void appSubmit(MaintainDetailSubmitReq param,AuthUser user) {
        MaintainPlan maintainPlan = maintainPlanRepository.getOne(param.getMaintainPlanId());
        if (null!=maintainPlan){
            if (Long.compare(maintainPlan.getPlanManagerId(),user.getUserId())!=0){
                BussinessException biz = new BussinessException("10001","不是本人处理计划，无权限操作");
                throw biz;
            }
            maintainPlan.setPlanStatus(PlanStatusEnum.OVER.getCode());
            maintainPlan.setUpdateTime(new Date());
            maintainPlan.setLastMaintainTime(new Date());
            String unit = maintainPlan.getCycleTimeUnit();
            Integer cyT = Integer.parseInt(maintainPlan.getCycleTimeValue());
            Calendar startTime = Calendar.getInstance();
            startTime.setTime(new Date());
            Calendar endTime = Calendar.getInstance();
            endTime.setTime(maintainPlan.getPlanMaintainTimeEnd());
            if ("天".equals(unit)){
                startTime.add(Calendar.DAY_OF_MONTH,cyT);
            }else if("月".equals(unit)){
                startTime.add(Calendar.MONTH,cyT);
            }else if(("年".equals(unit))){
                startTime.add(Calendar.YEAR,cyT);
            }
            if (startTime.after(endTime)){
                maintainPlan.setPlanStatus(PlanStatusEnum.EXPIRED.getCode());
            }else {
                maintainPlan.setPlanMaintainTimeStart(startTime.getTime());
            }
            if (!StringUtils.isEmpty(param.getMaintainPart())){
                maintainPlan.setMaintainPart(param.getMaintainPart());
            }
            maintainPlanRepository.save(maintainPlan);
            //增加保养记录
            MaintainRecord maintainRecord = new MaintainRecord();
            maintainRecord.setMaintainPlanId(maintainPlan.getId());
            maintainRecord.setMaintainStatus(param.getMaintainStatus());
            maintainRecord.setMaintainNo("BY"+new Date().getTime());
            maintainRecord.setMaintainContent(param.getMaintainRemark());
            maintainRecord.setStoped(param.getStoped());
            maintainRecord.setStopedHour(param.getStopedHour());
            maintainRecord.setMaintainAmount(param.getMaintainAmount());
            maintainRecord.setCostHour(param.getCostHour());
            maintainRecord.setCorporateIdentify(user.getCorporateIdentify());
            maintainRecord.setCreateTime(new Date());
            maintainRecordRepository.save(maintainRecord);

            List<WorkTimeVo> workTimeVos = param.getWorkTimes();
            if(!CheckParam.isNull(workTimeVos)){
                List<WorkTime> needSave = new ArrayList<>();
                workTimeVos.stream().forEach(workTimeVo -> {
                    WorkTime wt = new WorkTime();
                    wt.setCostHour(workTimeVo.getCostHour());
                    wt.setEndTime(DateTimeUtil.strToDate(workTimeVo.getEndTime()));
                    wt.setStartTime(DateTimeUtil.strToDate(workTimeVo.getStartTime()));
                    wt.setRepairRecordId(maintainRecord.getId());
                    wt.setRepairUserId(workTimeVo.getRepairUserId());
                    wt.setRepairUserName(workTimeVo.getRepairUserName());
                    wt.setCorporateIdentify(user.getCorporateIdentify());
                    wt.setType(2);
                    needSave.add(wt);
                });
                //保存工时信息
                List<WorkTime> olds = workTimeRepository.findByRepairRecordIdAndType(maintainRecord.getId(),2);
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
                    rrp.setRepairRecordId(maintainRecord.getId());
                    rrp.setName(rr.getName());
                    rrp.setCode(rr.getCode());
                    rrp.setSpecificationsAndodels(rr.getSpecificationsAndodels());
                    rrp.setInventoryUpperLimit(rr.getInventoryUpperLimit());
                    rrp.setOldOrderNum(rr.getOldOrderNum());
                    rrp.setNewOrderNum(rr.getNewOrderNum());
                    rrp.setAmount(rr.getAmount());
                    rrp.setCorporateIdentify(user.getCorporateIdentify());
                    rrp.setType(2);
                    needSave.add(rrp);
                });
                List<RepairRecordSparePartRel> ols = repairRecordSparePartRelRepository.findByRepairRecordIdAndType(maintainRecord.getId(),2);
                if (!CheckParam.isNull(ols)){
                    //清空老数据
                    repairRecordSparePartRelRepository.delete(ols);
                }
                if (!CheckParam.isNull(needSave)){
                    //保存新数据
                    repairRecordSparePartRelRepository.save(needSave);
                }
            }
        }else{
            BussinessException biz = new BussinessException("10000","保养计划不存在");
            throw biz;
        }
    }

    @Override
    public void batchDelete(List<Long> ids) {
        List<MaintainPlan> oldList = maintainPlanRepository.findAll(ids);
        if (!CheckParam.isNull(oldList)){
            maintainPlanRepository.deleteInBatch(oldList);
        }
    }

    @Override
    public Pagination<SimpleMaintainRecordVo> findSimpleRecordListByPage(TroubleListReq param) {

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
        Page<Object[]> page = maintainRecordRepository.getByPage(param.getDeviceId(),p);
        Pagination<SimpleMaintainRecordVo> res = new Pagination(currentPage+1,itemsPerPage,page.getTotalElements());
        List<SimpleMaintainRecordVo> list = new ArrayList<>();
        res.setList(list);
        if (page.hasContent()){
            page.getContent().stream().forEach(m -> {
                SimpleMaintainRecordVo v = new SimpleMaintainRecordVo();
                v.setId(((BigInteger)m[0]).longValue());
                v.setMaintainNo(String.valueOf(m[1]));
                v.setEndTime(String.valueOf(m[2]));
                v.setMaintainLevel(String.valueOf(m[3]));
                v.setRepairGroupName(String.valueOf(m[4]));
                v.setPlanManagerName(String.valueOf(m[5]));
                v.setPlanRemark(String.valueOf(m[6]));
                v.setMaintainContent(String.valueOf(m[7]));
                v.setMaintainAmount(String.valueOf(m[8]));
                list.add(v);
            });
        }
        return res;
    }

    @Override
    public IndexMaintainPlanCountVo getIndexPlanCount() {

        Object[] rs = maintainRecordRepository.getMaintainCount();
        IndexMaintainPlanCountVo vo = new IndexMaintainPlanCountVo();
        if (null !=rs && rs.length>0){
            Object[] r1 = (Object[]) rs[0];
            vo.setTodayPlanNum(Integer.parseInt(String.valueOf(r1[0])));
            vo.setExecutedPlanNum(Integer.parseInt(String.valueOf(r1[1])));
            vo.setNotExecutedPlanNum(Integer.parseInt(String.valueOf(r1[2])));
        }
        List<Object[]> list = maintainRecordRepository.getCountList();
        List<IndexSimpleMaintainPlanVo> li = new ArrayList<>();
        vo.setPlans(li);
        if (null != list && list.size()>0){
            list.stream().forEach(objects -> {
                IndexSimpleMaintainPlanVo v = new IndexSimpleMaintainPlanVo();
                v.setDeviceName(String.valueOf(objects[0]));
                v.setStatus(PlanStatusEnum.getByCode(Integer.parseInt(String.valueOf(objects[1]))).getName());
                li.add(v);
            });
        }
        return vo;
    }

    public static void main(String args[]){
        Calendar nowDate = Calendar.getInstance();
        nowDate.setTime(new Date());
        Calendar nowDate2 = Calendar.getInstance();
        nowDate2.setTime(new Date());
        nowDate.add(Calendar.DAY_OF_MONTH,2);
        System.out.println(nowDate.getTime());
        System.out.println(nowDate.after(nowDate2));
    }
}
