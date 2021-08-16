package com.yyw.framework.exception;

import com.yyw.api.exception.BusinessException;
import com.yyw.api.model.ResponseData;
import com.yyw.framework.util.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * @author yanzhitao
 */
@RestControllerAdvice
public class GlobalDefaultExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
    private static final String EXCEPTION_MSG = "请稍后再试";
    public static final String DEFAULT_EXCEPTION_CODE = "-1";

    @ExceptionHandler(value = Exception.class)
    public ResponseData<Object> defaultErrorHandler(HttpServletRequest request, Exception e) {
        printErrorLog(request, e);
        logger.error("defaultErrorHandler execute");
        return ResponseData.failure(DEFAULT_EXCEPTION_CODE, EXCEPTION_MSG);
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseData<Object> businessExceptionHandler(HttpServletRequest request, Exception e) {
        printErrorLog(request, e);
        logger.error("businessExceptionHandler execute");
        BusinessException businessException = (BusinessException) e;
        return ResponseData.failure(businessException.getCode(), businessException.getMessage());
    }

    public void printErrorLog(HttpServletRequest request, Exception e) {
        logger.error("GlobalException", e);
        //-----------------------------------------------
        //发生异常时候，打印出异常请求
        String uuid = "";
        if (request.getParameter("uuid") != null) {
            uuid = request.getParameter("uuid");
        }

        StringBuilder sb = new StringBuilder("\nException error report -------------")
                .append(uuid).append("----------")
                .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()))
                .append(" ------------------------------\n");

        sb.append("RemoteAddr  : ").append(ServletUtil.getIpAddr(request)).append("\n");

        String uri = request.getRequestURI();
        if (uri != null) {
            sb.append("url         : ").append(uri).append("\n");
        }

        String queryString = request.getQueryString();
        if (queryString != null) {
            sb.append("Parameter   : ").append(queryString.replace("&", "   ")).append("\n");
        } else {
            sb.append("Parameter   : ").append(buildParameterString(request)).append("\n");
        }
        sb.append("--------------------------------------------------------------------------------\n");
    }

    private String buildParameterString(HttpServletRequest request) {
        if (null == request) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Enumeration<String> e = request.getParameterNames();
        if (!e.hasMoreElements()) {
            return sb.toString();
        }
        while (e.hasMoreElements()) {
            String name = e.nextElement();
            String[] values = request.getParameterValues(name);
            sb.append(name).append("=").append(String.join(",", values)).append("  ");
        }
        return sb.toString();
    }

}
