package tech.yozo.factoryrp.service.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.yozo.factoryrp.entity.*;
import tech.yozo.factoryrp.exception.BussinessException;
import tech.yozo.factoryrp.repository.*;
import tech.yozo.factoryrp.service.AuthorizationService;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.EncryptUtils;
import tech.yozo.factoryrp.utils.ErrorCode;
import tech.yozo.factoryrp.utils.UUIDSequenceWorker;
import tech.yozo.factoryrp.vo.req.*;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import tech.yozo.factoryrp.vo.resp.menu.MenuQueryResp;
import tech.yozo.factoryrp.vo.resp.menu.MenuResp;
import tech.yozo.factoryrp.vo.resp.menu.MenuRoleResp;
import tech.yozo.factoryrp.vo.resp.role.RoleMenuQueryResp;
import tech.yozo.factoryrp.vo.resp.role.RoleResp;
import tech.yozo.factoryrp.vo.resp.user.UserAddResp;
import tech.yozo.factoryrp.vo.resp.user.UserResp;
import tech.yozo.factoryrp.vo.resp.user.UserRespWarpResp;
import tech.yozo.factoryrp.vo.resp.user.UserRoleResp;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description 权限相关服务
 */
@Service("authorizationService")
public class AuthorizationServiceImpl implements AuthorizationService {


    @Resource
    private RoleRepository roleRepository;

    @Resource
    private CorporateRepository corporateRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private PermissionRepository permissionRepository;

    @Resource
    private MenuRepository menuRepository;

    @Resource
    private MenuRoleRepository menuRoleRepository;

    @Resource
    private UserRoleRepository userRoleRepository;


    private static Logger logger = LoggerFactory.getLogger(AuthorizationServiceImpl.class);


    /**
     * 根据角色code查询用户信息
     * @param roleCode
     * @param corporateIdentify
     * @return
     */
    public UserRespWarpResp queryUserByRoleCode(String roleCode,Long corporateIdentify){
        Role role = roleRepository.findByRoleCodeAndCorporateIdentify(roleCode, corporateIdentify);

        if(!CheckParam.isNull(role)){

            UserRespWarpResp userRespWarpResp = new UserRespWarpResp();

            List<UserResp> userRespList = new ArrayList<>();

            role.getUserList().stream().forEach(r1 -> {
                UserResp userResp = new UserResp();
                userResp.setUserId(r1.getId());
                userResp.setUserName(r1.getUserName());

                userRespList.add(userResp);
            });

            userRespWarpResp.setUserRespList(userRespList);

            return userRespWarpResp;
        }

        return null;
    }


    /**
     * 删除指定用户下面指定的角色信息
     * @param userRoleDeleteReq
     * @param corporateIdentify
     */
    public void deleteUserRoleByUserId(UserRoleDeleteReq userRoleDeleteReq,Long corporateIdentify){

        List<UserRole> userRoleList = userRoleRepository.findByUserIdAndCorporateIdentifyAndRoleIdIn(userRoleDeleteReq.getUserId(),
                corporateIdentify, userRoleDeleteReq.getRoleList());

        if(!CheckParam.isNull(userRoleList) && !userRoleList.isEmpty()){
            userRoleRepository.deleteInBatch(userRoleList);
        }

    }

    /**
     * 删除指定角色下面指定的菜单信息
     * @param menuRoleDeleteReq
     * @param corporateIdentify
     */
    public void deleteMenuRoleByRoleId(MenuRoleDeleteReq menuRoleDeleteReq,Long corporateIdentify){

        List<MenuRole> menuRoleList = menuRoleRepository.findByRoleIdAndCorporateIdentifyAndRoleIdIn(menuRoleDeleteReq.getRoleId(),
                corporateIdentify, menuRoleDeleteReq.getMenuList());

        if(!CheckParam.isNull(menuRoleList) && !menuRoleList.isEmpty()){
            menuRoleRepository.deleteInBatch(menuRoleList);
        }


    }


