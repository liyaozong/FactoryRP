package cn.tech.yozo.factoryrp.config.shiro.filter;

import cn.tech.yozo.factoryrp.config.shiro.ShiroConstant;
import cn.tech.yozo.factoryrp.config.shiro.StatelessToken;
import cn.tech.yozo.factoryrp.utils.ShiroWebUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description 类似于 FormAuthenticationFilter，但是根据当前请求上下文信息每次请求时都要登录的认证过滤器。
 */
public class StatelessAuthcFilter extends AccessControlFilter {



    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return false;
    }
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        //1、客户端生成的消息摘要
        //String clientDigest = request.getParameter(ShiroConstant.PARAM_DIGEST);
        String sequence = request.getParameter(ShiroConstant.PARAM_SEQUENCE);
        //2、客户端传入的用户身份
        String tokenStr = request.getParameter(ShiroConstant.PARAM_TOKEN);
        String username = request.getParameter(ShiroConstant.PARAM_USERNAME);
        String password = request.getParameter(ShiroConstant.PARAM_PASSWORD);
        //3、客户端请求的参数列表
        Map<String, String[]> params =
                new HashMap<String, String[]>(request.getParameterMap());
        params.remove(ShiroConstant.PARAM_DIGEST);
        //4、生成无状态Token
        StatelessToken token = new StatelessToken(username, sequence,password);
        try {

            Subject subject = SecurityUtils.getSubject();

            //5、委托给Realm进行登录
            getSubject(request, response).login(token);

            //登陆成功之后的逻辑
            return onLoginSuccess(token,getSubject(request,response),request,response);
        } catch (Exception e) {
            e.printStackTrace();
            //6、登录失败
            onLoginFail(token,getSubject(request,response),request,response);
            return false;
        }
    }
    //登录失败时默认返回401状态码
    private void onLoginFail(AuthenticationToken token, Subject subject, ServletRequest request,
                             ServletResponse response) throws IOException {
        ShiroWebUtil.loginFailed(request, response, token, subject);
    }

    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        ShiroWebUtil.loginSuccess(request, response, token, subject);
        return false;
    }
}