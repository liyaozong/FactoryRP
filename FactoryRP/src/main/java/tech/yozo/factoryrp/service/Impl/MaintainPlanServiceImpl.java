package tech.yozo.factoryrp.service.Impl;

import com.alibaba.druid.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.DeviceParameterDictionary;
import tech.yozo.factoryrp.entity.MaintainPlan;
import tech.yozo.factoryrp.enums.PlanStatusEnum;
import tech.yozo.factoryrp.enums.device.DeviceParamDicEnum;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.repository.DeviceParameterDictionaryRepository;
import tech.yozo.factoryrp.repository.MaintainPlanRepository;
import tech.yozo.factoryrp.service.DepartmentService;
import tech.yozo.factoryrp.service.DeviceTypeService;
import tech.yozo.factoryrp.service.MaintainPlanService;
import tech.yozo.factoryrp.vo.req.AddMaintainPlanReq;
import tech.yozo.factoryrp.vo.req.MaintainPlanListForAppReq;
import tech.yozo.factoryrp.vo.req.MaintainPlanListReq;
import tech.yozo.factoryrp.vo.resp.MaintainPlanDetailVo;
import tech.yozo.factoryrp.vo.resp.MaintainPlanListVo;
import tech.yozo.factoryrp.vo.resp.SimpleMaintainPlanVo;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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
    private DeviceTypeService deviceTypeService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DeviceParameterDictionaryRepository deviceParameterDictionaryRepository;

    @Override
    public void addMaintainPlan(AddMaintainPlanReq plan, Long corporateIdentify, AuthUser AuthUser) {
        MaintainPlan maintainPlan = new MaintainPlan();
        BeanUtils.copyProperties(plan,maintainPlan);
        maintainPlan.setPlanStatus(PlanStatusEnum.TODAY.getCode());
        maintainPlan.setCorporateIdentify(corporateIdentify);
        maintainPlan.setCreateTime(new Date());
        maintainPlan.setUpdateTime(new Date());
        maintainPlanRepository.save(maintainPlan);
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
    public Pagination<SimpleMaintainPlanVo> findSimpleListByPage(MaintainPlanListForAppReq param, Long corporateIdentify) {
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
            List<DeviceParameterDictionary> deviceParameterDictionaryList = deviceParameterDictionaryRepository.findByCodeAndCorporateIdentify(DeviceParamDicEnum.DEVICE_PARAM_MAINTENANCE_LEVEL.getCode(), corporateIdentify);
            Map<Integer,String> maintainLevels = new HashMap<>();
            if (null!=deviceParameterDictionaryList && deviceParameterDictionaryList.size()>0){
                deviceParameterDictionaryList.stream().forEach(deviceParameterDictionary -> {
                    maintainLevels.put(deviceParameterDictionary.getType(),deviceParameterDictionary.getName());
                });
            }
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

    @Override
    public MaintainPlanDetailVo getDetailById(Long id) {
        MaintainPlan maintainPlan = maintainPlanRepository.getOne(id);
        MaintainPlanDetailVo vo = new MaintainPlanDetailVo();
        BeanUtils.copyProperties(maintainPlan,vo);
        return vo;
    }
}
