package com.yyw.framework.log;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2020/12/10 17:45
 */
@ConfigurationProperties(prefix = "log")
public class LogProperties {

    private Boolean enable = false;
    private String pointcut;

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public String getPointcut() {
        return pointcut;
    }

    public void setPointcut(String pointcut) {
        this.pointcut = pointcut;
    }
}
