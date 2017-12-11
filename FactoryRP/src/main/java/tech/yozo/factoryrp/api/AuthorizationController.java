package tech.yozo.factoryrp.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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


    /*@Value("${spring.redis.host}")
    private String host;

    @Resource
    private RedisTemplate redisTemplate;


    @Resource
    private StringRedisTemplate stringRedisTemplate;*/
    /**
     * 根据企业标识查询所有角色
     * @return
     */
    @ApiOperation(value = "根据企业标识查询所有角色",notes = "根据企业标识查询所有角色",httpMethod = "GET")
    @GetMapping("/queryRoles")
    public ApiResponse<List<RoleResp>> queryRolesByorporateIdentify(@PathVariable("requestSeqNo") String requestSeqNo,HttpServletRequest request){
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
     * 根据企业标识查询所有角色
     * @return
     */
    @ApiOperation(value = "根据角色id查询角色具备的菜单",notes = "根据角色id查询角色具备的菜单",httpMethod = "GET")
    @GetMapping("/queryByRoleId")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Long" ,name = "roleId", paramType = "query" ,
                    value = "角色id",required = true,defaultValue = "3"),
    })
    public ApiResponse<RoleMenuQueryResp> queryByRoleIdAndCorporateIdentify(@PathVariable("requestSeqNo") String requestSeqNo,
                                                                            @RequestParam(value="roleId",required = true,defaultValue = "1")
                                                                                      Long roleId,HttpServletRequest request){
        Long corporateIdentify = userAuthService.getCurrentUserCorporateIdentify(request);
        return apiResponse(authorizationService.queryByRoleIdAndCorporateIdentify(roleId,corporateIdentify));
    }


    /**
     * 查询企业所有用户
     * @return
     */
    @ApiOperation(value = "查询企业所有用户",notes = "查询企业所有用户",httpMethod = "GET")
    @GetMapping("/queryCorporateAllUser")
    public ApiResponse<RoleMenuQueryResp> queryAllUserByCorporateIdentify(HttpServletRequest request){
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
                    value = "请求流水号",required = true,defaultValue = "12345678")
    })
    @ApiOperation(value = "登陆接口",notes = "登陆接口",httpMethod = "GET")
    @GetMapping("/login")
    public ApiResponse<AuthUser> login(HttpServletRequest request, HttpServletResponse response){
        //return AuthWebUtil.needLogin1(request,response);
        return null;
    }

}
