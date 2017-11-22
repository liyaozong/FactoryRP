package cn.tech.yozo.factoryrp.config.shiro;

import cn.tech.yozo.factoryrp.entity.Role;
import cn.tech.yozo.factoryrp.entity.User;
import cn.tech.yozo.factoryrp.repository.RoleRepository;
import cn.tech.yozo.factoryrp.repository.UserRepository;
import cn.tech.yozo.factoryrp.repository.UserRoleRepository;
import cn.tech.yozo.factoryrp.utils.CheckParam;
import cn.tech.yozo.factoryrp.utils.EncryptUtils;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description 认证类
 */
public class StateLessShiroRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        //仅支持StatelessToken类型的Token
        return token instanceof StatelessToken;
    }

    private Logger logger =  LoggerFactory.getLogger(StateLessShiroRealm.class);

    @Resource
    private UserRepository userRepository;


    @Resource
    private UserRoleRepository userRoleRepository;


    @Resource
    private RoleRepository roleRepository;


    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     * @description 本例中该方法的调用时机为需授权资源被访问时
     * @description 并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
     * @description 如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("执行权限认证doGetAuthorizationInfo+"+principalCollection.toString());

        //获取当前登录输入的用户名，等价于(String) principalCollection.fromRealm(getName()).iterator().next();
        //Long.valueOf(String.valueOf(principalCollection.getPrimaryPrincipal()))
        String loginName =  String.valueOf(super.getAvailablePrincipal(principalCollection));

        User user = userRepository.findByUserName(loginName);

        logger.info(">>>>>>>>>>>>>>>>>>>>>>执行权限认证doGetAuthorizationInfo查询出来的User<<<<<<<<<<<<<<<<<<<<<<"+ JSON.toJSONString(user));

        if(!CheckParam.isNull(user)){
            //把principals放session中 key=userId value=principals
            SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getId()),SecurityUtils.getSubject().getPrincipals());

            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

            //用户的角色集合
            List<Role> roleList = user.getRoleList();

            Set<String> permissionNameList = new HashSet<>();

            /**
             * 赋予角色
             */
           /* roleList.stream().forEach(u1 ->{
                info.addRole(u1.getRoleName());
                u1.getPermissionList().stream().forEach(p1 ->{
                    permissionNameList.add(p1.getName()); //拿到权限名称
                });
            });*/

            info.addStringPermissions(permissionNameList);

            // 或者按下面这样添加
            //添加一个角色,不是配置意义上的添加,而是证明该用户拥有admin角色
            //simpleAuthorInfo.addRole("admin");
            //添加权限
            //simpleAuthorInfo.addStringPermission("admin:manage");
            //logger.info("已为用户[mike]赋予了[admin]角色和[admin:manage]权限");

            return info;
        }else{
            // 返回null的话，就会导致任何用户访问被拦截的请求时，都会自动跳转到unauthorizedUrl指定的地址
            return null;
        }


    }

    /**
     * 授权
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("doGetAuthenticationInfo +"  + authenticationToken.toString());
        UsernamePasswordToken token = null;
        try{
            token = (UsernamePasswordToken) authenticationToken;

        }catch (Exception e){
            e.printStackTrace();
        }

        String userName = token.getUsername();
        String tokenPassword = String.valueOf(token.getPassword());
        logger.info(">>>>>>>>>doGetAuthenticationInfo密码<<<<<<<<<<<<<<<<<<<"+token.getPassword());

        User user = userRepository.findByUserName(userName);

        if (!CheckParam.isNull(user)) {
            String sault = user.getSault();
            //ShiroUser shiroUser=new ShiroUser(user.getId(), user.getLoginName(), user.getName());
            //设置用户session
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("user", user);

            //处理盐
            token.setPassword(EncryptUtils.generate(tokenPassword,user.getSault()).toCharArray());
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            return new SimpleAuthenticationInfo(userName,user.getPassword(),getName());
        } else {
            return null;
        }

    }
}
