package tech.yozo.factoryrp.service.Impl;

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
     * @return
     */
    public UserRoleResp addUserRole(UserRoleReq userRoleReq){

        UserRole userRole = userRoleRepository.findByUserIdAndRoleIdAndCorporateIdentify(userRoleReq.getUserId(),
                userRoleReq.getRoleId(), userRoleReq.getCorporateIdentify());

        if(!CheckParam.isNull(userRole)){
            throw new BussinessException(ErrorCode.USERROLE__REPETED_ERROR.getCode(),ErrorCode.USERROLE__REPETED_ERROR.getMessage());
        }

        userRole = new UserRole();

        userRole.setRoleId(userRoleReq.getRoleId());
        userRole.setCorporateIdentify(userRoleReq.getCorporateIdentify());
        userRole.setUserId(userRoleReq.getUserId());

        userRoleRepository.save(userRole);

        UserRoleResp userRoleResp = new UserRoleResp();

        userRoleResp.setRoleId(userRole.getRoleId());
        userRoleResp.setUserId(userRole.getUserId());
        userRoleResp.setId(userRole.getId());

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

                  menuResp.setId(String.valueOf(m1.getId()));
                  menuResp.setOrderNumber(m1.getOrderNumber());
                  menuResp.setUrl(m1.getUrl());
                  menuResp.setCorporateIdentify(String.valueOf(m1.getCorporateIdentify()));
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
    public RoleMenuQueryResp queryByRoleIdAndCorporateIdentify(Long roleId, Long corporateIdentify){


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
     * @return
     */
    public MenuRoleResp addMenuRole(MenuRoleReq menuRoleReq){

        MenuRole menuRole = menuRoleRepository.findByRoleIdAndMenuIdAndCorporateIdentify(menuRoleReq.getRoleId(), menuRoleReq.getMenuId(),menuRoleReq.getCorporateIdentify());

        if(!CheckParam.isNull(menuRole)){
            throw new BussinessException(ErrorCode.MENUROLE__REPETED_ERROR.getCode(),ErrorCode.MENUROLE__REPETED_ERROR.getMessage());
        }

        menuRole = new MenuRole();

        menuRole.setMenuId(menuRoleReq.getMenuId());
        menuRole.setRoleId(menuRoleReq.getRoleId());
        menuRole.setCorporateIdentify(menuRoleReq.getCorporateIdentify());
        menuRole.setRemark(menuRoleReq.getRemark());

        MenuRoleResp menuRoleResp = new MenuRoleResp();

        menuRoleResp.setId(menuRole.getId());
        menuRoleResp.setRemark(menuRole.getRemark());
        menuRoleResp.setRoleId(menuRole.getRoleId());
        menuRoleResp.setCorporateIdentify(String.valueOf(menuRole.getCorporateIdentify()));
        menuRoleResp.setRemark(menuRole.getRemark());


        return menuRoleResp;
    }

    /**
     * 新增菜单
     * @param menuReq
     * @return
     */
    public MenuResp addMenu(MenuReq menuReq){
        Menu menu = menuRepository.findByNameAndUrlAndCorporateIdentify(menuReq.getName(),
                menuReq.getUrl(),menuReq.getCorporateIdentify());

        if(!CheckParam.isNull(menu)){
            throw new BussinessException(ErrorCode.MENU__REPETED_ERROR.getCode(),ErrorCode.MENU__REPETED_ERROR.getMessage());
        }

        menu = new Menu();

        menu.setName(menuReq.getName());
        menu.setParentId(menuReq.getParentId());
        menu.setRemark(menuReq.getRemark());
        menu.setCorporateIdentify(menuReq.getCorporateIdentify());
        menu.setOrderNumber(menuReq.getOrderNumber());
        menu.setUrl(menuReq.getUrl());

        menuRepository.save(menu);

        MenuResp menuResp = new MenuResp();
        menuResp.setId(String.valueOf(menu.getId()));
        menuResp.setOrderNumber(menu.getOrderNumber());
        menuResp.setUrl(menu.getUrl());
        menuResp.setCorporateIdentify(String.valueOf(menu.getCorporateIdentify()));

        return menuResp;
    }

    /**
     * 新增角色
     * @param roleReq
     * @return
     */
    public RoleResp addRole(RoleReq roleReq){
        Role role = roleRepository.findByRoleCodeAndCorporateIdentify(roleReq.getRoleCode(),roleReq.getCorporateIdentify());

        if(!CheckParam.isNull(role)){
            throw new BussinessException(ErrorCode.ROLE__REPETED_ERROR.getCode(),ErrorCode.ROLE__REPETED_ERROR.getMessage());
        }

        role = new Role();
        role.setRoleCode(roleReq.getRoleCode());
        role.setRoleName(roleReq.getRoleName());
        role.setEnableStatus(roleReq.getEnableStatus());
        role.setCorporateIdentify(roleReq.getCorporateIdentify());
        role.setRoleDescription(roleReq.getRoleDescription());

        roleRepository.save(role);

        RoleResp roleResp = new RoleResp();
        roleResp.setRoleDescription(roleReq.getRoleDescription());
        roleResp.setRoleId(String.valueOf(role.getId()));
        roleResp.setRoleName(role.getRoleName());
        roleResp.setRoleCode(role.getRoleCode());
        roleResp.setEnableStatus(roleReq.getEnableStatus());
        roleResp.setCorporateIdentify(roleReq.getCorporateIdentify());

        return roleResp;
    }




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
                roleResp.setCorporateIdentify(r1.getCorporateIdentify());
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
     * @return
     */
    public UserAddResp addUser(UserAddReq userAddReq){

        Corporate corporate = corporateRepository.findByCorporateIdentify(userAddReq.getCorporateIdentify());

        if(CheckParam.isNull(corporate)){
            throw new BussinessException(ErrorCode.CORPORATE__NOTEXIST_ERROR.getCode(),ErrorCode.CORPORATE__NOTEXIST_ERROR.getMessage());
        }

        User userInCorporate = userRepository.findByUserNameAndCorporateIdentify(userAddReq.getUserName(), userAddReq.getCorporateIdentify());

        if(CheckParam.isNull(userInCorporate)) {
            throw new BussinessException(ErrorCode.CORPORATE_USER__REPET_ERROR.getCode(),ErrorCode.CORPORATE_USER__REPET_ERROR.getMessage());
        }

        User user =  new User();

        user.setCorporateIdentify(userAddReq.getCorporateIdentify());
        user.setUserName(userAddReq.getUserName());
        user.setUserId(UUIDSequenceWorker.uniqueSequenceId());
        user.setPassword(EncryptUtils.generate(userAddReq.getPassword(),EncryptUtils.generateSalt()));

        userRepository.save(user);

        UserAddResp userAddResp  = new UserAddResp();

        userAddResp.setUserName(user.getUserName());
        userAddResp.setCorporateIdentify(user.getCorporateIdentify());
        userAddResp.setUserId(user.getId());


        return userAddResp;

    }

}
