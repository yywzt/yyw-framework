package com.yyw.api.model;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2021/4/14 18:07
 */
public enum ResponseCode implements AppCode {
    /**
     *
     */
    RESOURCE_NOT_FOUND("404", "资源不存在"),
    UNKNOWN_EXCEPTION("-1", "系统压力山大,请稍后重试！"),
    SUCCESS("0", "OK"),
    INSERT_EXCEPTION("10", "数据新增失败！"),
    INSERT_BATCH_EXCEPTION("11", "数据新增失败！"),
    UPDATE_EXCEPTION("20", "数据更新失败！"),
    DELETE_EXCEPTION("30", "数据删除失败！"),
    DISABLE_EXCEPTION("31", "使数据无效失败！"),
    SELECT_ONE_EXCEPTION("40", "单条数据获取失败！"),
    SELECT_EXCEPTION("41", "数据获取失败！"),
    SELECT_PAGINATION_EXCEPTION("42", "分页数据获取失败！"),
    INVALID_SYSTEM_CLOCK("10101", "系统时间回调到当前时间之前的时间点，拒绝产生ID%d毫秒"),
    UNKNOWN_WORKER_ID("10102", "无法获取IdWorker标识"),
    INVALID_WORKER_ID("10103", "无效IdWorker标识，%d > %d");

    private String code;
    private String message;

    ResponseCode(String code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }

    @Override
    public String toString() {
        return "ResponseCode{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                "} " + super.toString();
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

}
