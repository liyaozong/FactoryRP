package cn.tech.yozo.factoryrp.service.Impl;

import cn.tech.yozo.factoryrp.entity.Menu;
import cn.tech.yozo.factoryrp.entity.MenuRole;
import cn.tech.yozo.factoryrp.entity.Role;
import cn.tech.yozo.factoryrp.exception.BussinessException;
import cn.tech.yozo.factoryrp.repository.*;
import cn.tech.yozo.factoryrp.service.AuthorizationService;
import cn.tech.yozo.factoryrp.utils.CheckParam;
import cn.tech.yozo.factoryrp.utils.ErrorCode;
import cn.tech.yozo.factoryrp.vo.req.MenuReq;
import cn.tech.yozo.factoryrp.vo.req.MenuRoleReq;
import cn.tech.yozo.factoryrp.vo.req.RoleReq;
import cn.tech.yozo.factoryrp.vo.resp.*;
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


    @Resource
    private  UserRepository userRepository;


    @Resource
    private  PermissionRepository permissionRepository;

    @Resource
    private MenuRepository menuRepository;

    @Resource
    private
    MenuRoleRepository menuRoleRepository;


    /**
     * 根据企业标识和角色id查询角色具备的菜单
     * @param roleId
     * @param corporateIdentify
     * @return
     */
    public RoleMenuQueryResp queryByRoleIdAndCorporateIdentify(Long roleId, Long corporateIdentify){


        Role  role = roleRepository.findByIdAndCorporateIdentify(roleId, corporateIdentify);

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

        MenuRole menuRole = menuRoleRepository.findByRoleIdAndMenuIdAndCorporateIdentify(menuRoleReq.getRoleId(), menuRoleReq.getMenuId(), Long.parseLong(menuRoleReq.getCorporateIdentify()));

        if(!CheckParam.isNull(menuRole)){
            throw new BussinessException(ErrorCode.MENUROLE__REPETED_ERROR.getCode(),ErrorCode.MENUROLE__REPETED_ERROR.getMessage());
        }

        menuRole = new MenuRole();

        menuRole.setMenuId(menuRoleReq.getMenuId());
        menuRole.setRoleId(menuRoleReq.getRoleId());
        menuRole.setCorporateIdentify(Long.parseLong(menuRoleReq.getCorporateIdentify()));
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
                menuReq.getUrl(),Long.parseLong(menuReq.getCorporateIdentify()));

        if(!CheckParam.isNull(menu)){
            throw new BussinessException(ErrorCode.MENU__REPETED_ERROR.getCode(),ErrorCode.MENU__REPETED_ERROR.getMessage());
        }

        menu = new Menu();

        menu.setName(menuReq.getName());
        menu.setParentId(menuReq.getParentId());
        menu.setRemark(menuReq.getRemark());
        menu.setCorporateIdentify(Long.parseLong(menuReq.getCorporateIdentify()));
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
        Role role = roleRepository.findByRoleCodeAndCorporateIdentify(roleReq.getRoleCode(), Long.parseLong(roleReq.getCorporateIdentify()));

        if(!CheckParam.isNull(role)){
            throw new BussinessException(ErrorCode.ROLE__REPETED_ERROR.getCode(),ErrorCode.ROLE__REPETED_ERROR.getMessage());
        }

        role = new Role();
        role.setRoleCode(roleReq.getRoleCode());
        role.setRoleName(roleReq.getRoleName());
        role.setEnableStatus(roleReq.getEnableStatus());
        role.setCorporateIdentify(Long.parseLong(roleReq.getCorporateIdentify()));
        role.setRoleDescription(roleReq.getRoleDescription());

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



}
