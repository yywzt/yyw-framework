package com.yyw.framework.exception;

import com.yyw.api.exception.BusinessException;
import com.yyw.api.model.ResponseCode;
import com.yyw.api.model.ResponseData;
import com.yyw.framework.util.ServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

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

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseData<Object> illegalArgumentExceptionHandler(HttpServletRequest request, Exception e) {
        printErrorLog(request, e);
        logger.error("IllegalArgumentExceptionHandler return!!!", e);
        return new ResponseData<>(ResponseCode.UNKNOWN_EXCEPTION.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = IOException.class)
    public ResponseData<Object> ioExceptionErrorHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        printErrorLog(request, e);
        logger.error("ioExceptionErrorHandler done!!!", e);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseData<>(ResponseCode.UNKNOWN_EXCEPTION);
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseData<Object> missingServletRequestParameterErrorHandler(HttpServletRequest request, Exception e) {
        printErrorLog(request, e);
        logger.error("MissingServletRequestParameterErrorHandler: {}", e.getMessage());
        return new ResponseData<>(ResponseCode.UNKNOWN_EXCEPTION.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseData<Object> httpRequestMethodNotSupportedErrorHandler(HttpServletRequest request, Exception e) {
        printErrorLog(request, e);
        logger.error("httpRequestMethodNotSupportedErrorHandler: {}", e.getMessage());
        return new ResponseData<>(ResponseCode.UNKNOWN_EXCEPTION.getCode(), e.getMessage());
    }

    /**
     * 参数校验未通过
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BindException.class)
    public ResponseData<Object> handleBindException(BindException e, HttpServletRequest request) {
        printErrorLog(request, e);
        logger.error("handleBindException return", e);
        BindingResult bindingResult = e.getBindingResult();
        List<String> msg = new ArrayList<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().forEach(fieldError -> msg.add(fieldError.getDefaultMessage()));
        }
        return new ResponseData<>(getStatus(request).value() + "", Arrays.toString(msg.toArray()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseData<Object> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                                       HttpServletRequest request) {
        printErrorLog(request, e);
        logger.error("handlerMethodArgumentNotValidException return", e);
        BindingResult result = e.getBindingResult();
        List<String> msg = new ArrayList<>();
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(fieldError -> msg.add(fieldError.getDefaultMessage()));
        }
        return new ResponseData<>(getStatus(request).value() + "", Arrays.toString(msg.toArray()));
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
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
