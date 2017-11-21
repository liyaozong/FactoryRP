package cn.tech.yozo.factoryrp.service;

import cn.tech.yozo.factoryrp.vo.req.RoleReq;
import cn.tech.yozo.factoryrp.vo.resp.RoleResp;

import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description 权限相关服务
 */
public interface AuthorizationService {


    /**
     * 根据企业标识码查询企业所有的角色
     * @param corporateIdentify
     * @return
     */
    List<RoleResp> queryRolesByorporateIdentify(String corporateIdentify);


    /**
     * 新增角色
     * @param roleReq
     * @return
     */
    RoleResp addRole(RoleReq roleReq);

}
