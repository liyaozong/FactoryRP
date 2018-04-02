package tech.yozo.factoryrp.service;

import tech.yozo.factoryrp.entity.DeviceProcessType;
import tech.yozo.factoryrp.entity.User;
import tech.yozo.factoryrp.vo.req.*;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
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
     * 根据角色code查询用户信息
     * @param roleCode
     * @param corporateIdentify
     * @return
     */
    UserRespWarpResp queryUserByRoleCode(String roleCode,Long corporateIdentify);

    /**
     * 根据企业标识码和企业code进行查找
     * @param userName
     * @param corporateCode
     * @return
     */
    User findByUserNameAndCorporateCode(String userName,String corporateCode);

    /**
     * 删除指定用户下面指定的角色信息
     * @param userRoleDeleteReq
     * @param corporateIdentify
     */
    void deleteUserRoleByUserId(UserRoleDeleteReq userRoleDeleteReq,Long corporateIdentify);

    /**
     * 删除当前角色下面指定的菜单信息
     * @param menuRoleDeleteReq
     * @param corporateIdentify
     */
    void deleteMenuRoleByRoleId(MenuRoleDeleteReq menuRoleDeleteReq,Long corporateIdentify);

    /**
     * 删除角色 需要删除角色和用户关联 需要删除角色和菜单关联
     * @param roleId
     * @param authUser
     */
    void deleteRole(Long roleId, AuthUser authUser);


    /**
     * 删除菜单 需要删除菜单信息 删除菜单和角色关联信息
     * @param menuId
     * @param authUser
     */
    void deleteMenu(Long menuId, AuthUser authUser);


    /**
     * 删除用户 需要删除用户相关角色
     * @param userId
     * @param authUser
     */
    void deleteUser(Long userId, AuthUser authUser);


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
    RoleMenuQueryResp queryMenusByRoleId(Long roleId, Long corporateIdentify);


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
     * 修改当前用户密码
     * @param newPassword
     * @param oldPassword
     * @param userId
     * @param corporateIdentify
     */
    void updateCurrentUserPassword(String newPassword,String oldPassword,Long userId, Long corporateIdentify);

    /**
     * 根据用户id查询用户角色
     * @param userId
     * @param corporateIdentify
     * @return
     */
    List<RoleResp> queryRoleByUserId(Long userId, Long corporateIdentify);

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
