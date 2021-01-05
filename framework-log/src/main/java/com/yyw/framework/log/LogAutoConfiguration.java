package com.yyw.framework.log;

import org.springframework.aop.aspectj.AspectJExpressionPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanzhitao@xiaomalixing.com
 * @date 2020/12/10 17:43
 */
@Configuration
@ConditionalOnClass(LogProperties.class)
@ConditionalOnWebApplication
@EnableConfigurationProperties(LogProperties.class)
public class LogAutoConfiguration {

    @Autowired
    private LogProperties logProperties;

    @Bean
    @ConditionalOnProperty(prefix = "log", name = "enable", havingValue = "true")
    public AspectJExpressionPointcutAdvisor configurabledvisor() {
        AspectJExpressionPointcutAdvisor advisor = new AspectJExpressionPointcutAdvisor();
        advisor.setExpression(logProperties.getPointcut());
        advisor.setAdvice(new WebLogAdvice());
        return advisor;
    }

    /**
     * 注册filter
     */
    @Bean
    @ConditionalOnProperty(prefix = "log", name = "enable", havingValue = "true")
    public FilterRegistrationBean<RequestWrapperFilter> filterRegistrationAuthBean() {
        FilterRegistrationBean<RequestWrapperFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new RequestWrapperFilter());
        registration.addUrlPatterns("/*");
        registration.setName("requestWrapperFilter");
        registration.setOrder(1);
        return registration;
    }
}
