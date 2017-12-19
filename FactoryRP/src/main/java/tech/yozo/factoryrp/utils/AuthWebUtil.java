package tech.yozo.factoryrp.utils;

import tech.yozo.factoryrp.vo.base.ApiResponse;
import tech.yozo.factoryrp.vo.resp.auth.AuthUser;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
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
public class AuthWebUtil {
	private static final Logger logger = LoggerFactory.getLogger(AuthWebUtil.class);

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
    public static ApiResponse needLogin1(ServletRequest request, ServletResponse response) {

        ApiResponse apiResponse = new ApiResponse();
        preSetResponse(response);
        PrintWriter out = null;
        apiResponse.setErrorCode(ErrorCode.NEED_LOGIN.getCode());
        apiResponse.setErrorMessage(ErrorCode.NEED_LOGIN.getMessage());
        apiResponse.setResponseTime(DateTimeUtil.currentDateToStr(""));
        apiResponse.setRequestSeqNo(request.getParameter("requestSeqNo"));
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            System.out.println(JSON.toJSONString(apiResponse));

        return apiResponse;
    }

	/**
	 * 需要登录的响应
	 * 
	 * @param request
	 * @param response
	 */
	public static void needLogin(ServletRequest request, ServletResponse response) {

        ApiResponse apiResponse = new ApiResponse();
        preSetResponse(response);
        PrintWriter out = null;
        apiResponse.setErrorCode(ErrorCode.NEED_LOGIN.getCode());
        apiResponse.setErrorMessage(ErrorCode.NEED_LOGIN.getMessage());
        apiResponse.setResponseTime(DateTimeUtil.currentDateToStr(""));
        apiResponse.setRequestSeqNo(request.getParameter("requestSeqNo"));
		try {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            System.out.println(JSON.toJSONString(apiResponse));
            httpResponse.getWriter().write(JSON.toJSONString(apiResponse));
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
        apiResponse.setResponseTime(DateTimeUtil.currentDateToStr(""));
        apiResponse.setRequestSeqNo(request.getParameter("requestSeqNo"));
		
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
        apiResponse.setResponseTime(DateTimeUtil.currentDateToStr(""));
        apiResponse.setRequestSeqNo(request.getParameter("requestSeqNo"));
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
	 * @throws IOException
	 */
	public static void loginSuccess(ServletRequest request,
                                    ServletResponse response,AuthUser authUser) throws IOException {
		ApiResponse<AuthUser> apiResponse = new ApiResponse<>();
		ServletOutputStream out = null;
		try {
			preSetResponse(response);

			apiResponse.setErrorCode(ErrorCode.SUCCESS.getCode());
			apiResponse.setErrorMessage(ErrorCode.SUCCESS.getMessage());
			apiResponse.setData(authUser);
            apiResponse.setResponseTime(DateTimeUtil.currentDateToStr(""));
            apiResponse.setRequestSeqNo(request.getParameter("requestSeqNo"));
		} catch (Exception e) {
			logger.error("loginSuccess error:" + e.getMessage(), e);
		} finally {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.getWriter().write(JSON.toJSONString(apiResponse));

			//out = response.getOutputStream();
			//out.write(JSON.toJSONString(apiResponse).getBytes());
			//out.flush();
			//out.close();
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
        apiResponse.setResponseTime(DateTimeUtil.currentDateToStr(""));
        apiResponse.setRequestSeqNo(request.getParameter("requestSeqNo"));
		try {
			out = response.getWriter();
			out.append(JSON.toJSONString(apiResponse));
			out.flush();
		} catch (IOException e) {
			logger.error("logoutSuccess error:" + e.getMessage(), e);
		}
	}


	/**
	 * 企业不存在
	 * @param request
	 * @param response
	 */
	public static void corporateCodeError(ServletRequest request,
								   ServletResponse response) {
		ApiResponse apiResponse = new ApiResponse();
		preSetResponse(response);
		PrintWriter out = null;
		apiResponse.setErrorCode(ErrorCode.NEED_LOGIN.getCode());
		apiResponse.setErrorMessage(ErrorCode.NEED_LOGIN.getMessage());
		apiResponse.setResponseTime(DateTimeUtil.currentDateToStr(""));
		try {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.getWriter().write(JSON.toJSONString(apiResponse));
		} catch (IOException e) {
			logger.error("loginFailed error:" + e.getMessage(), e);
		}
	}

	/**
	 * 用户名密码错误
	 * 
	 * @param request
	 * @param response
	 */
	public static void loginFailed(ServletRequest request,
                                   ServletResponse response) {
		ApiResponse apiResponse = new ApiResponse();
		preSetResponse(response);
		PrintWriter out = null;
		apiResponse.setErrorCode(ErrorCode.LOGIN_FAILED.getCode());
		apiResponse.setErrorMessage(ErrorCode.LOGIN_FAILED.getMessage());
        apiResponse.setResponseTime(DateTimeUtil.currentDateToStr(""));
        apiResponse.setRequestSeqNo(request.getParameter("requestSeqNo"));
		try {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.getWriter().write(JSON.toJSONString(apiResponse));
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
        apiResponse.setResponseTime(DateTimeUtil.currentDateToStr(""));
        apiResponse.setRequestSeqNo(request.getParameter("requestSeqNo"));
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
	private String token;
	public LoginSuccessResut(String username, String token) {
		super();
		this.username = username;
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
