package cn.tech.yozo.factoryrp.config.shiro.filter;

import cn.tech.yozo.factoryrp.utils.ShiroWebUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author created by Singer email:313402703@qq.com
 * @time 2017/11/16
 * @description
 */
public class AuthenticationFilter extends FormAuthenticationFilter {


    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
                                     ServletResponse response) {
        ShiroWebUtil.loginFailed(request, response, e);
        return false;
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
                                     ServletResponse response) throws Exception {
        ShiroWebUtil.loginSuccess(request, response, token, subject);
        return false;
    }

    @Override
    protected boolean isLoginSubmission(ServletRequest request, ServletResponse response) {
        return (request instanceof HttpServletRequest)
                && ((HttpServletRequest) request).getServletPath().equals(getLoginUrl());
    }

    private boolean handleRelogin(ServletRequest request, ServletResponse response) {
        if (isLoginSubmission(request, response)) {
            Subject subject = SecurityUtils.getSubject();

            if (subject != null && subject.isAuthenticated()) {
                // ShiroWebUtil.handleRelogin(request, response);
                // return true;
                subject.logout();
            }
        }
        return false;
    }
    /**

     * 在这里需要进行统一校验，例如签名检验
     */
    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        if (handleRelogin(request, response)) {
            return false;
        }
        return super.onPreHandle(request, response, mappedValue);
    }

    @Override
    protected void saveRequestAndRedirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
        ShiroWebUtil.needLogin(request, response);
    }

}
