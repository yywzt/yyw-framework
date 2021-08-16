package com.yyw.api.model;

import java.util.Objects;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2021/4/14 18:03
 */
public class ResponseData<T> {

    private String code;
    private String message;
    private T data;

    public ResponseData() {
    }

    public ResponseData(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseData(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseData(AppCode appCode) {
        this.code = appCode.getCode();
        this.message = appCode.getMessage();
    }

    public ResponseData(AppCode appCode, T data) {
        this.code = appCode.getCode();
        this.message = appCode.getMessage();
        this.data = data;
    }

    public static ResponseData<Object> success(String code, String message) {
        return new ResponseData<>(code, message);
    }

    public static <T> ResponseData<T> success(String code, String message, T data) {
        return new ResponseData<>(code, message, data);
    }

    public static ResponseData<Object> failure(String code, String message) {
        return new ResponseData<>(code, message);
    }

    public static <T> ResponseData<T> failure(String code, String message, T data) {
        return new ResponseData<>(code, message, data);
    }

    public static ResponseData<Object> failure(AppCode appCode) {
        return new ResponseData<>(appCode);
    }

    public static <T> ResponseData<T> failure(AppCode appCode, T data) {
        return new ResponseData<>(appCode, data);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResponseData)) {
            return false;
        }
        ResponseData<?> that = (ResponseData<?>) o;
        return Objects.equals(getCode(), that.getCode()) && Objects.equals(getMessage(), that.getMessage()) && Objects.equals(getData(), that.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getMessage(), getData());
    }
}