    /**
     * 删除菜单 需要删除菜单信息 删除菜单和角色关联信息
     * 还需要删除子菜单
     * @param menuId
     * @param authUser
     */
    public void deleteMenu(Long menuId, AuthUser authUser){

        Menu menu = menuRepository.findOne(menuId);

        if(CheckParam.isNull(menu)){
            throw new BussinessException(ErrorCode.MENU_NOTEXIST_ERROR.getCode(),ErrorCode.MENU_NOTEXIST_ERROR.getMessage());
        }

        Long corporateIdentify = authUser.getCorporateIdentify();

        List<MenuRole> menuRoleList = menuRoleRepository.findByMenuIdAndCorporateIdentify(menuId, corporateIdentify);

        if(null != menuRoleList && !menuRoleList.isEmpty()){
            menuRoleRepository.deleteInBatch(menuRoleList);
        }

        List<Menu> menuList = menuRepository.findByAndCorporateIdentifyAndParentId(authUser.getCorporateIdentify(), menuId);

        if(null != menuList && !menuList.isEmpty()){
            menuRepository.deleteInBatch(menuList);
        }

        menuRepository.delete(menu);

    }


    /**
     * 删除角色 需要删除角色和用户关联 需要删除角色和菜单关联
     * @param roleId
     * @param authUser
     */
    public void deleteRole(Long roleId, AuthUser authUser){

        Role role = roleRepository.findOne(roleId);

        if(CheckParam.isNull(role)){
            throw new BussinessException(ErrorCode.ROLE_NOTEXIST_ERROR.getCode(),ErrorCode.ROLE_NOTEXIST_ERROR.getMessage());
        }

        Long corporateIdentify = authUser.getCorporateIdentify();

        //需要删除角色-用户关联
        List<UserRole> userRoleList = userRoleRepository.findByRoleIdAndCorporateIdentify(roleId, corporateIdentify);

        if(null != userRoleList && !userRoleList.isEmpty()){
            userRoleRepository.deleteInBatch(userRoleList);
        }

        List<MenuRole> menuRoleList = menuRoleRepository.findByRoleIdAndCorporateIdentify(roleId, corporateIdentify);

        if(null != menuRoleList && !menuRoleList.isEmpty()){
            menuRoleRepository.deleteInBatch(menuRoleList);
        }

        roleRepository.delete(role);

    }

    /**
     * 删除用户 需要删除用户相关角色
     * 注意：不能删除当前用户的信息
     * @param userId
     * @param authUser
     */
    public void deleteUser(Long userId, AuthUser authUser){


        //当前用户不能删除自己
        if(userId == authUser.getUserId()){
            throw new BussinessException(ErrorCode.CURRENTUSER_OPERATESELF_ERROR.getCode(),ErrorCode.CURRENTUSER_OPERATESELF_ERROR.getMessage());
        }

        Long corporateIdentify = authUser.getCorporateIdentify();

        User user = userRepository.findByUserIdAndCorporateIdentify(userId, corporateIdentify);

        if(CheckParam.isNull(user)){
            throw new BussinessException(ErrorCode.USER_NOTEXIST_ERROR.getCode(),ErrorCode.USER_NOTEXIST_ERROR.getMessage());
        }

        List<UserRole> userRoleList = userRoleRepository.findByUserIdAndCorporateIdentify(user.getId(), corporateIdentify);

        if(null != userRoleList && !userRoleList.isEmpty()){
            userRoleRepository.deleteInBatch(userRoleList);
        }

        userRepository.delete(user);

    }


    /**
     * 根据企业标识码和企业code进行查找
     * @param userName
     * @param corporateCode
     * @return
     */
    public User findByUserNameAndCorporateCode(String userName,String corporateCode){
        return userRepository.findByUserNameAndCorporateCode(userName,corporateCode);
    }


    /**
     * 根据用户名进行查询
     * @param userName
     * @return
     */
    public User findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    /**
     * 根据用户名和企业唯一标识进行查找
     * @param username
     * @param corporateIdentify
     * @return
     */
    public User queryUserByNameAndCorporateIdentify(String username,Long corporateIdentify){
        User byUserNameAndCorporateIdentify = userRepository.findByUserNameAndCorporateIdentify(username, corporateIdentify);
        return  byUserNameAndCorporateIdentify;
    }

