package tech.yozo.factoryrp.service.Impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.DeviceInfo;
import tech.yozo.factoryrp.entity.MaintainPlan;
import tech.yozo.factoryrp.repository.MaintainPlanRepository;
import tech.yozo.factoryrp.service.MaintainPlanService;
import tech.yozo.factoryrp.vo.req.AddMaintainPlanReq;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;

import java.util.Date;

/**
 * @author chenxiang
 * @create 2018-03-02 下午2:26
 **/
@Service
public class MaintainPlanServiceImpl implements MaintainPlanService{

    @Autowired
    private MaintainPlanRepository maintainPlanRepository;
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
}
