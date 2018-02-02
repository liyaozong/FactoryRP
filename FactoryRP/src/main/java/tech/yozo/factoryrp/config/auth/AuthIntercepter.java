package tech.yozo.factoryrp.config.auth;

import tech.yozo.factoryrp.entity.MenuRole;
import tech.yozo.factoryrp.entity.User;
import tech.yozo.factoryrp.service.AuthorizationService;
import tech.yozo.factoryrp.utils.AuthWebUtil;
import tech.yozo.factoryrp.utils.CheckParam;
import tech.yozo.factoryrp.utils.EncryptUtils;
import tech.yozo.factoryrp.utils.TokenUtil;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import tech.yozo.factoryrp.vo.resp.auth.AuthUserMenu;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import tech.yozo.factoryrp.vo.resp.role.RoleResp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/23
 * @description 权限拦截器
 */
@Component
public class AuthIntercepter implements HandlerInterceptor {


    private static Logger logger  = LoggerFactory.getLogger(AuthIntercepter.class);


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AuthorizationService authorizationService;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.auth.cache-prefix}")
    private String authCachePrefix;

    @Value("${spring.auth.expiredTime}")
    private Long authExpiredTime;

   /* @Resource
    private UserRepository userRepository;
*/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        /**
         * 如果是登陆的URL执行登陆逻辑
         */
       if(requestURI.contains("api/authorization/webLogin") || requestURI.contains("api/authorization/login")){
           logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>用户开始登陆<<<<<<<<<<<<<<<<<<"+requestURI);


           //临时的处理逻辑，直接返回用户权限给前端
           /*String username = request.getParameter("username");
           String password = request.getParameter("password");
           String corporateCode = request.getParameter("corporateCode");
           User user = authorizationService.findByUserName(username);
           if(CheckParam.isNull(username) || CheckParam.isNull(password)){
               AuthWebUtil.loginFailed(request,response);
               return false;
           }else if(CheckParam.isNull(corporateCode)){
               AuthWebUtil.corporateCodeError(request,response);
               return false;
           }

           AuthUser authUser = new AuthUser();
           authUser.setToken("1");
           authUser.setCorporateIdentify(1L);
           authUser.setUserName(user.getUserName());
           authUser.setUserId(user.getUserId());
           List<AuthUserMenu> authUserMenuList = new ArrayList<>();
           List<RoleResp> roleList  = new ArrayList<>();

           user.getRoleList().forEach(u1 ->{
               u1.getMenuList().forEach(m1 ->{
                   AuthUserMenu authUserMenu = new AuthUserMenu();
                   authUserMenu.setId(m1.getId());
                   authUserMenu.setParentId(m1.getParentId());
                   authUserMenu.setName(m1.getName());
                   authUserMenu.setUrl(m1.getUrl());
                   authUserMenu.setRemark(m1.getRemark());

                   authUserMenuList.add(authUserMenu);
               });

               RoleResp roleResp = new RoleResp();
               roleResp.setEnableStatus(u1.getEnableStatus());
               roleResp.setRoleCode(u1.getRoleCode());
               roleResp.setRoleDescription(u1.getRoleDescription());
               roleResp.setRoleId(String.valueOf(u1.getId()));
               roleResp.setRoleName(u1.getRoleName());

               roleList.add(roleResp);
           });

           authUser.setAuthUserMenuList(authUserMenuList);
           authUser.setRoleList(roleList);
           AuthWebUtil.loginSuccess(request,response,authUser);
           return true;*/


           return handleLogin(request,response);
        }

        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>调用非登陆接口，开始token验证，请求地址为:<<<<<<<<<<<<<<<<<<"+requestURI);
        return verifyToken(request,response);
        //return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }


    /**
     * 验证token
     * 如果token在Redis缓存里面不存在，需要重新登陆
     * @param request
     * @param response
     * @return
     */
    private boolean verifyToken(HttpServletRequest request,HttpServletResponse response){
        String token = request.getHeader("token");

        //从请求头和从请求参数里面拿到token都可以
        if(CheckParam.isNull(token)){
          token = request.getParameter("token");
        }
        if(CheckParam.isNull(token)){
            AuthWebUtil.needLogin(request,response);
            return false;
        }


        String authUser = stringRedisTemplate.opsForValue().get(authCachePrefix+token);

        if(CheckParam.isNull(authUser)){
            AuthWebUtil.needLogin(request,response);
            return false;
        }
        return true;
    }

    /**
     * 处理登陆逻辑
     *  1，根据用户名和企业标识到数据库查询用户是否存在
     *     如果不存在,直接返回错误信息，如果用户名和密码不存在也返回错误信息
     *     进行密码比对,成功直接放进Redis
     * @param request
     * @return
     */

    private boolean handleLogin(HttpServletRequest request,HttpServletResponse response){


        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String corporateCode = request.getParameter("corporateCode");

        /**
         * 用户名密码为空直接返回
         */
        if(CheckParam.isNull(username) || CheckParam.isNull(password)){
            AuthWebUtil.loginFailed(request,response);
            return false;
        }else if(CheckParam.isNull(corporateCode)){
            AuthWebUtil.corporateCodeError(request,response);
            return false;
        }

        //User user = authorizationService.findByUserName(username);

        User user = authorizationService.findByUserNameAndCorporateCode(username,corporateCode);

        /**
         * 用户不存在直接返回
         */
        if(CheckParam.isNull(user)){
            AuthWebUtil.loginFailed(request,response);
            return false;
        }


        /**
         * 加密之后的密码和盐
         */
        String sysPassword = user.getPassword();
        String sault = user.getSault();

        /**
         * 用前端的密码生成加密后的密码
         */
        String encryptedPassword = EncryptUtils.generate(password, sault);

        boolean verify = EncryptUtils.verifyPassword(sysPassword, encryptedPassword);


        /**
         * 验证成功返回用户能够访问的菜单信息
         */
        if(verify){

            //生成64位token
            String token = TokenUtil.generateTokenString(64);
            //返回企业标识信息
            Long corporateIdentify = user.getCorporateIdentify();

            AuthUser authUser = new AuthUser();

            authUser.setToken(token);
            authUser.setCorporateIdentify(corporateIdentify);
            authUser.setUserName(user.getUserName());
            authUser.setUserId(user.getUserId());

            List<AuthUserMenu> authUserMenuList = new ArrayList<>();
            List<RoleResp> roleList  = new ArrayList<>();

            user.getRoleList().forEach(u1 ->{
                u1.getMenuList().forEach(m1 ->{
                    AuthUserMenu authUserMenu = new AuthUserMenu();
                    authUserMenu.setId(m1.getId());
                    authUserMenu.setParentId(m1.getParentId());
                    authUserMenu.setName(m1.getName());
                    authUserMenu.setUrl(m1.getUrl());
                    authUserMenu.setRemark(m1.getRemark());

                    authUserMenuList.add(authUserMenu);
                });

                RoleResp roleResp = new RoleResp();
                roleResp.setEnableStatus(u1.getEnableStatus());
                roleResp.setRoleCode(u1.getRoleCode());
                roleResp.setRoleDescription(u1.getRoleDescription());
                roleResp.setRoleId(String.valueOf(u1.getId()));
                roleResp.setRoleName(u1.getRoleName());

                roleList.add(roleResp);

            });


            //去重操作
            ArrayList<AuthUserMenu> authUserMenuArrayList = authUserMenuList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparingLong(AuthUserMenu::getId))), ArrayList::new));

            authUser.setAuthUserMenuList(authUserMenuArrayList);
            authUser.setRoleList(roleList);

            stringRedisTemplate.opsForValue().set(authCachePrefix+token, JSON.toJSONString(authUser),authExpiredTime);



            try {
                AuthWebUtil.loginSuccess(request,response,authUser);
                return true;
            } catch (IOException e) {
                logger.error("登陆发生异常: "+e.getMessage(),e);
            }

        }

            AuthWebUtil.loginFailed(request, response);
            return false;
    }



}