    /**
     * 根据企业角色标识查询企业的所有用户
     * @param corporateIdentify
     * @return
     */
    public UserRespWarpResp queryAllUserByCorporateIdentify(Long corporateIdentify){

        List<User> userList = userRepository.findByCorporateIdentify(corporateIdentify);
        List<UserResp> userRespList = new ArrayList<>();
        userList.stream().forEach(u1 ->{
            UserResp userResp = new UserResp();
            userResp.setUserId(u1.getUserId());
            userResp.setUserName(u1.getUserName());

            userRespList.add(userResp);
        });

        UserRespWarpResp userRespWarpResp = new UserRespWarpResp();

        userRespWarpResp.setUserRespList(userRespList);
        return userRespWarpResp;
    }

    /**
     * 为用户添加角色
     * @param userRoleReq
     * @param corporateIdentify
     * @return
     */
    public UserRoleResp addUserRole(UserRoleReq userRoleReq,Long corporateIdentify){

        if (!CheckParam.isNull(userRoleReq.getRoleIdList()) && !userRoleReq.getRoleIdList().isEmpty()){

            userRoleReq.setRoleIdList(userRoleReq.getRoleIdList().stream().distinct().collect(Collectors.toList()));

        }

        List<Long> roleIdList = userRoleReq.getRoleIdList();

        /**
         * 此处查询出来的userId必定全部都是相同的 排除重复的关系
         */
        List<UserRole> userRoleList = userRoleRepository.findByUserIdAndCorporateIdentifyAndRoleIdIn(userRoleReq.getUserId(),
                corporateIdentify, roleIdList);

        if(null != userRoleList && !userRoleList.isEmpty()){

            //形成以角色id为键的Map
            Map<Long, UserRole> userRoleMap = userRoleList.stream().collect(Collectors.toMap(UserRole::getRoleId, Function.identity()));

            //过滤掉在以角色ID为键的Map种存在的角色id
            roleIdList = roleIdList.stream().filter(r1 -> CheckParam.isNull(userRoleMap.get(r1))).collect(Collectors.toList());

        }

        List<UserRole> userRoles = new ArrayList<>();
        roleIdList.stream().forEach(r1 -> {
            UserRole userRole = new UserRole();

            userRole.setRoleId(r1);
            userRole.setCorporateIdentify(corporateIdentify);
            userRole.setUserId(userRoleReq.getUserId());

            userRoles.add(userRole);
        });

        userRoleRepository.save(userRoles);

        UserRoleResp userRoleResp = new UserRoleResp();

        userRoleResp.setRoleId(roleIdList);
        userRoleResp.setUserId(userRoleReq.getUserId());

        List<Long> ids = new ArrayList<>();

        userRoles.stream().forEach(u1 -> {
            ids.add(u1.getId());
        });
        userRoleResp.setId(ids);

        return userRoleResp;

    }


    /**
     * 根据企业唯一标识查询菜单
     * @param corporateIdentify
     * @return
     */
    public List<MenuResp> queryMenuByCorporateIdentify(Long corporateIdentify){

        List<Menu> menuList = menuRepository.findByCorporateIdentify(corporateIdentify);

          if(!CheckParam.isNull(menuList) && !menuList.isEmpty()){
              List<MenuResp> menuRespList = new ArrayList<>();

              menuList.stream().forEach(m1 -> {
                  MenuResp menuResp = new MenuResp();

                  menuResp.setId(m1.getId());
                  menuResp.setOrderNumber(m1.getOrderNumber());
                  menuResp.setUrl(m1.getUrl());
                  menuResp.setCorporateIdentify(m1.getCorporateIdentify());
                  menuResp.setName(m1.getName());
                  menuResp.setParentId(m1.getParentId());
                  menuRespList.add(menuResp);

              });
              return menuRespList;
          }
                return null;
    }


