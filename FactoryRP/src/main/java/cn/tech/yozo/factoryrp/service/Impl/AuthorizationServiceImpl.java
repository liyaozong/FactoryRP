package cn.tech.yozo.factoryrp.service.Impl;

import cn.tech.yozo.factoryrp.entity.Corporate;
import cn.tech.yozo.factoryrp.entity.Role;
import cn.tech.yozo.factoryrp.repository.CorporateRepository;
import cn.tech.yozo.factoryrp.repository.RoleRepository;
import cn.tech.yozo.factoryrp.service.AuthorizationService;
import cn.tech.yozo.factoryrp.utils.CheckParam;
import cn.tech.yozo.factoryrp.utils.UUIDSequenceWorker;
import cn.tech.yozo.factoryrp.vo.req.CorporateReq;
import cn.tech.yozo.factoryrp.vo.resp.CorporateResp;
import cn.tech.yozo.factoryrp.vo.resp.RoleResp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description 权限相关服务
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {


    @Resource
    private RoleRepository roleRepository;

    @Resource
    private CorporateRepository corporateRepository;

    /**
     * 根据企业标识码查询企业所有的角色
     * @param corporateIdentify
     * @return
     */
    public List<RoleResp> queryRolesByorporateIdentify(String corporateIdentify){

        List<Role> roleList = roleRepository.findByCorporateIdentify(Long.parseLong(corporateIdentify));



        if(!CheckParam.isNull(roleList)){
            List<RoleResp> roleResps = new ArrayList<>();
            roleList.stream().forEach(r1 ->{
                RoleResp roleResp = new RoleResp();
                roleResp.setCorporateIdentify(String.valueOf(r1.getCorporateIdentify()));
                roleResp.setEnableStatus(r1.getEnableStatus());
                roleResp.setRoleCode(r1.getRoleCode());
                roleResp.setRoleDescription(r1.getRoleDescription());
                roleResp.setRoleId(String.valueOf(r1.getId()));
                roleResp.setRoleName(r1.getRoleName());

                roleResps.add(roleResp);
            });
            return roleResps;
        }
            return null;
    }


    /**
     * 新增企业
     * @param corporateReq
     * @return
     */
    public CorporateResp addCorporate(CorporateReq corporateReq){

        Corporate corporate = corporateRepository.findByCorporateName(corporateReq.getCorporateName());

        if(CheckParam.isNull(corporate)){
            corporate = new Corporate();
            corporate.setCorporateName(corporateReq.getCorporateName());
            corporate.setCorporateIdentify(UUIDSequenceWorker.uniqueSequenceId());
            corporate.setEnableStatus(Integer.valueOf(corporateReq.getEnableStatus()));

            corporateRepository.save(corporate);

            CorporateResp corporateResp = new CorporateResp();
            corporateResp.setCorporateName(corporate.getCorporateName());
            corporateResp.setEnableStatus(String.valueOf(corporate.getEnableStatus()));
            corporateResp.setCorporateIdentify(String.valueOf(corporate.getCorporateIdentify()));

            return corporateResp;
        }

            return null;
    }

}
