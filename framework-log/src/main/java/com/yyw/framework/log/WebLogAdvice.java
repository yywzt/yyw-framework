package com.yyw.framework.log;


import com.bying.commons.exception.BusinessException;
import com.bying.commons.util.ServletUtil;
import com.yyw.framework.log.dto.RequestLogDTO;
import com.yyw.framework.log.event.WebLogEvent;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String uuid = Optional.ofNullable(request.getParameter(UUID)).orElse(StringUtils.EMPTY);
        String requestMethod = request.getMethod();
        String methodName = method.getName();
        String classRemoteName = target.getClass().getName();
        String classSimpleName = target.getClass().getSimpleName();
        String ipAddr = ServletUtil.getIpAddr(request);
        StringBuilder sb = new StringBuilder("\nSpringBoot action report ------------------------------------------------------")
                .append(uuid)
                .append("----------")
                .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                .append(" ------------------------------\n")
                .append("RemoteAddr  : ").append(ipAddr).append("\n")
                .append("reqMethod   : ").append(requestMethod).append("\n").append("Controller  : ")
                .append(classRemoteName).append(".(")
                .append(classSimpleName).append(".java )").append("\n")
                .append("Method      : ").append(methodName).append("\n");
        String uri = request.getRequestURI();
        if (uri != null) {
            sb.append("url         : ").append(uri).append("\n");
        }
        String queryString = request.getQueryString();
        String parameter = buildParameterString(request);
        String body = getRequestPayload(request);
        sb.append("query       : ").append(queryString).append("\n");
        sb.append("Parameter   : ").append(parameter).append("\n");
        sb.append("body        : ").append(body).append("\n");
        sb.append("return value: ").append(returnValue).append("\n");
        long time = System.currentTimeMillis() - startTime.get();
        sb.append("times(ms)   : ").append(time).append("ms").append("\n");
        sb.append("--------------------------------------------------------------------------------\n");
        logger.info("{}", sb);
        int status = attributes.getResponse().getStatus();
        RequestLogDTO roadBookRequestLogDTO = createRoadBookRequestLogDTO(uri, requestMethod, methodName, classRemoteName
                , queryString, parameter, body, returnValue, status, ipAddr, time);
        LogAutoConfiguration.APPLICATION_CONTEXT.publishEvent(new WebLogEvent(this, roadBookRequestLogDTO));
    }

    public RequestLogDTO createRoadBookRequestLogDTO(String uri, String requestMethod, String methodName, String classRemoteName
            , String queryString, String parameter, String body, String returnValue, Integer status, String ipAddr, long time) {
        Date date = new Date();
        RequestLogDTO requestLogDTO = new RequestLogDTO();
        requestLogDTO.setCreateDate(date);
        requestLogDTO.setUrl(uri);
        requestLogDTO.setRequestMethod(requestMethod);
        requestLogDTO.setMethodName(methodName);
        requestLogDTO.setClassName(classRemoteName);
        requestLogDTO.setParameter(parameter);
        requestLogDTO.setResponse(returnValue);
        requestLogDTO.setStatus(status);
        requestLogDTO.setIp(ipAddr);
        requestLogDTO.setTime(time);
        return requestLogDTO;
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
        }
        return sb.toString();
    }

    private String getRequestPayload(HttpServletRequest request) {
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        try {
            return IOUtils.toString(wrapper.getContentAsByteArray(), wrapper.getCharacterEncoding());
        } catch (IOException e) {
            logger.error("getRequestPayload error ", e);
        }
        return StringUtils.EMPTY;
    }

}