    /**
     * 根据企业标识和角色id查询角色具备的菜单
     * @param roleId
     * @param corporateIdentify
     * @return
     */
    public RoleMenuQueryResp queryMenusByRoleId(Long roleId, Long corporateIdentify){


        Role role = roleRepository.findByIdAndCorporateIdentify(roleId, corporateIdentify);

        if(!CheckParam.isNull(role)){

            List<MenuQueryResp> menuList = new ArrayList<>();

            RoleMenuQueryResp roleMenuQueryResp = new RoleMenuQueryResp();

            roleMenuQueryResp.setRoleId(role.getId());

            role.getMenuList().stream().forEach(r1 ->{
                MenuQueryResp menuQueryResp = new MenuQueryResp();
                menuQueryResp.setId(r1.getId());
                menuQueryResp.setName(r1.getName());
                menuQueryResp.setUrl(r1.getUrl());
                menuQueryResp.setOrderNumber(r1.getOrderNumber());
                menuQueryResp.setParentId(r1.getParentId());


                menuList.add(menuQueryResp);
            });

            roleMenuQueryResp.setMenuList(menuList);

            return roleMenuQueryResp;
        }

            return null;

    }


    /**
     * 为角色新增能访问的菜单
     * @param menuRoleReq
     * @param corporateIdentify
     * @return
     */
    public MenuRoleResp addMenuRole(MenuRoleReq menuRoleReq,Long corporateIdentify){

        if (!CheckParam.isNull(menuRoleReq.getMenuIdList()) && !menuRoleReq.getMenuIdList().isEmpty()){

            //去重操作
            menuRoleReq.setMenuIdList(menuRoleReq.getMenuIdList().stream().distinct().collect(Collectors.toList()));

        }

        List<Long> menuIdList = menuRoleReq.getMenuIdList();

        /**
         * 此处查询出来的roleId必定全部都是相同的 排除重复的关系
         */
        List<MenuRole> menuRoleList = menuRoleRepository.findByRoleIdAndCorporateIdentifyAndRoleIdIn(menuRoleReq.getRoleId(),
                corporateIdentify, menuIdList);

        if(null != menuRoleList && !menuRoleList.isEmpty()){

            //形成以菜单id为键的Map
            Map<Long, MenuRole> menuRoleMap = menuRoleList.stream().collect(Collectors.toMap(MenuRole::getRoleId, Function.identity()));

            //过滤掉在以角色ID为键的Map种存在的角色id
            menuIdList = menuIdList.stream().filter(r1 -> CheckParam.isNull(menuRoleMap.get(r1))).collect(Collectors.toList());

        }


        List<MenuRole> menuRoles = new ArrayList<>();

        menuIdList.stream().forEach(m1 -> {
            MenuRole menuRole = new MenuRole();

            menuRole.setRoleId(menuRoleReq.getRoleId());
            menuRole.setCorporateIdentify(corporateIdentify);
            menuRole.setMenuId(m1);

            menuRoles.add(menuRole);
        });


        menuRoleRepository.save(menuRoles);

        MenuRoleResp menuRoleResp = new MenuRoleResp();

        menuRoleResp.setRoleId(menuRoleReq.getRoleId());
        menuRoleResp.setMenuIdList(menuIdList);

        List<Long> ids = new ArrayList<>();
        menuRoles.stream().forEach(m1 -> {
            ids.add(m1.getId());
        });

        menuRoleResp.setIds(ids);

        return menuRoleResp;
    }

    /**
     * 新增菜单
     * @param menuReq
     * @param corporateIdentify
     * @return
     */
    public MenuResp addMenu(MenuReq menuReq,Long corporateIdentify){
        Menu menu = menuRepository.findByNameAndUrlAndCorporateIdentify(menuReq.getName(),
                menuReq.getUrl(),corporateIdentify);

        if(!CheckParam.isNull(menu)){
            throw new BussinessException(ErrorCode.MENU__REPETED_ERROR.getCode(),ErrorCode.MENU__REPETED_ERROR.getMessage());
        }

        menu = new Menu();

        menu.setName(menuReq.getName());
        menu.setParentId(menuReq.getParentId());
        menu.setRemark(menuReq.getRemark());
        menu.setCorporateIdentify(corporateIdentify);
        menu.setOrderNumber(menuReq.getOrderNumber());
        menu.setUrl(menuReq.getUrl());

        menuRepository.save(menu);

        MenuResp menuResp = new MenuResp();
        menuResp.setId(menu.getId());
        menuResp.setOrderNumber(menu.getOrderNumber());
        menuResp.setUrl(menu.getUrl());
        menuResp.setCorporateIdentify(menu.getCorporateIdentify());

        return menuResp;
    }

