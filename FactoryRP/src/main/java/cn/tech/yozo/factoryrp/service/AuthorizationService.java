package cn.tech.yozo.factoryrp.service;

import cn.tech.yozo.factoryrp.vo.req.MenuReq;
import cn.tech.yozo.factoryrp.vo.req.MenuRoleReq;
import cn.tech.yozo.factoryrp.vo.req.RoleReq;
import cn.tech.yozo.factoryrp.vo.resp.MenuResp;
import cn.tech.yozo.factoryrp.vo.resp.MenuRoleResp;
import cn.tech.yozo.factoryrp.vo.resp.RoleMenuQueryResp;
import cn.tech.yozo.factoryrp.vo.resp.RoleResp;

import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description 权限相关服务
 */
public interface AuthorizationService {

    /**
     * 根据企业标识和角色id查询角色具备的菜单
     * @param roleId
     * @param corporateIdentify
     * @return
     */
    RoleMenuQueryResp queryByRoleIdAndCorporateIdentify(Long roleId, Long corporateIdentify);


    /**
     * 为角色新增能访问的菜单
     * @param menuRoleReq
     * @return
     */
    MenuRoleResp addMenuRole(MenuRoleReq menuRoleReq);

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

    /**
     * 新增菜单
     * @param menuReq
     * @return
     */
    MenuResp addMenu(MenuReq menuReq);

}
