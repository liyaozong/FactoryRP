package tech.yozo.factoryrp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tech.yozo.factoryrp.config.auth.UserAuthService;
import tech.yozo.factoryrp.service.AuthorizationService;
import tech.yozo.factoryrp.utils.AuthWebUtil;
import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.req.*;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import tech.yozo.factoryrp.vo.resp.menu.MenuResp;
import tech.yozo.factoryrp.vo.resp.role.RoleMenuQueryResp;
import tech.yozo.factoryrp.vo.resp.role.RoleResp;
import tech.yozo.factoryrp.vo.resp.user.UserRespWarpResp;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/17
 * @description 权限相关控制器
 */
@RestController
@RequestMapping(value = "api/authorization")
@Api(description = "系统权限相关接口")
public class AuthorizationController extends BaseController{


    @Resource
    private AuthorizationService authorizationService;


    @Resource
    private UserAuthService userAuthService;


    /**
     * 根据角色code查询用户信息
     * @param roleCode
     * @param request
     * @return
     */
    @ApiOperation(value = "根据角色code查询用户信息",notes = "根据角色code查询用户信息",httpMethod = "GET")
    @GetMapping("/queryUserByRoleCode")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String" ,name = "roleCode", paramType = "query" ,
                    value = "角色Code",required = true,defaultValue = "MAINTENANCE_PERSONNEL"),
    })
    public ApiResponse<UserRespWarpResp> queryUserByRoleCode(@RequestParam(required = true) String roleCode,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(authorizationService.queryUserByRoleCode(roleCode,corporateIdentify));
    }

    /**
     * 根据企业标识查询所有角色
     * @return
     */
    @ApiOperation(value = "根据企业标识查询所有角色",notes = "根据企业标识查询所有角色",httpMethod = "GET")
    @GetMapping("/queryRoles")
    public ApiResponse<List<RoleResp>> queryRolesByorporateIdentify(HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        List<RoleResp> roleResps = authorizationService.queryRolesByCorporateIdentify(corporateIdentify);
        return apiResponse(roleResps);
    }


    /**
     * 新增角色
     * @param roleReq
     * @return
     */
    @ApiOperation(value = "新增角色",notes = "新增角色",httpMethod = "POST")
    @PostMapping("/addRole")
    @ApiImplicitParam(dataType = "RoleReq" ,name = "roleReq", paramType = "VO" ,
            value = "企业新增相关信息",required = true)
    public ApiResponse<RoleResp> addRole(@Valid @RequestBody RoleReq roleReq,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(authorizationService.addRole(roleReq,corporateIdentify));
    }

    /**
     * 根据角色id查询角色具备的菜单
     * @return
     */
    @ApiOperation(value = "根据角色id查询角色具备的菜单",notes = "根据角色id查询角色具备的菜单",httpMethod = "GET")
    @GetMapping("/queryByRoleId")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Long" ,name = "roleId", paramType = "query" ,
                    value = "角色id",required = true,defaultValue = "3"),
    })
    public ApiResponse<RoleMenuQueryResp> queryMenusByRoleId(@RequestParam(value="roleId",required = true,defaultValue = "1")
                                                                                      Long roleId, HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(authorizationService.queryMenusByRoleId(roleId,corporateIdentify));
    }




    /**
     * 查询企业所有用户
     * @return
     */
    @ApiOperation(value = "查询企业所有用户",notes = "查询企业所有用户",httpMethod = "GET")
    @GetMapping("/queryCorporateAllUser")
    public ApiResponse<UserRespWarpResp> queryAllUserByCorporateIdentify(HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(authorizationService.queryAllUserByCorporateIdentify(corporateIdentify));
    }


    /**
     * 新增菜单
     * @param menuReq
     * @return
     */
    @ApiOperation(value = "新增菜单",notes = "新增菜单",httpMethod = "POST")
    @PostMapping("/addMenu")
    @ApiImplicitParam(dataType = "MenuReq" ,name = "menuReq", paramType = "VO" ,
            value = "新增菜单",required = true)
    public ApiResponse<MenuResp> addMenu(@Valid @RequestBody MenuReq menuReq,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(authorizationService.addMenu(menuReq,corporateIdentify));
    }




    /**
     * 为角色新增能访问的菜单
     * @param menuRoleReq
     * @return
     */
    @ApiOperation(value = "为角色新增能访问的菜单",notes = "为角色新增能访问的菜单",httpMethod = "POST")
    @PostMapping("/addMenuRole")
    @ApiImplicitParam(dataType = "MenuRoleReq" ,name = "menuRoleReq", paramType = "VO" ,
            value = "为角色新增能访问的菜单",required = true)
    public ApiResponse<MenuResp> addMenuRole(@Valid @RequestBody MenuRoleReq menuRoleReq,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(authorizationService.addMenuRole(menuRoleReq,corporateIdentify));
    }

    /**
     * 企业新增用户
     * @param userAddReq
     * @return
     */
    @ApiOperation(value = "企业新增用户",notes = "企业新增用户企业新增用户",httpMethod = "POST")
    @PostMapping("/addUser")
    @ApiImplicitParam(dataType = "UserAddReq" ,name = "userAddReq", paramType = "VO" ,
            value = "企业新增用户",required = true)
    public ApiResponse<MenuResp> addUser(@Valid @RequestBody UserAddReq userAddReq,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(authorizationService.addUser(userAddReq,corporateIdentify));
    }

    /**
     * 为用户添加角色
     * @param userRoleReq
     * @return
     */
    @ApiOperation(value = "为用户添加角色",notes = "为用户添加角色",httpMethod = "POST")
    @PostMapping("/addUserRole")
    @ApiImplicitParam(dataType = "UserRoleReq" ,name = "userRoleReq", paramType = "VO" ,
            value = "为用户添加角色",required = true)
    public ApiResponse<MenuResp> addUserRole(@Valid @RequestBody UserRoleReq userRoleReq,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(authorizationService.addUserRole(userRoleReq,corporateIdentify));
    }

    /**
     * 查询企业菜单
     * @return
     */
    @ApiOperation(value = "查询企业菜单",notes = "查询企业菜单",httpMethod = "GET")
    @GetMapping("/queryCorporateMenu")
    public ApiResponse<List<MenuResp>> queryMenuByCorporateIdentify(HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(authorizationService.queryMenuByCorporateIdentify(corporateIdentify));
    }


    /**
     * 删除用户 需要删除用户相关角色
     * @param userId
     */
    @ApiOperation(value = "根据用户id删除用户",notes = "根据用户id删除用户",httpMethod = "GET")
    @GetMapping("/deleteUser")
    public ApiResponse deleteUser(@RequestParam(value="userId",required = true,defaultValue = "1")
                                          Long userId,HttpServletRequest request){
        AuthUser authUser = userAuthService.getCurrentUser(request);
        authorizationService.deleteUser(userId,authUser);
        return apiResponse();
    }

    /**
     * 根据角色id删除角色
     * @param roleId
     * @param request
     * @return
     */
    @ApiOperation(value = "根据角色id删除角色",notes = "根据角色id删除角色",httpMethod = "GET")
    @GetMapping("/deleteRole")
    public ApiResponse deleteRole(@RequestParam(value="roleId",required = true,defaultValue = "1")
                                          Long roleId,HttpServletRequest request){
        AuthUser authUser = userAuthService.getCurrentUser(request);
        authorizationService.deleteRole(roleId,authUser);
        return apiResponse();
    }


    /**
     * 删除当前角色下面指定的菜单信息
     * @param menuRoleDeleteReq
     * @param request
     * @return
     */
    @ApiOperation(value = "删除当前角色下面指定的菜单信息",notes = "删除当前角色下面指定的菜单信息",httpMethod = "POST")
    @PostMapping("/deleteMenuRoleByRoleId")
    @ApiImplicitParam(dataType = "MenuRoleDeleteReq" ,name = "menuRoleDeleteReq", paramType = "VO" ,
            value = "删除当前角色下面指定的菜单信息",required = true)
    public ApiResponse deleteMenuRoleByRoleId(@Valid @RequestBody MenuRoleDeleteReq menuRoleDeleteReq,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        authorizationService.deleteMenuRoleByRoleId(menuRoleDeleteReq,corporateIdentify);
        return apiResponse();
    }

    /**
     * 删除指定用户下面指定的角色信息
     * @param userRoleDeleteReq
     * @param request
     */
    @ApiOperation(value = "删除指定用户下面指定的角色信息",notes = "删除指定用户下面指定的角色信息",httpMethod = "POST")
    @PostMapping("/deleteUserRoleByUserId")
    @ApiImplicitParam(dataType = "UserRoleDeleteReq" ,name = "userRoleDeleteReq", paramType = "VO" ,
            value = "删除指定用户下面指定的角色信息",required = true)
    public ApiResponse deleteUserRoleByUserId(@Valid @RequestBody UserRoleDeleteReq userRoleDeleteReq,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        authorizationService.deleteUserRoleByUserId(userRoleDeleteReq,corporateIdentify);
        return apiResponse();
    }

    /**
     * 根据菜单ID删除菜单
     * @param menuId
     * @param request
     * @return
     */
    @ApiOperation(value = "根据菜单ID删除菜单",notes = "根据菜单ID删除菜单",httpMethod = "GET")
    @GetMapping("/deleteMenu")
    public ApiResponse deleteMenu(@RequestParam(value="menuId",required = true,defaultValue = "1")
                                          Long menuId,HttpServletRequest request){
        AuthUser authUser = userAuthService.getCurrentUser(request);
        authorizationService.deleteMenu(menuId,authUser);
        return apiResponse();
    }

    /**
     * 根据用户id查询用户角色
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据用户id查询用户角色",notes = "根据用户id查询用户角色",httpMethod = "GET")
    @GetMapping("/queryRoleByUserId")
    public ApiResponse<List<RoleResp>> queryRoleUserId(@RequestParam(value="userId",required = true,defaultValue = "1")
                                                                   Long userId,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(authorizationService.queryRoleByUserId(userId,corporateIdentify));
    }

    /**
     * 没有token的返回接口，没啥用
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/unAuthToken")
    public ApiResponse unAuthToken(HttpServletRequest request, HttpServletResponse response){
        return AuthWebUtil.needLogin1(request,response);
    }


    /**
     * 登陆接口,其实没啥用
     * 如果不定义，SpringBoot会把登陆接口重置为/error
     * @param request
     * @param response
     * @return
     */
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String" ,name = "username", paramType = "query" ,
                    value = "用户名",required = true,defaultValue = "张三"),
            @ApiImplicitParam(dataType = "String" ,name = "password", paramType = "query" ,
                    value = "用户密码",required = true,defaultValue = "123"),
            @ApiImplicitParam(dataType = "Long" ,name = "requestSeqNo", paramType = "path" ,
                    value = "请求流水号",required = true,defaultValue = "12345678"),
            @ApiImplicitParam(dataType = "String" ,name = "corporateCode", paramType = "query" ,
                    value = "企业code",required = true,defaultValue = "AEC386")
    })
    @ApiOperation(value = "登陆接口",notes = "登陆接口",httpMethod = "GET")
    @GetMapping("/login")
    public ApiResponse<AuthUser> login(HttpServletRequest request, HttpServletResponse response){
        //return AuthWebUtil.needLogin1(request,response);
        return null;
    }

    /**
     * 退出登陆接口
     * @param request
     */
    @ApiOperation(value = "退出登陆接口",notes = "退出登陆接口",httpMethod = "GET")
    @GetMapping("/loginOut")
    public ApiResponse loginOut(HttpServletRequest request){
        userAuthService.loginOut(request);
        return apiResponse();
    }

}
