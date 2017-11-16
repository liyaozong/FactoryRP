package cn.tech.yozo.factoryrp.utils;

import cn.tech.yozo.factoryrp.vo.base.ApiResponse;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 对前端的工具
 * @author Administrator
 *
 */
public class ShiroWebUtil {
	private static final Logger logger = LoggerFactory.getLogger(ShiroWebUtil.class);

	/**
	 * 预设响应头信息
	 * 
	 * @param response
	 */
	private static void preSetResponse(ServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		if (response instanceof HttpServletResponse) {
			((HttpServletResponse) response).addHeader("Access-Control-Allow-Origin", "*");
			((HttpServletResponse) response).addHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
		}
	}


	/**
	 * 需要登录的响应
	 * 
	 * @param request
	 * @param response
	 */
	public static void needLogin(ServletRequest request, ServletResponse response) {
		preSetResponse(response);
		PrintWriter out = null;
		ApiResponse apiResponse = new ApiResponse();

		apiResponse.setErrorCode(ErrorCode.NEED_LOGIN.getCode());
		apiResponse.setErrorMessage(ErrorCode.NEED_LOGIN.getMessage());
		try {
			out = response.getWriter();
			out.append(JSON.toJSONString(apiResponse));
			out.flush();
		} catch (IOException e) {
			logger.error("needLogin error:" + e.getMessage(), e);
		}
	}

	/**
	 * 没有权限的响应
	 * 
	 * @param request
	 * @param response
	 */
	public static void unauthorized(ServletRequest request, ServletResponse response) {
		preSetResponse(response);
		PrintWriter out = null;
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setErrorCode(ErrorCode.NO_PERMISSION.getCode());
		apiResponse.setErrorMessage(ErrorCode.NO_PERMISSION.getMessage());
		
		try {
			out = response.getWriter();
			out.append(JSON.toJSONString(apiResponse));
			out.flush();
		} catch (IOException e) {
			logger.error("unauthorized error:" + e.getMessage(), e);
		}
	}

	/**
	 * 服务器异常
	 * 
	 * @param request
	 * @param response
	 */
	public static void serverException(ServletRequest request, ServletResponse response) {
		ApiResponse apiResponse = new ApiResponse();
		preSetResponse(response);
		PrintWriter out = null;
		apiResponse.setErrorMessage(ErrorCode.ERROR.getMessage());
		apiResponse.setErrorCode(ErrorCode.ERROR.getMessage());
		try {
			out = response.getWriter();
			out.append(JSON.toJSONString(apiResponse));
			out.flush();
		} catch (IOException e) {
			logger.error("serverException error:" + e.getMessage(), e);
		}
	}

	/**
	 * 登录成功
	 * 
	 * @param request
	 * @param response
	 * @param token
	 * @param subject
	 * @throws IOException
	 */
	public static void loginSuccess(ServletRequest request, ServletResponse response, AuthenticationToken token,
			Subject subject) throws IOException {
		ApiResponse<LoginSuccessResut> apiResponse = new ApiResponse<>();
		PrintWriter out = null;
		try {
			preSetResponse(response);

			//shiro自动生成的token
			String tokenStr = subject.getSession().getId().toString();
			apiResponse.setData(new LoginSuccessResut(((UsernamePasswordToken) token).getUsername(),
					tokenStr));
			apiResponse.setErrorCode(ErrorCode.SUCCESS.getCode());
			apiResponse.setErrorMessage(ErrorCode.SUCCESS.getMessage());
		} catch (Exception e) {
			logger.error("loginSuccess error:" + e.getMessage(), e);
		} finally {
			out = response.getWriter();
			out.append(JSON.toJSONString(apiResponse));
			out.flush();
		}
	}

	/**
	 * 登出成功
	 * 
	 * @param request
	 * @param response
	 */
	public static void logoutSuccess(ServletRequest request, ServletResponse response) {
		ApiResponse apiResponse = new ApiResponse();
		preSetResponse(response);
		PrintWriter out = null;
		apiResponse.setErrorCode(ErrorCode.SUCCESS.getCode());
		apiResponse.setErrorMessage(ErrorCode.SUCCESS.getMessage());
		try {
			out = response.getWriter();
			out.append(JSON.toJSONString(apiResponse));
			out.flush();
		} catch (IOException e) {
			logger.error("logoutSuccess error:" + e.getMessage(), e);
		}
	}

	/**
	 * 用户名密码错误
	 * 
	 * @param request
	 * @param response
	 * @param ex
	 */
	public static void loginFailed(ServletRequest request, ServletResponse response, AuthenticationException ex) {
		ApiResponse apiResponse = new ApiResponse();
		preSetResponse(response);
		PrintWriter out = null;
		apiResponse.setErrorCode(ErrorCode.LOGIN_FAILED.getCode());
		apiResponse.setErrorMessage(ErrorCode.LOGIN_FAILED.getMessage());
		try {
			out = response.getWriter();
			out.append(JSON.toJSONString(apiResponse));
			out.flush();
		} catch (IOException e) {
			logger.error("loginFailed error:" + e.getMessage(), e);
		}
	}

	/**
	 * 登录失败
	 * 
	 * @param request
	 * @param response
	 * @param ex
	 */
	public static void userexception(HttpServletRequest request, HttpServletResponse response, Exception ex) {
		ApiResponse apiResponse = new ApiResponse();
		preSetResponse(response);
		PrintWriter out = null;
		apiResponse.setErrorCode(ErrorCode.LOGIN_FAILED.getCode());
		apiResponse.setErrorMessage(ErrorCode.LOGIN_FAILED.getMessage());
		try {
			out = response.getWriter();
			out.append(JSON.toJSONString(apiResponse));
			out.flush();
		} catch (IOException e) {
			logger.error("userexception error:" + e.getMessage(), e);
		}
	}
}

class LoginSuccessResut {
	private String username;
	private String jsessionId;
	public LoginSuccessResut(String username, String jsessionId) {
		super();
		this.username = username;
		this.jsessionId = jsessionId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getJsessionId() {
		return jsessionId;
	}

	public void setJsessionId(String jsessionId) {
		this.jsessionId = jsessionId;
	}


}
