package tech.yozo.factoryrp.config;

import tech.yozo.factoryrp.utils.MDCUtils;
import tech.yozo.factoryrp.utils.NetworkUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class ServletFilter implements Filter {
    //响应码为302时，response header中跳转地址的key
    private static final String RESPONSE_HEADER_NAME_REDIRECT = "Location";
    private static final String PARAM_NAME_EXCLUSIONS = "exclusions";
    private Set<String> excludesPattern;
    private String contextPath;

    @Override
    public void init(FilterConfig config) throws ServletException {
        contextPath = this.getContextPath(config.getServletContext());
        excludesPattern = new HashSet<>();
        String exclusions = config.getInitParameter(PARAM_NAME_EXCLUSIONS);
        if (StringUtils.isNotEmpty(exclusions)) {
            String[] arr = exclusions.trim().split("\\s*,\\s*");
            excludesPattern.addAll(Arrays.asList(arr));
        }
        excludesPattern.add("favicon.ico");
        excludesPattern.add("health");
        excludesPattern.add("info");

        NetworkUtils.IpAddress ipAddress = NetworkUtils.getIp();
        String ip = ipAddress.getIP();
        MDCUtils.setIp(ip);
        log.info("servlet init..");
    }

    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String requestUri = req.getRequestURI();
        this.setMdcData(req);
        if (this.isExclusion(requestUri)) {
            chain.doFilter(request, response);
            log.info("请求:method=" + req.getMethod());
            return;
        }

        StringBuilder logBuffer = new StringBuilder();
        this.logRequestParameters(logBuffer, req);
        log.info(logBuffer.toString());
        try {
            chain.doFilter(req, res);
        } finally {
            this.removeMdcData();
        }
    }

    @Override
    public void destroy() {
    }

    /**
     * 判断是否无效的请求地址
     * @param uri
     * @return
     */
    private boolean isInvalid(String uri) {
        if (StringUtils.isBlank(uri)) {
            return true;
        }
        return uri.lastIndexOf('.') > 0;
    }
    private boolean isExclusion(String requestURI) {
        if (excludesPattern == null) {
            return false;
        }

        if (contextPath != null && requestURI.startsWith(contextPath)) {
            requestURI = requestURI.substring(contextPath.length());
        }

        for (String pattern : excludesPattern) {
            if (this.matches(pattern, requestURI)) {
                return true;
            }
        }

        return false;
    }

    private void setMdcData(HttpServletRequest request) {
        MDCUtils.setMsgId(this.getMsgId(request));
        MDCUtils.setUrL(request.getRequestURI());
    }
    private void removeMdcData() {
        MDCUtils.removeMsgId();
        MDCUtils.removeUrL();
    }
    /**
     * 从http header中获取消息埋点id，没有则生成一个
     * @return
     */
    private String getMsgId(HttpServletRequest request) {
        String msgId = request.getHeader(MDCUtils.KEY_MSG_ID);
        if (StringUtils.isBlank(msgId)) {
            msgId = MDCUtils.generateId();
        }
        return msgId;
    }

    private void logRequestParameters(
            StringBuilder logBuffer, HttpServletRequest request) throws IOException {
        logBuffer.append("请求:method=").append(request.getMethod()).append(" Request:");
        Enumeration<String> en = request.getParameterNames();
        while (en.hasMoreElements()) {
            String key = en.nextElement();
            if (StringUtils.isNotEmpty(key)) {
                String value = request.getParameter(key);
                logBuffer.append("&" + key + "=" + value);
            }
        }
    }


    private String getContextPath(ServletContext context) {
        String contextPath;
        try {
            contextPath = context.getContextPath();
            if (StringUtils.isEmpty(contextPath)) {
                contextPath = "/";
            }
        } catch (Exception e) {
            contextPath = "/";
        }

        return contextPath;
    }

    private boolean matches(String pattern, String source) {
        int start;
        if(pattern.endsWith("*")) {
            start = pattern.length() - 1;
            if(source.length() >= start && pattern.substring(0, start).equals(source.substring(0, start))) {
                return true;
            }
        } else if(pattern.startsWith("*")) {
            start = pattern.length() - 1;
            if(source.length() >= start && source.endsWith(pattern.substring(1))) {
                return true;
            }
        } else if(pattern.contains("*")) {
            start = pattern.indexOf("*");
            int end = pattern.lastIndexOf("*");
            if(source.startsWith(pattern.substring(0, start)) && source.endsWith(pattern.substring(end + 1))) {
                return true;
            }
        } else if(pattern.equals(source)) {
            return true;
        }

        return false;
    }
}
