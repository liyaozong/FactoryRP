package tech.yozo.factoryrp.service.Impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.Department;
import tech.yozo.factoryrp.entity.DeviceInfo;
import tech.yozo.factoryrp.entity.DeviceType;
import tech.yozo.factoryrp.entity.MaintainPlan;
import tech.yozo.factoryrp.page.Pagination;
import tech.yozo.factoryrp.repository.MaintainPlanRepository;
import tech.yozo.factoryrp.service.DepartmentService;
import tech.yozo.factoryrp.service.DeviceTypeService;
import tech.yozo.factoryrp.service.MaintainPlanService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.vo.req.AddMaintainPlanReq;
import tech.yozo.factoryrp.vo.req.MaintainPlanListReq;
import tech.yozo.factoryrp.vo.resp.MaintainPlanDetailVo;
import tech.yozo.factoryrp.vo.resp.MaintainPlanListVo;
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

    @Override
    public void addMaintainPlan(AddMaintainPlanReq plan, Long corporateIdentify, AuthUser AuthUser) {
        MaintainPlan maintainPlan = new MaintainPlan();
        BeanUtils.copyProperties(plan,maintainPlan);
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setId(plan.getDeveiceId());
        maintainPlan.setDeviceInfo(deviceInfo);
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
            List<DeviceType> deviceTypeList =  deviceTypeService.list(corporateIdentify);
            Map<Long,String> dtMap = new HashMap<>();
            if (!CheckParam.isNull(deviceTypeList)){
                deviceTypeList.forEach(deviceType -> {
                    dtMap.put(deviceType.getId(),deviceType.getName());
                });
            }
            List<Department> listDept = departmentService.list(corporateIdentify);
            Map<Long,String> deptMap = new HashMap<>();
            if(!CheckParam.isNull(listDept)){
                listDept.forEach(department -> {
                    deptMap.put(department.getId(),department.getName());
                });
            }
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
                v.setDeviceAddress(maintainPlan.getDeviceInfo().getInstallationAddress());
                v.setDeviceCode(maintainPlan.getDeviceInfo().getCode());
                v.setDeviceName(maintainPlan.getDeviceInfo().getName());
                v.setDeviceSpec(maintainPlan.getDeviceInfo().getSpecification());
                v.setDeviceType(dtMap.get(maintainPlan.getDeviceInfo().getDeviceType()));
                v.setDeviceUseDept(deptMap.get(maintainPlan.getDeviceInfo().getUseDept()));
                Date now = new Date();
                Calendar calendarStart = Calendar.getInstance();
                calendarStart.setTime(maintainPlan.getPlanMaintainTimeStart());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                v.setStartTime(sdf.format(maintainPlan.getPlanMaintainTimeStart()));
                v.setLastTime(sdf.format(maintainPlan.getLastMaintainTime()));
                v.setEndTime(sdf.format(maintainPlan.getPlanMaintainTimeEnd()));
                Calendar calendarEnd = Calendar.getInstance();
                calendarEnd.setTime(maintainPlan.getPlanMaintainTimeEnd());
                Calendar calendarNow = Calendar.getInstance();
                calendarNow.setTime(now);
                if (calendarEnd.before(calendarNow)){
                    v.setPlanStatus("已过期");
                }else if (calendarStart.get(Calendar.DAY_OF_MONTH)==calendarNow.get(Calendar.DAY_OF_MONTH)
                        && calendarStart.get(Calendar.MONTH)==calendarNow.get(Calendar.MONTH)
                        && calendarStart.get(Calendar.YEAR)==calendarNow.get(Calendar.YEAR)){
                    v.setPlanStatus("今日计划");
                }else if (calendarStart.get(Calendar.YEAR)==calendarNow.get(Calendar.YEAR)
                        && calendarStart.get(Calendar.MONTH)==calendarNow.get(Calendar.MONTH)){
                    v.setPlanStatus("当月计划");
                }

                list.add(v);
            });
            res.setList(list);
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
