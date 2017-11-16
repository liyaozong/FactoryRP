package cn.tech.yozo.factoryrp.config.shiro;

import cn.tech.yozo.factoryrp.entity.Role;
import cn.tech.yozo.factoryrp.entity.User;
import cn.tech.yozo.factoryrp.entity.UserRole;
import cn.tech.yozo.factoryrp.repository.RoleRepository;
import cn.tech.yozo.factoryrp.repository.UserRepository;
import cn.tech.yozo.factoryrp.repository.UserRoleRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description 认证类
 */
public class ShiroRealm  extends AuthorizingRealm {

    private Logger logger =  LoggerFactory.getLogger(ShiroRealm.class);

    @Resource
    private UserRepository userRepository;


    @Resource
    private UserRoleRepository userRoleRepository;


    @Resource
    private RoleRepository roleRepository;


    /**
     * 认证
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("doGetAuthorizationInfo+"+principalCollection.toString());
        User user = userRepository.findOne(Long.valueOf(String.valueOf(principalCollection.getPrimaryPrincipal())));

        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getUserId());


        List<Long> roleIds = new ArrayList<>();
        userRoles.stream().forEach(u1 ->{
            roleIds.add(u1.getRoleId());
        });

        long [] roleIdArray = roleIds.stream().mapToLong(Long::longValue).toArray();

        List<Role> roleList = roleRepository.findByIdIn(roleIdArray);

        //把principals放session中 key=userId value=principals
        SecurityUtils.getSubject().getSession().setAttribute(String.valueOf(user.getId()),SecurityUtils.getSubject().getPrincipals());


        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //赋予角色
        roleList.stream().forEach(r1 -> {
            info.addRole(r1.getRoleName());
        });

        return null;
    }

    /**
     * 授权
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        return null;
    }
}