    /**
     * 新增角色
     * @param roleReq
     * @return
     */
    public RoleResp addRole(RoleReq roleReq, Long corporateIdentify){
        Role role = roleRepository.findByRoleCodeAndCorporateIdentify(roleReq.getRoleCode(),corporateIdentify);

        if(!CheckParam.isNull(role)){
            throw new BussinessException(ErrorCode.ROLE__REPETED_ERROR.getCode(),ErrorCode.ROLE__REPETED_ERROR.getMessage());
        }

        role = new Role();
        role.setRoleCode(roleReq.getRoleCode());
        role.setRoleName(roleReq.getRoleName());
        role.setEnableStatus(CheckParam.isNull(roleReq.getEnableStatus()) ? 1 : roleReq.getEnableStatus()); //默认启用
        role.setCorporateIdentify(corporateIdentify);
        role.setRoleDescription(roleReq.getRoleDescription());

        roleRepository.save(role);

        RoleResp roleResp = new RoleResp();
        roleResp.setRoleDescription(roleReq.getRoleDescription());
        roleResp.setRoleId(String.valueOf(role.getId()));
        roleResp.setRoleName(role.getRoleName());
        roleResp.setRoleCode(role.getRoleCode());
        roleResp.setEnableStatus(roleReq.getEnableStatus());
        //roleResp.setCorporateIdentify(corporateIdentify);

        return roleResp;
    }




    /**
     * 根据企业标识码查询企业所有的角色
     * @param corporateIdentify
     * @return
     */
    public List<RoleResp> queryRolesByCorporateIdentify(Long corporateIdentify){

        List<Role> roleList = roleRepository.findByCorporateIdentify(corporateIdentify);

        if(!CheckParam.isNull(roleList)){
            List<RoleResp> roleResps = new ArrayList<>();
            roleList.stream().forEach(r1 ->{
                RoleResp roleResp = new RoleResp();
                //roleResp.setCorporateIdentify(r1.getCorporateIdentify());
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
     * 根据用户id查询用户角色
     * @param userId
     * @return
     */
    public List<RoleResp> queryRoleByUserId(Long userId, Long corporateIdentify){

        User user = userRepository.findByIdAndCorporateIdentify(userId,corporateIdentify);

        if(!CheckParam.isNull(user)){
            List<RoleResp> roleResps = new ArrayList<>();

            user.getRoleList().stream().forEach(r1 ->{
                RoleResp roleResp = new RoleResp();
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
     * 企业新增用户
     * @param userAddReq
     * @param corporateIdentify
     * @return
     */
    public UserAddResp addUser(UserAddReq userAddReq,Long corporateIdentify){

       /* Corporate corporate = corporateRepository.findByCorporateIdentify(userAddReq.getCorporateIdentify());

        if(CheckParam.isNull(corporate)){
            throw new BussinessException(ErrorCode.CORPORATE__NOTEXIST_ERROR.getCode(),ErrorCode.CORPORATE__NOTEXIST_ERROR.getMessage());
        }*/

        User userInCorporate = userRepository.findByUserNameAndCorporateIdentify(userAddReq.getUserName(), corporateIdentify);

        if(!CheckParam.isNull(userInCorporate)) {
            throw new BussinessException(ErrorCode.CORPORATE_USER__REPET_ERROR.getCode(),ErrorCode.CORPORATE_USER__REPET_ERROR.getMessage());
        }


        Corporate corporate = corporateRepository.findByCorporateIdentify(corporateIdentify);

        User user =  new User();

        user.setCorporateIdentify(corporateIdentify);
        user.setUserName(userAddReq.getUserName());
        user.setUserId(UUIDSequenceWorker.uniqueSequenceId());

        String salt = EncryptUtils.generateSalt();
        user.setPassword(EncryptUtils.generate(userAddReq.getPassword(),salt));
        user.setSault(salt);

        if(!CheckParam.isNull(corporate)){
            user.setCorporateCode(corporate.getCorporateCode());
        }

        userRepository.save(user);

        UserAddResp userAddResp  = new UserAddResp();

        userAddResp.setUserName(user.getUserName());
        userAddResp.setCorporateIdentify(user.getCorporateIdentify());
        userAddResp.setUserId(user.getId());


        return userAddResp;

    }

}
