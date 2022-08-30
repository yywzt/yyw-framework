package com.yyw.api.exception;

import com.yyw.api.model.AppCode;

import java.text.MessageFormat;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2021/4/14 18:28
 */
public class BusinessException extends RuntimeException {
    private String code = "";
    private String message = "";

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public BusinessException(AppCode appCode) {
        this(appCode.getCode(), appCode.getMessage());
    }

    public BusinessException(AppCode appCode, Throwable cause) {
        this(appCode.getCode(), appCode.getMessage(), cause);
    }

    public BusinessException(AppCode appCode, Object... parameter) {
        this(appCode.getCode(), MessageFormat.format(appCode.getMessage(), parameter));
    }

    public BusinessException(AppCode appCode, Throwable cause, Object... parameter) {
        this(appCode.getCode(), MessageFormat.format(appCode.getMessage(), parameter), cause);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
