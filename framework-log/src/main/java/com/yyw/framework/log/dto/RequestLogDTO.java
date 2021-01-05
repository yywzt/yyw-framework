package com.yyw.framework.log.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2021/1/5 14:34
 */
public class RequestLogDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Date createDate;
    private String url;
    private String requestMethod;
    private String methodName;
    private String className;
    private String queryString;
    private String parameter;
    private String body;
    private String response;
    private Integer status;
    private String ip;
    private Long time;


    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "RequestLogDTO{" +
                "createDate=" + createDate +
                ", url='" + url + '\'' +
                ", requestMethod='" + requestMethod + '\'' +
                ", methodName='" + methodName + '\'' +
                ", className='" + className + '\'' +
                ", queryString='" + queryString + '\'' +
                ", parameter='" + parameter + '\'' +
                ", body='" + body + '\'' +
                ", response='" + response + '\'' +
                ", status=" + status +
                ", ip='" + ip + '\'' +
                ", time=" + time +
                '}';
    }
}
