package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.entity.User;
import tech.yozo.factoryrp.vo.req.*;
import tech.yozo.factoryrp.vo.resp.menu.MenuResp;
import tech.yozo.factoryrp.vo.resp.menu.MenuRoleResp;
import tech.yozo.factoryrp.vo.resp.role.RoleMenuQueryResp;
import tech.yozo.factoryrp.vo.resp.role.RoleResp;
import tech.yozo.factoryrp.vo.resp.user.UserAddResp;
import tech.yozo.factoryrp.vo.resp.user.UserRespWarpResp;
import tech.yozo.factoryrp.vo.resp.user.UserRoleResp;

import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description 权限相关服务
 */
public interface AuthorizationService {


    /**
     * 根据用户名进行查询
     * @param userName
     * @return
     */
    User findByUserName(String userName);

    /**
     * 根据用户名和企业唯一标识进行查找
     * @param username
     * @param corporateIdentify
     * @return
     */
    User queryUserByNameAndCorporateIdentify(String username, Long corporateIdentify);

    /**
     * 根据企业角色标识查询企业的所有用户
     * @param corporateIdentify
     * @return
     */
    UserRespWarpResp queryAllUserByCorporateIdentify(Long corporateIdentify);

    /**
     * 为用户添加角色
     * @param userRoleReq
     * @param corporateIdentify
     * @return
     */
    UserRoleResp addUserRole(UserRoleReq userRoleReq,Long corporateIdentify);

    /**
     * 根据企业唯一标识查询菜单
     * @param corporateIdentify
     * @return
     */
    List<MenuResp> queryMenuByCorporateIdentify(Long corporateIdentify);

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
     * @param corporateIdentify
     * @return
     */
    MenuRoleResp addMenuRole(MenuRoleReq menuRoleReq,Long corporateIdentify);

    /**
     * 根据企业标识码查询企业所有的角色
     * @param corporateIdentify
     * @return
     */
    List<RoleResp> queryRolesByCorporateIdentify(Long corporateIdentify);


    /**
     * 新增角色
     * @param roleReq
     * @param corporateIdentify
     * @return
     */
    RoleResp addRole(RoleReq roleReq, Long corporateIdentify);

    /**
     * 新增菜单
     * @param menuReq
     * @param corporateIdentify
     * @return
     */
    MenuResp addMenu(MenuReq menuReq,Long corporateIdentify);


    /**
     * 企业新增用户
     * @param userAddReq
     * @param corporateIdentify
     * @return
     */
    UserAddResp addUser(UserAddReq userAddReq,Long corporateIdentify);

}
