package cn.tech.yozo.factoryrp.api;

import cn.tech.yozo.factoryrp.service.AuthorizationService;
import cn.tech.yozo.factoryrp.vo.base.ApiResponse;
import cn.tech.yozo.factoryrp.vo.req.MenuReq;
import cn.tech.yozo.factoryrp.vo.req.MenuRoleReq;
import cn.tech.yozo.factoryrp.vo.req.RoleReq;
import cn.tech.yozo.factoryrp.vo.req.UserRoleReq;
import cn.tech.yozo.factoryrp.vo.resp.MenuResp;
import cn.tech.yozo.factoryrp.vo.resp.RoleMenuQueryResp;
import cn.tech.yozo.factoryrp.vo.resp.RoleResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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



    /**
     * 根据企业标识查询所有角色
     * @param corporateIdentify
     * @return
     */
    @ApiOperation(value = "根据企业标识查询所有角色",notes = "根据企业标识查询所有角色",httpMethod = "GET")
    @GetMapping("/queryRolesByCorporateIdentify/{requestSeqNo}")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "String" ,name = "corporateIdentify", paramType = "query" ,
                    value = "企业唯一标识",required = true,defaultValue = "111"),
            @ApiImplicitParam(dataType = "String" ,name = "requestSeqNo", paramType = "path" ,
                    value = "请求流水号",required = true,defaultValue = "12345678")
    })
    public ApiResponse<List<RoleResp>> queryRolesByorporateIdentify(@PathVariable("requestSeqNo") String requestSeqNo,
                                                                    @RequestParam(value="corporateIdentify",required = true,defaultValue = "1")
                                                                            String corporateIdentify){
        List<RoleResp> roleResps = authorizationService.queryRolesByorporateIdentify(corporateIdentify);
        return apiResponse(requestSeqNo,roleResps);
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
    public ApiResponse<RoleResp> addRole(@Valid @RequestBody RoleReq roleReq){
        return apiResponse(roleReq,authorizationService.addRole(roleReq));
    }

    /**
     * 根据企业标识查询所有角色
     * @param corporateIdentify
     * @return
     */
    @ApiOperation(value = "根据企业标识和角色id查询角色具备的菜单",notes = "根据企业标识和角色id查询角色具备的菜单",httpMethod = "GET")
    @GetMapping("/queryByRoleIdAndCorporateIdentify/{requestSeqNo}")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "Long" ,name = "corporateIdentify", paramType = "query" ,
                    value = "企业唯一标识",required = true,defaultValue = "3123881"),
            @ApiImplicitParam(dataType = "Long" ,name = "roleId", paramType = "query" ,
                    value = "角色id",required = true,defaultValue = "3"),
            @ApiImplicitParam(dataType = "Long" ,name = "requestSeqNo", paramType = "path" ,
                    value = "请求流水号",required = true,defaultValue = "12345678")
    })
    public ApiResponse<RoleMenuQueryResp> queryByRoleIdAndCorporateIdentify(@PathVariable("requestSeqNo") String requestSeqNo,
                                                                          @RequestParam(value="corporateIdentify",required = true,defaultValue = "1")
                                                                            Long corporateIdentify, @RequestParam(value="roleId",required = true,defaultValue = "1")
                                                                                      Long roleId){

        return apiResponse(requestSeqNo,authorizationService.queryByRoleIdAndCorporateIdentify(roleId,corporateIdentify));
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
    public ApiResponse<MenuResp> addMenu(@Valid @RequestBody MenuReq menuReq){
        return apiResponse(menuReq,authorizationService.addMenu(menuReq));
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
    public ApiResponse<MenuResp> addMenuRole(@Valid @RequestBody MenuRoleReq menuRoleReq){
        return apiResponse(menuRoleReq,authorizationService.addMenuRole(menuRoleReq));
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
    public ApiResponse<MenuResp> addUserRole(@Valid @RequestBody UserRoleReq userRoleReq){
        return apiResponse(userRoleReq,authorizationService.addUserRole(userRoleReq));
    }

}
