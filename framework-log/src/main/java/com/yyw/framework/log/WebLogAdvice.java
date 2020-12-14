package com.yyw.framework.log;


import com.bying.commons.exception.BusinessException;
import com.bying.commons.util.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2020/12/10 19:36
 */
public class WebLogAdvice implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {

    private final Logger logger = LoggerFactory.getLogger(WebLogAdvice.class);

    public static final String UUID = "uuid";
    public static final String RESULT_CODE = "resultCode";
    public static final String RESULT_MESSAGE = "resultMessage";
    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_JSON_CHARSET_UTF_8 = "application/json;charset=UTF-8";

    protected ThreadLocal<Long> startTime = new ThreadLocal<>();
    protected ThreadLocal<String> timeTag = new ThreadLocal<>();

    @Override
    public void before(Method method, Object[] args, Object target) {
        /* 记录启动前时间 */
        startTime.set(System.currentTimeMillis());
        timeTag.set(java.util.UUID.randomUUID() + "");
    }

    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) {
        logRequestInfo(target, method, null != returnValue ? returnValue.toString() : "");
    }

    public void afterThrowing(Method method, Object[] args, Object target, Exception ex) {
        logRequestInfo(target, method, buildReturnValueOfException(ex));
    }

    private void logRequestInfo(Object target, Method method, String returnValue) {
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        String uuid = "";
        if (request.getParameter(UUID) != null) {
            uuid = request.getParameter(UUID);
        }
        StringBuilder sb = new StringBuilder("\nSpringBoot action report ------------------------------------------------------")
                .append(uuid)
                .append("----------")
                .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                .append(" ------------------------------\n")
                .append("RemoteAddr  : ").append(ServletUtil.getIpAddr(request)).append("\n")
                .append("reqMethod   : ").append(request.getMethod()).append("\n").append("Controller  : ")
                .append(target.getClass().getName()).append(".(")
                .append(target.getClass().getSimpleName()).append(".java )").append("\n")
                .append("Method      : ").append(method.getName()).append("\n");
        String uri = request.getRequestURI();
        if (uri != null) {
            sb.append("url         : ").append(uri).append("\n");
        }
        sb.append("Parameter   : ");
        String header = request.getHeader("Content-Type");
        if (null != request.getQueryString()) {
            sb.append(request.getQueryString().replace("&", "   "));
        } else if (APPLICATION_JSON.equals(header) || APPLICATION_JSON_CHARSET_UTF_8.equals(header)) {
            sb.append(getRequestPayload(request));
        } else {
            sb.append(buildParameterString(request));
        }
        sb.append("\n");
        sb.append("return value: ").append(returnValue).append("\n");
        sb.append("times(ms)   : ").append((System.currentTimeMillis() - startTime.get())).append("ms").append("\n");
        sb.append("--------------------------------------------------------------------------------\n");
        logger.info("{}", sb);
    }

    private String buildReturnValueOfException(Exception ex) {
        if (null == ex) {
            return "";
        }
        Map<String, Object> result = new HashMap<>(2);
        if (ex instanceof BusinessException) {
            result.put(RESULT_CODE, ((BusinessException) ex).getCode());
            result.put(RESULT_MESSAGE, ex.getMessage());
            return result.toString();
        }
        result.put(RESULT_CODE, -1);
        result.put(RESULT_MESSAGE, "errorMessage:" + ex.getMessage() + "");
        return result.toString();
    }

    private String buildParameterString(HttpServletRequest request) {
        if (null == request) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Enumeration<String> e = request.getParameterNames();
        if (e.hasMoreElements()) {
            while (e.hasMoreElements()) {
                String name = e.nextElement();
                String[] values = request.getParameterValues(name);
                sb.append(name).append("=").append(String.join(",", values)).append("  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private String getRequestPayload(HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = req.getReader();
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
        } catch (IOException e) {
            logger.error("getRequestPayload error", e);
        }
        return sb.toString();
    }

}
